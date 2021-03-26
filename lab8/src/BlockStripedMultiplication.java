import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockStripedMultiplication {

    private long serialExecutionTime;
    private long parallelExecutionTime;
    private long parallelNExecutionTime;

    private double[][] matrixA;
    private double[][] matrixB;

    private int numOfThreads;

    public BlockStripedMultiplication(double[][] matrixA, double[][] matrixB){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    public BlockStripedMultiplication(double[][] matrixA, double[][] matrixB, int numOfThreads){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.numOfThreads = numOfThreads;
    }

    public Result multiplyMatrixParallelNThreads(){
        int rows = matrixA.length;
        int columns = matrixB[0].length;
        double[][] resultMatrix = new double[rows][columns];
        Thread[] threads = new Thread[numOfThreads];
        int rowsForOneThread = rows / numOfThreads;
        int firstRowForThread = 0;

        long startTime = System.nanoTime();
        for (int i=0; i<numOfThreads; i++){
            int lastRowForThread = firstRowForThread + rowsForOneThread;
            if (lastRowForThread>rows){
                lastRowForThread = rows;
            }
            threads[i] = new Thread(new BlockStripedThreadN(matrixA, matrixB, resultMatrix, firstRowForThread, lastRowForThread));
            threads[i].start();
            firstRowForThread = lastRowForThread;
        }
        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException e) {}
        this.parallelNExecutionTime = (System.nanoTime() - startTime) / 1000000;
        Result result = new Result(resultMatrix);
        return result;
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
