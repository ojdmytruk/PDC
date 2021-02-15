package BlockStripedCorrected;

public class BlockStripedMain {

    public static void main(String[] args){
//        MatrixGenerator mg = new MatrixGenerator();
//        double[][] matrixA = mg.identityMatrixGenerator(1000);
//        double[][] matrixB = mg.lineElementMatrixGenerator(1000);
//        Result r1 = new Result(1000);
//        BlockStripedMultiplication bsm1 = new BlockStripedMultiplication(matrixA, matrixB, r1, 100);
//        bsm1.multiplyMatrixNParallel();
//        r1.printMatrix();
        experimentNSize();
        experimentNThreads();

    }

    public static void experimentNSize(){
        long[] timesSerial = new long[5];
        long[] timesParallel = new long[5];
        int[] steps = new int[]{400,800,1200,1600,2000};

        for (int i=0; i<5; i++){
            long timeSumSerial = 0;
            long timeSumParallel = 0;
            BlockStripedAlgo.MatrixGenerator mg = new BlockStripedAlgo.MatrixGenerator();
            double[][] matrixA = mg.randomMatrixGenerator(steps[i]);
            double[][] matrixB = mg.randomMatrixGenerator(steps[i]);
            for (int j=0; j<4; j++){
                Result r = new Result(steps[i]);
                BlockStripedMultiplication bsm = new BlockStripedMultiplication(matrixA, matrixB, r);
                bsm.multiplyMatrixParallel();
                bsm.multiplyMatrixSerial();
                timeSumParallel += bsm.getParallelExecutionTime();
                timeSumSerial += bsm.getSerialExecutionTime();
            }
            timesParallel[i] = timeSumParallel/4;
            timesSerial[i] = timeSumSerial/4;
        }
        System.out.format("%32s%32s%32s%32s", "Size", "Serial", "Parallel", "SpeedUp");
        for (int i=0; i<5; i++){
            System.out.println();
            System.out.format("%32f%32f%32f%32f", (double) steps[i], (double) timesSerial[i], (double) timesParallel[i], (double) timesSerial[i]/timesParallel[i]);
        }
        System.out.println();
    }

    public static void experimentNThreads(){
        long[] timesSerial = new long[5];
        long[] timesParallel = new long[5];
        int[] threads = new int[]{200,400,600,800,1000};
        long timeSumSerial = 0;

        for (int i=0; i<5; i++){
            long timeSumParallel = 0;
            BlockStripedAlgo.MatrixGenerator mg = new BlockStripedAlgo.MatrixGenerator();
            double[][] matrixA = mg.randomMatrixGenerator(1000);
            double[][] matrixB = mg.randomMatrixGenerator(1000);
            for (int j=0; j<4; j++){
                BlockStripedAlgo.BlockStripedMultiplication bsm = new BlockStripedAlgo.BlockStripedMultiplication(matrixA, matrixB, threads[i]);
                BlockStripedAlgo.Result r1 = bsm.multiplyMatrixParallelNThreads();
                if (i==0){
                    BlockStripedAlgo.Result r2 = bsm.multiplyMatrixSerial();
                    timeSumSerial += bsm.getSerialExecutionTime();
                }
                timeSumParallel += bsm.getParallelNExecutionTime();

            }
            timesParallel[i] = timeSumParallel/4;
            timesSerial[i] = timeSumSerial/4;
        }
        System.out.format("%32s%32s%32s%32s", "Threads", "Serial", "Parallel", "SpeedUp");
        for (int i=0; i<5; i++){
            System.out.println();
            System.out.format("%32f%32f%32f%32f", (double) threads[i], (double) timesSerial[i], (double) timesParallel[i], (double) timesSerial[i]/timesParallel[i]);
        }
        System.out.println();
    }

}
