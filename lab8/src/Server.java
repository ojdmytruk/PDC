import java.io.*;
import java.net.*;

public class Server {

    private static Socket client;
    private static ServerSocket server;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) throws IOException {

        try {
            server = new ServerSocket(5000);
            System.out.print("Сервер запущено");

            client = server.accept();
            System.out.print("\nЗ'єднання підтверджено");

            try {
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());

                while (client.isConnected()) {

                    System.out.println("\nСервер очікує дані клієнта");

                    String side = (String) in.readObject();
                    int matrixSize = (int) in.readObject();
                    int numOfThreads = (int) in.readObject();

                    processClientRequest(side, matrixSize, numOfThreads);

                    out.flush();
                }

            } finally {
                client.close();
                in.close();
                out.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\nShutdown");
            server.close();
        }


    }

    private static void processClientRequest(String side, int matrixSize, int numOfThreads) throws IOException, ClassNotFoundException {
        if (side.equals("с")) {
            System.out.println("Розпочато обчислення (дані на сервері)");

            double[][] matrixA = MatrixGenerator.lineElementMatrixGenerator(matrixSize);
            double[][] matrixB = MatrixGenerator.identityMatrixGenerator(matrixSize);

            BlockStripedMultiplication multiplication = new BlockStripedMultiplication(matrixA, matrixB, numOfThreads);
            Result result = multiplication.multiplyMatrixParallelNThreads();

            System.out.println("Завершено обчислення (дані на сервері)");

            out.writeObject(result.getMatrix());
        }

        if (side.equals("к")) {
            System.out.println("Розпочато обчислення (дані на клієнті)");

            double[][] matrixA = (double[][]) in.readObject();
            double[][] matrixB = (double[][]) in.readObject();

            BlockStripedMultiplication multiplication = new BlockStripedMultiplication(matrixA, matrixB, numOfThreads);
            Result result = multiplication.multiplyMatrixParallelNThreads();

            System.out.println("Закінчено обчислення (дані на клієнті)");

            out.writeObject(result.getMatrix());
        }
    }
}