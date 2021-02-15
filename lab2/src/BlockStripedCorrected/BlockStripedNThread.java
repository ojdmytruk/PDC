package BlockStripedCorrected;

public class BlockStripedNThread extends Thread{
    private int j;
    private double[][] rows;
    private double[] column;
    private double[][] resultMatrix;
    private int firstRowIndex;

    BlockStripedNThread (double[][] rows, double[][] resultMatrix, int firstRowIndex) {
        this.rows = rows;
        this.resultMatrix = resultMatrix;
        this.firstRowIndex = firstRowIndex;
    }

    @Override
    public void run() {
        synchronized (resultMatrix){
            for (int i=0; i<rows.length; i++){
                calculateRowElement(rows[i], i+firstRowIndex);
            }
        }

    }

    private void calculateRowElement(double[] row, int rowIndex){
        synchronized (column){
            int result = 0;
            for (int i = 0; i < column.length; i++) {
                result += row[i] * column[i];
            }
            resultMatrix[rowIndex][j] = result;
        }
    }

    public void setColumn(double[] column) {
        this.column=column;
    }

    public void setJ(int j) {
        this.j=j;
    }
}
