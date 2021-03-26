import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {


        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Клієнт приєднано до сервера");

            while (!socket.isOutputShutdown()) {

                if (br.ready()) {

                    //System.out.println("Дані знаходяться на: клієнті('к')/сервері('с')?");
                    String clientCommand = br.readLine();
                    int matrixSize =  Integer.valueOf(br.readLine());
                    int numOfThreads =  Integer.valueOf(br.readLine());

                    if (clientCommand.equals("с")) {
                        long startTime = System.nanoTime();

                        out.writeObject(clientCommand);
                        out.flush();

                        out.writeObject(matrixSize);
                        out.flush();

                        out.writeObject(numOfThreads);
                        out.flush();

                        processServerAnswer(startTime, in);
                    }

                    if (clientCommand.equals("к")) {
                        double[][] matrixA = MatrixGenerator.lineElementMatrixGenerator(matrixSize);
                        double[][] matrixB = MatrixGenerator.identityMatrixGenerator(matrixSize);

                        long startTime = System.nanoTime();

                        out.writeObject(clientCommand);
                        out.flush();

                        out.writeObject(matrixSize);
                        out.flush();

                        out.writeObject(numOfThreads);
                        out.flush();


                        out.writeObject(matrixA);
                        out.flush();

                        out.writeObject(matrixB);
                        out.flush();

                        processServerAnswer(startTime, in);
                    }

                    System.out.println();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void processServerAnswer(long startTime, ObjectInputStream in) throws IOException, ClassNotFoundException {
        System.out.println("Очікування результату з сервера");
        double[][] matrix = (double[][]) in.readObject();

        long endTime = System.nanoTime();
        System.out.println("Результат:");
        Result result = new Result(matrix);
        result.printMatrix();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Час виконання: " + duration + " ms");
    }
}
