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


public class lab6 {
    final static int MASTER = 0;
    final static int FROM_MASTER = 1; /* setting a message type */
    final static int FROM_WORKER = 2; /* setting a message type */
    static double resultBlocking = 0;
    static double resultNonBlocking = 0;

    public static void main(String[] args) {
        int matrixesSize = 10;
        MPI.Init(args);   
        NonBlocking(args, true, 1000);
//        HelloWorld(args);
//        Blocking(args, true, matrixesSize);
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
    
    public static void HelloWorld(String[] args){
        int numtasks, taskid; 
        String hostname;
        numtasks = MPI.COMM_WORLD.Size();
        taskid = MPI.COMM_WORLD.Rank(); 
        hostname = MPI.Get_processor_name();
        System.out.println("Hello from task " + taskid + " on " + hostname + " !\n" );
        if (taskid == MASTER) 
            System.out.println("MASTER: Number of MPI tasks is: " + numtasks); 
    }

    public static void Blocking(String[] args, boolean printResult, int matrixesSize) {
        
        double[][] a = new double[matrixesSize][matrixesSize];
        double[][] b = new double[matrixesSize][matrixesSize];
        double[][] c = new double[matrixesSize][matrixesSize];
        int rows;
        int offset = 0;
        int[] _offset = new int[1];
        int[] _rows = new int[1];
        double startTime;
        double totalTime = 0;
        final int rank = MPI.COMM_WORLD.Rank();
        final int numtasks = MPI.COMM_WORLD.Size();
        if (numtasks < 2 ) { 
            System.out.println("Need at least two MPI tasks. Quitting...\n"); 
            System.exit(1); 
        } 
        int numworkers = numtasks - 1;
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
            int averow = matrixesSize/numworkers;
            int extra = matrixesSize%numworkers;
            startTime = System.currentTimeMillis();
            for (int dest=1; dest<=numworkers; dest++) {
                rows = (dest <= extra) ? averow + 1 : averow;
                _offset[0] = offset;
                _rows[0] = rows;
                double[][] a_buf = new double[rows][matrixesSize];
                for (int i = 0; i < rows; i++) {
                    a_buf[i] = a[offset+i];
                }                
                MPI.COMM_WORLD.Send(_offset, 0, 1,MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(_rows, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(a_buf, 0, rows, MPI.OBJECT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(b, 0,matrixesSize, MPI.OBJECT, dest, FROM_MASTER);
                offset += rows;
            }
            /* Receive results from worker tasks */
            for (int source=1; source<=numworkers; source++) {
                MPI.COMM_WORLD.Recv(_offset, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(_rows, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(c, _offset[0],_rows[0], MPI.OBJECT, source, FROM_WORKER);
            }
            totalTime = System.currentTimeMillis() - startTime;
            //System.out.println(totalTime);
            if (printResult)
                printResult(c, matrixesSize);


        }
        //******** worker *****************
        else {
            MPI.COMM_WORLD.Recv(_offset, 0,1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(_rows, 0,1, MPI.INT, MASTER, FROM_MASTER);
            a = new double[_rows[0]][matrixesSize];
            MPI.COMM_WORLD.Recv(a, 0, _rows[0], MPI.OBJECT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(b, 0, matrixesSize, MPI.OBJECT, MASTER, FROM_MASTER);
            for (int k=0; k<matrixesSize; k++) {
                for (int i = 0; i < _rows[0]; i++) {
                    c[i][k] = 0.0;
                    for (int j = 0; j < matrixesSize; j++) {
                        c[i][k] += a[i][j] * b[j][k];
                    }
                }
            }
            MPI.COMM_WORLD.Send(_offset, 0,1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(_rows, 0,1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(c, 0,_rows[0], MPI.OBJECT, MASTER, FROM_WORKER);
        }
//        return totalTime;
    }
    
    public static void NonBlocking(String[] args, boolean printResult, int matrixesSize) {
        double[][] a = new double[matrixesSize][matrixesSize];
        double[][] b = new double[matrixesSize][matrixesSize];
        double[][] c = new double[matrixesSize][matrixesSize];
        int[] _offset = new int[1];
        int[] _rows = new int[1];
        int offset = 0;
        double startTime;
        double totalTime=0;
        int rows;
        final int rank = MPI.COMM_WORLD.Rank();
        final int numtasks = MPI.COMM_WORLD.Size();
         if (numtasks < 2 ) { 
            System.out.println("Need at least two MPI tasks. Quitting...\n"); 
            System.exit(1); 
        } 
        int numworkers = numtasks-1;
        if (rank == MASTER) {
            for (int i=0; i<matrixesSize; i++){
                for (int j=0; j<matrixesSize; j++){
                    a[i][j]= j+1;
                }
            }
            for (int i=0; i<matrixesSize; i++){
                for (int j=0; j<matrixesSize; j++) {
                    b[i][j] = (i == j) ? 1 : 0;
                }
            }
            int averow = matrixesSize/numworkers;
            int extra = matrixesSize%numworkers;
            startTime = System.currentTimeMillis();
            for (int dest=1; dest<=numworkers; dest++) {
                rows = (dest <= extra) ? averow+1 : averow;
                _offset[0] = offset;
                _rows[0] = rows;
                double[][] a_buf = new double[rows][matrixesSize];
                for (int i = 0; i < rows; i++) {
                    a_buf[i] = a[offset+i];
                }     
                Request[] sendRequest = new Request[4];
                sendRequest[0] = MPI.COMM_WORLD.Isend(_offset, 0, 1,MPI.INT, dest, FROM_MASTER);
                sendRequest[1] = MPI.COMM_WORLD.Isend(_rows, 0, 1, MPI.INT, dest, FROM_MASTER);
                sendRequest[2] = MPI.COMM_WORLD.Isend(a_buf, 0, rows, MPI.OBJECT, dest, FROM_MASTER);
                sendRequest[3] = MPI.COMM_WORLD.Isend(b, 0, matrixesSize, MPI.OBJECT, dest, FROM_MASTER);
                Request.Waitall(sendRequest);
                offset = offset + rows;
            }
            /* Receive results from worker tasks */
            Request[] receiveRequest = new Request[numworkers+2];
            for (int source=1; source<=numworkers; source++) {
                MPI.COMM_WORLD.Recv(_offset, 0,1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(_rows, 0,1, MPI.INT, source, FROM_WORKER);
                receiveRequest[source-1] = MPI.COMM_WORLD.Irecv(c, _offset[0],_rows[0], MPI.OBJECT, source, FROM_WORKER);
//                System.out.println("Received results from task %d\n" +  source);
            }
            Request.Waitall(receiveRequest);
            totalTime = System.currentTimeMillis()-startTime;
            //System.out.println(totalTime);
            if (printResult)
                for(int i=0; i<matrixesSize; i++){
                    System.out.print(c[matrixesSize-1][i] + " ");
                }
                

        }
        //******** worker *****************
        else {
            MPI.COMM_WORLD.Recv(_offset, 0,1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(_rows, 0,1, MPI.INT, MASTER, FROM_MASTER);
            a = new double[_rows[0]][matrixesSize];
            MPI.COMM_WORLD.Recv(a, 0, _rows[0], MPI.OBJECT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(b, 0, matrixesSize, MPI.OBJECT, MASTER, FROM_MASTER);
            for (int k=0; k<matrixesSize; k++) {
                for (int i = 0; i < _rows[0]; i++) {
                    c[i][k] = 0.0;
                    for (int j = 0; j < matrixesSize; j++) {
                        c[i][k] += a[i][j] * b[j][k];
                    }
                }
            }
            Request[] sendBackRequest = new Request[3];
            sendBackRequest[0] = MPI.COMM_WORLD.Isend(_offset, 0,1, MPI.INT, MASTER, FROM_WORKER);
            sendBackRequest[1] = MPI.COMM_WORLD.Isend(_rows, 0,1, MPI.INT, MASTER, FROM_WORKER);
            sendBackRequest[2] = MPI.COMM_WORLD.Isend(c, 0,_rows[0], MPI.OBJECT, MASTER, FROM_WORKER);
            Request.Waitall(sendBackRequest);
        }
        //return totalTime;
    }
    
    public static void printResult(double[][] c, int matrixesSize){
        System.out.println("Result Matrix:\n");
            for (int k = 0; k < matrixesSize; k++)
            {
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
                a[i][j]= j+1;
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
            

