package PrConBlocking;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private BlockingQueue<Integer> drop;

    public Producer(BlockingQueue<Integer> drop) {
        this.drop = drop;
    }


    public void run() {
//        String importantInfo[] = {
//                "Mares eat oats",
//                "Does eat oats",
//                "Little lambs eat ivy",
//                "A kid will eat ivy too"
//        };
        Integer importantInfo[] = importantInfoArray(100);
//        String importantInfo[] = importantInfoArray(1000);
//        String importantInfo[] = importantInfoArray(5000);
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            try {
                drop.put(importantInfo[i]);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
//            try {
//                Thread.sleep(random.nextInt(5000));
//            } catch (InterruptedException e) {}
        }
        //drop.put("DONE");
    }

    public Integer[] importantInfoArray(int n){
        Integer[] importantInfo = new Integer[n];
        for (int i=0; i<n; i++){
            importantInfo[i] = i+1;
        }
        return importantInfo;
    }
}
