package FoxThread;


public class FoxMultiplication {

    private long serialExecutionTime;
    private long parallelNExecutionTime;

    private double[][] matrixA;
    private double[][] matrixB;
    private Result result;
    private int blocksNum;

    private int numOfThreads;

    public FoxMultiplication(double[][] matrixA, double[][] matrixB, Result result, int numOfThreads){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.numOfThreads = numOfThreads;
        this.blocksNum = (int) Math.sqrt((double) numOfThreads);
    }

    public void multiplyMatrixSerial() {
        int rows = matrixA.length;
        int columns = matrixB[0].length;

        long startTime = System.nanoTime();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < columns; ++col) {
                double resultElement = 0;
                for (int i = 0; i < matrixB.length; ++i)
                    resultElement += matrixA[row][i] * matrixB[i][col];
                result.matrix[row][col] = resultElement;
            }
        }
        this.serialExecutionTime = (System.nanoTime() - startTime) / 1000000;
    }

    public void multiplyMatrixNParallel(){
        int rowsCount = matrixA.length;
        FoxThread[][] threads = new FoxThread[blocksNum][blocksNum];
        int blockSize = rowsCount / blocksNum;
        double[][][][] matrixOfMatricesA = new double[blocksNum][blocksNum][blockSize][blockSize];
        double[][][][] matrixOfMatricesB = new double[blocksNum][blocksNum][blockSize][blockSize];
        int iBound = 0;

        long startTime = System.nanoTime();

        for (int i=0; i<blocksNum; i++){
            int jBound = 0;
            for (int j=0; j<blocksNum; j++){
                matrixOfMatricesA[i][j] = copyBlock(matrixA, iBound, jBound, blockSize);
                matrixOfMatricesB[i][j] = copyBlock(matrixB, iBound, jBound, blockSize);
                threads[i][j] = new FoxThread(result.matrix, iBound, jBound, blocksNum);
                jBound += blockSize;
            }
            iBound += blockSize;
        }

        for (int i=0; i<blocksNum; i++) {
            for (int j = 0; j < blocksNum; j++) {
                threads[i][j].setMatrixOfMatricesA(matrixOfMatricesA);
                threads[i][j].setMatrixOfMatricesB(matrixOfMatricesB);
            }
        }

        for (int i=0; i<blocksNum; i++){
            for (int j=0; j<blocksNum; j++){
                threads[i][j].start();
            }
        }
        for (int i=0; i<blocksNum; i++) {
            for (int j = 0; j < blocksNum; j++) {
                try {
                    threads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.parallelNExecutionTime = (System.nanoTime() - startTime) / 1000000;

    }

    private double[][] copyBlock(double[][] matrix, int i, int j, int size) {
        double[][] block = new double[size][size];
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                block[k][l] = matrix[k + i][l + j];
            }
        }
        return block;
    }


    public long getParallelNExecutionTime() {
        return parallelNExecutionTime;
    }

    public long getSerialExecutionTime() {
        return serialExecutionTime;
    }

}
