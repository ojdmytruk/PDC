/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labs67;

/**
 *
 * @author teraz
 */
import java.util.ArrayList;
import mpi.*;

public class lab7 {
   
    final static int MASTER = 0;
    final static int FROM_MASTER = 1; /* setting a message type */
    final static int FROM_WORKER = 2; /* setting a message type */
    
    public static void main(String[] args) {
        MPI.Init(args);
        CollectiveMultiplication(args, false, 2500);
        MPI.Finalize();
//        int[] matrixSizes = {200, 400, 600, 800, 1000};
//        double[] serialResults = new double[matrixSizes.length];
//        for (int i=0; i<matrixSizes.length; i++){
//            ArrayList<Double> results = new ArrayList<>();
//            for (int j=0; j<4; j++){
//                results.add(serial(matrixSizes[i]));
//            }
//            serialResults[i] = results.stream().mapToDouble(a -> a).sum() / results.size();
//        } 
//        for (double result: serialResults)
//            System.out.println(result);
    }
    
    public static void CollectiveMultiplication(String[] args, boolean printResult, int matrixesSize){
        double[][] a = new double[matrixesSize][matrixesSize];
        double[][] b = new double[matrixesSize][matrixesSize];
        double[][] c = new double[matrixesSize][matrixesSize];
        double[] buffer_a = new double[matrixesSize*matrixesSize];
        double[] buffer_b = new double[matrixesSize*matrixesSize];
        double[] buffer_c = new double[matrixesSize*matrixesSize];
        int[] _matrixesSize = new int[1];
        int index_buf=0;
        _matrixesSize[0] = matrixesSize;
        final int rank = MPI.COMM_WORLD.Rank();
        final int numtasks = MPI.COMM_WORLD.Size();
        final int numworkers = numtasks;
        double startTime;
        double totalTime = 0;
        if (rank == MASTER) {
            for (int i=0; i<matrixesSize; i++){
                for (int j=0; j<matrixesSize; j++){
                    a[i][j]= j+1;
                }
            }
            for (int i=0; i<matrixesSize; i++){
                for (int j=0; j<matrixesSize; j++) {
                    b[i][j] = (i==j) ? 1 : 0;
                }
            }            
        
            for(int i=0; i<matrixesSize; i++){
                for (int j=0; j<matrixesSize; j++){
                    buffer_a[index_buf] = a[i][j];
                    buffer_b[index_buf] = b[i][j];
                    index_buf++;
                }
            }
            index_buf=0;
        }
                
        startTime = System.currentTimeMillis();
        MPI.COMM_WORLD.Bcast(_matrixesSize, 0, 1, MPI.INT, 0);
        double[] _receiveA = new double[(_matrixesSize[0]/numworkers)*(_matrixesSize[0])];
        double[] _receiveC = new double[(_matrixesSize[0]/numworkers)*(_matrixesSize[0])];
        int count = _matrixesSize[0]/numworkers;  
        
        MPI.COMM_WORLD.Scatter(buffer_a, 0, count*_matrixesSize[0], MPI.DOUBLE, 
                _receiveA, 0, count*_matrixesSize[0], MPI.DOUBLE, 0);
        MPI.COMM_WORLD.Bcast(buffer_b, 0, _matrixesSize[0]*_matrixesSize[0], MPI.DOUBLE, 0);
        for (int i=0; i<_matrixesSize[0]/numworkers; i++){
            for ( int j = 0 ; j<_matrixesSize[0] ; j++ ){
                _receiveC[i*_matrixesSize[0]+j] = 0;
                for ( int k = 0 ; k<_matrixesSize[0] ; k++ )
                    _receiveC[i*_matrixesSize[0]+j] += _receiveA[i*_matrixesSize[0]+k]*buffer_b[k*_matrixesSize[0]+j];
                        
            }
        }
        MPI.COMM_WORLD.Gather(_receiveC, 0, count*_matrixesSize[0], 
                MPI.DOUBLE, buffer_c, 0, count*_matrixesSize[0], MPI.DOUBLE, 0);
        totalTime = System.currentTimeMillis() - startTime;
        if (rank == MASTER) {
            for(int i=0; i<_matrixesSize[0]; i++){
                for (int j=0; j<_matrixesSize[0]; j++){
                    c[i][j] = buffer_c[index_buf];
                    index_buf++;
                }
            }
            if (printResult)
                printResult(c, _matrixesSize[0]);
            System.out.println(totalTime);
        }       
        
    }    
    
    public static void printResult(double[][] c, int matrixesSize){
        System.out.println("Result Matrix:\n");
        for (int k = 0; k < matrixesSize; k++){
            System.out.println("\n");
            for (int t = 0; t < matrixesSize; t++)
                System.out.printf("%6.2f ", c[k][t]);
        }
    }
    
    public static double serial (int matrixesSize){
        double[][] a = new double[matrixesSize][matrixesSize];
        double[][] b = new double[matrixesSize][matrixesSize];
        double[][] c = new double[matrixesSize][matrixesSize];
        long startTime;
        long totalTime;
        for (int i=0; i<matrixesSize; i++){
            for (int j=0; j<matrixesSize; j++){
                a[i][j]= 10;
            }
        }
        for (int i=0; i<matrixesSize; i++){
            for (int j=0; j<matrixesSize; j++) {
                b[i][j] = (i == j) ? 1 : 0;
            }
        }
        startTime = System.currentTimeMillis();
        for(int i = 0; i < matrixesSize; i++)
        {
            for(int j = 0; j < matrixesSize; j++)
            {
                for (int k = 0; k < matrixesSize; k++)
                {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        totalTime = System.currentTimeMillis() - startTime;
        return (double) totalTime;
    }    
}


