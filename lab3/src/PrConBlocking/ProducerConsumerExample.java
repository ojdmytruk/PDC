package PrConBlocking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> drop = new SynchronousQueue<Integer>();

        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
