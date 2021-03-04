import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ProducerThread implements Runnable {
    private BlockingQueue<Integer> drop;

    public ProducerThread(BlockingQueue<Integer> drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Integer objects[] = createObjects(100);

        for (int i = 0;
             i < objects.length;
             i++) {
            try {
                drop.put(objects[i]);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
    public Integer[] createObjects(int n){
        Integer[] importantInfo = new Integer[n];
        for (int i=0; i<n; i++){
            importantInfo[i] = i+1;
        }
        return importantInfo;
    }
}