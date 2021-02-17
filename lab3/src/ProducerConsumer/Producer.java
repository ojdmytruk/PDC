package ProducerConsumer;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
//        String importantInfo[] = {
//                "Mares eat oats",
//                "Does eat oats",
//                "Little lambs eat ivy",
//                "A kid will eat ivy too"
//        };
        String importantInfo[] = importantInfoArray(100);
//        String importantInfo[] = importantInfoArray(1000);
//        String importantInfo[] = importantInfoArray(5000);
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }

    public String[] importantInfoArray(int n){
        String[] importantInfo = new String[n];
        for (int i=0; i<n; i++){
            importantInfo[i] = String.valueOf(i+1);
        }
        return importantInfo;
    }
}
