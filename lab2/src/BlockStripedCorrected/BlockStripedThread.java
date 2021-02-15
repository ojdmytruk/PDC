package BlockStripedCorrected;

public class BlockStripedThread extends Thread{

    private int i;
    private int j;
    private double[] row;
    private double[] column;
    private double[][] resultMatrix;

    BlockStripedThread (int i, double[] row, double[][] resultMatrix) {
        this.i = i;
        this.row = row;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void run() {
        synchronized (column){
            int result = 0;
            for (int i = 0; i < row.length; i++) {
                result += row[i] * column[i];
            }
            resultMatrix[i][j] = result;
        }
    }

    public void setColumn(double[] column) {
        this.column=column;
    }

    public void setJ(int j) {
        this.j=j;
    }
}
