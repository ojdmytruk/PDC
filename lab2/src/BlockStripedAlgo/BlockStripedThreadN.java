package BlockStripedAlgo;

public class BlockStripedThreadN implements Runnable{
    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] resultMatrix;
    private int firstRowIndex;
    private int lastRowIndex;

    public BlockStripedThreadN(double[][] matrixA, double[][] matrixB, double[][] resultMatrix, int firstRowIndex, int lastRowIndex){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.firstRowIndex = firstRowIndex;
        this.lastRowIndex = lastRowIndex;
    }

    @Override
    public void run() {
        for (int i=firstRowIndex; i<lastRowIndex; i++){
           calculateRow(i);
        }
    }

    private void calculateRow(int rowIndex){
        for (int column = 0; column<matrixB[0].length; column++){
            double rowElement = 0;
            for (int i = 0; i<matrixB.length; i++){
                rowElement += matrixA[rowIndex][i] * matrixB[i][column];
            }
            resultMatrix[rowIndex][column] = rowElement;
        }
    }
}
