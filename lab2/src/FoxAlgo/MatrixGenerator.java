package FoxAlgo;

import java.util.Random;

public class MatrixGenerator {
    public static double[][] randomMatrixGenerator(int matrixSize){
        Random random = new Random();
        double[][] result = new double[matrixSize][matrixSize];

        for (int i=0; i<matrixSize; i++){
            for (int j=0; j<matrixSize; j++){
                int randomIntPart = random.nextInt(100);
                double randomDoublePart = random.nextDouble();
                result[i][j] = (double) randomIntPart + randomDoublePart;
            }
        }
        return result;
    }

    public static double[][] identityMatrixGenerator(int matrixSize){
        double[][] result = new double[matrixSize][matrixSize];

        for (int i=0; i<matrixSize; i++){
            for (int j=0; j<matrixSize; j++){
                if (i==j)
                    result[i][j]=1;
                else result[i][j]=0;
            }
        }
        return result;
    }

    public static double[][] lineElementMatrixGenerator(int matrixSize){
        double[][] result = new double[matrixSize][matrixSize];

        for (int i=0; i<matrixSize; i++){
            for (int j=0; j<matrixSize; j++){
                result[i][j] = (double) i+1;
            }
        }
        return result;
    }
}
