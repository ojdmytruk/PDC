import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private Random random = new Random();
    public static void main(String[] args) {
        int CAPACITY = 10;
        BlockingQueue<Integer> drop = new ArrayBlockingQueue<>(10);

        Thread A = new Thread(new ProducerThread(drop));
        Thread B = new Thread(new ProducerThread(drop));
        Thread C = new Thread(new ConsumerThread(drop));

        A.start();
        B.start();
        C.start();
    }
}
