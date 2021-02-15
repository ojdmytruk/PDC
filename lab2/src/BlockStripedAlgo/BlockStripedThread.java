package BlockStripedAlgo;

public class BlockStripedThread implements Runnable{
    private double[] rowMatrixA;
    private double[][] matrixB;
    private double[][] resultMatrix;
    private int index;

    public BlockStripedThread(double[] rowMatrixA, double[][] matrixB, double[][] resultMatrix, int index){
        this.rowMatrixA = rowMatrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.index = index;
    }

    @Override
    public void run() {
        for (int column = 0; column<matrixB[0].length; column++){
            double rowElement = 0;
            for (int i = 0; i<matrixB.length; i++){
                rowElement += rowMatrixA[i] * matrixB[i][column];
            }
            resultMatrix[index][column] = rowElement;
        }
    }

}
