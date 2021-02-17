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
//        while (this.column==null) {
//            try {
//                sleep(1);
//            } catch (InterruptedException exception) {
//                exception.printStackTrace();
//            }
//        }
        synchronized (column){
            int result = 0;
            for (int i = 0; i < row.length; i++) {
                result += row[i] * column[i];
            }
            resultMatrix[i][j] = result;
            //this.column = null;
        }
    }

    public void setColumn(double[] column) {
        this.column=column;
    }

    public void setJ(int j) {
        this.j=j;
    }
}
