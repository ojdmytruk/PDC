package FoxAlgo;

public class FoxThread extends Thread{
    private double[][] blockOfMatrixA;
    private double[][] blockOfMatrixB;
    private double[][] resultMatrix;
    private int iBound;
    private int jBound;


    public FoxThread(double[][] result, int iBound, int jBound){
        this.resultMatrix = result;
        this.iBound = iBound;
        this.jBound = jBound;
    }

//    public FoxThread(double[][] blockOfMatrixA, double[][] blockOfMatrixB, double[][] resultMatrix){
//        this.blockOfMatrixA = blockOfMatrixA;
//        this.blockOfMatrixB = blockOfMatrixB;
//        this.resultMatrix = resultMatrix;
//    }

    @Override
    public void run() {
        synchronized (resultMatrix){
            double [][] blockOfResult = multiplyBlocks();
            for (int i=0; i<blockOfResult.length; i++){
                for (int j=0; j<blockOfResult.length; j++){
                    resultMatrix[i+iBound][j+jBound] += blockOfResult[i][j];
                }
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

}
