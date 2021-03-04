import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ConsumerThread implements Runnable{
    private BlockingQueue<Integer> drop;

    public ConsumerThread(BlockingQueue<Integer> drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            for (int object = drop.take();
                 ! (object ==1111111);
                 object = drop.take()) {
                System.out.format("Object: %s%n", object);
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
