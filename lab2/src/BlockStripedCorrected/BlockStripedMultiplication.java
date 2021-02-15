package BlockStripedCorrected;

import java.util.ArrayList;

public class BlockStripedMultiplication {

    private double[][] matrixA;
    private double[][] matrixB;
    private Result result;
    private long serialExecutionTime;
    private long parallelExecutionTime;
    private long parallelNExecutionTime;
    private int numOfThreads;

    public BlockStripedMultiplication(double[][] matrixA, double[][] matrixB, Result result){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
    }

    public BlockStripedMultiplication(double[][] matrixA, double[][] matrixB, Result result, int numOfThreads){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.numOfThreads = numOfThreads;
    }

    public void multiplyMatrixSerial() {
        int rows = matrixA.length;
        int columns = matrixB[0].length;

        long startTime = System.nanoTime();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                double resultElement = 0;
                for (int i = 0; i < matrixB.length; i++)
                    resultElement += matrixA[row][i] * matrixB[i][col];
                result.matrix[row][col] = resultElement;
            }
        }
        this.serialExecutionTime = (System.nanoTime() - startTime) / 1000000;
    }

    public void multiplyMatrixParallel() {
        int rowsCount = matrixA.length;
        double[][] transposedB = transposeMatrix(matrixB);
        ArrayList<BlockStripedThread> threads = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            BlockStripedThread thread = new BlockStripedThread(i, matrixA[i], result.matrix);
            threads.add(thread);
        }

        long startTime = System.nanoTime();
        for (int j = 0; j < rowsCount; j++) {
            for (BlockStripedThread thread : threads) {
                thread.setColumn(transposedB[j]);
                thread.setJ(j);
                thread.run();
            }
            for (BlockStripedThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.parallelExecutionTime = (System.nanoTime() - startTime) / 1000000;
    }

    public void multiplyMatrixNParallel() {
        int rowsCount = matrixA.length;
        double[][] transposedB = transposeMatrix(matrixB);
        ArrayList<BlockStripedNThread> threads = new ArrayList<>();
        int rowsForOneThread = rowsCount / numOfThreads;
        int firstRowForThread = 0;

        for (int i = 0; i < numOfThreads; i++) {
            int lastRowForThread = firstRowForThread + rowsForOneThread;
            if (lastRowForThread>rowsCount){
                lastRowForThread = rowsCount;
            }
            double[][] rows = severalRows(matrixA, firstRowForThread, lastRowForThread);
            BlockStripedNThread thread = new BlockStripedNThread(rows, result.matrix, firstRowForThread);
            threads.add(thread);
            firstRowForThread = lastRowForThread;
        }
        long startTime = System.nanoTime();
        for (int j = 0; j < rowsCount; j++) {
            for (BlockStripedNThread thread : threads) {
                thread.setColumn(transposedB[j]);
                thread.setJ(j);
                thread.run();
            }
            for (BlockStripedNThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.parallelNExecutionTime = (System.nanoTime() - startTime) / 1000000;
    }

    private double[][] transposeMatrix(double[][] matrix){
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix.length; j++) {
                result[i][j] = matrix[j][i];
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    private double[][] severalRows(double[][] matrix, int firstRow, int lastRow) {
        double[][] rowsArray = new double[lastRow-firstRow][matrix.length];
        int i = 0;
        for (int k = firstRow; k < lastRow; k++) {
            for (int j = 0; j < matrix.length; j++) {
                rowsArray[i][j] = matrix[k][j];
            }
            i++;
        }
        return rowsArray;
    }

    public long getParallelExecutionTime(){
        return parallelExecutionTime;
    }

    public long getParallelNExecutionTime() {
        return parallelNExecutionTime;
    }

    public long getSerialExecutionTime() {
        return serialExecutionTime;
    }
}
