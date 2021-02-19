package FoxThread;

public class FoxThread extends Thread{
    private double[][] blockOfMatrixA;
    private double[][] blockOfMatrixB;
    private double[][] resultMatrix;
    private int iBound;
    private int jBound;
    private double[][][][] matrixOfMatricesA;
    private double[][][][] matrixOfMatricesB;
    private Solver solver;
    private int blocksNum;


    public FoxThread(double[][] result, int iBound, int jBound, int blocksNum){
        this.resultMatrix = result;
        this.iBound = iBound;
        this.jBound = jBound;
        this.solver = new Solver(this.resultMatrix, this.iBound, this.jBound);
        this.blocksNum = blocksNum;
    }

    @Override
    public void run() {
        synchronized (resultMatrix){
            for (int l=0; l<blocksNum; l++){
                for (int i=0; i<blocksNum; i++){
                    for (int j=0; j<blocksNum; j++){
                        solver.setBlockOfMatrixA(matrixOfMatricesA[i][(i+l) % blocksNum]);
                        solver.setBlockOfMatrixB(matrixOfMatricesB[(i+l) % blocksNum][j]);
                    }
                }
                solver.run();
            }
        }
    }

    private double[][] multiplyBlocks(){
        double[][] blockResult = new double[blockOfMatrixA.length][blockOfMatrixA.length];
        for (int i=0; i<blockOfMatrixA.length; i++){
            for (int j = 0; j<blockOfMatrixA.length; j++){
                double resultElement = 0;
                for (int k = 0; k < blockOfMatrixB.length; k++)
                    resultElement += blockOfMatrixA[i][k] * blockOfMatrixB[k][j];
                blockResult[i][j] = resultElement;
            }
        }
        return blockResult;
    }

    public void setBlockOfMatrixA(double[][] blockOfMatrixA){
        this.blockOfMatrixA = blockOfMatrixA;
    }

    public void setBlockOfMatrixB(double[][] blockOfMatrixB){
        this.blockOfMatrixB = blockOfMatrixB;
    }
    public void setMatrixOfMatricesA(double[][][][] matrixOfMatricesA){
        this.matrixOfMatricesA = matrixOfMatricesA;
    }
    public void setMatrixOfMatricesB(double[][][][] matrixOfMatricesB){
        this.matrixOfMatricesB = matrixOfMatricesB;
    }
}
