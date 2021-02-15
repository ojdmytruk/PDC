package FoxAlgo;


import BlockStripedCorrected.BlockStripedMultiplication;

public class FoxMain {

    public static void main(String[] args){
//        MatrixGenerator mg = new MatrixGenerator();
//        double[][] matrixA = mg.identityMatrixGenerator(1000);
//        double[][] matrixB = mg.lineElementMatrixGenerator(1000);
//        Result r1 = new Result(1000);
//        FoxMultiplication fm1 = new FoxMultiplication(matrixA, matrixB, r1, 100);
//        fm1.multiplyMatrixNParallel();
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
            MatrixGenerator mg = new MatrixGenerator();
            double[][] matrixA = mg.randomMatrixGenerator(steps[i]);
            double[][] matrixB = mg.randomMatrixGenerator(steps[i]);
            for (int j=0; j<4; j++){
                Result r = new Result(steps[i]);
                FoxMultiplication fm = new FoxMultiplication(matrixA, matrixB, r, 100);
                fm.multiplyMatrixNParallel();
                fm.multiplyMatrixSerial();
                timeSumParallel += fm.getParallelNExecutionTime();
                timeSumSerial += fm.getSerialExecutionTime();
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
        int[] threads = new int[]{100,400,625,2500,10000};
        long timeSumSerial = 0;

        for (int i=0; i<5; i++){
            long timeSumParallel = 0;
            MatrixGenerator mg = new MatrixGenerator();
            double[][] matrixA = mg.randomMatrixGenerator(1000);
            double[][] matrixB = mg.randomMatrixGenerator(1000);
            for (int j=0; j<4; j++){
                Result r = new Result(1000);
                FoxMultiplication fm = new FoxMultiplication(matrixA, matrixB, r, threads[i]);
                fm.multiplyMatrixNParallel();
                if (i==0){
                    fm.multiplyMatrixSerial();
                    timeSumSerial += fm.getSerialExecutionTime();
                }
                timeSumParallel += fm.getParallelNExecutionTime();

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
