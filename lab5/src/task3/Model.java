package task3;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Model {

    public BlockingQueue<Task> processQueue;
    private double tCurrent;
    public double timeModeling;
    public Dispose dispose = new Dispose();
    private Process process;
    private Create create;
    private ReentrantLock lock = new ReentrantLock();

    public Model(Process process, Create create, double timeModeling){
        this.process = process;
        this.create = create;
        this.timeModeling = timeModeling;
        this.processQueue = new ArrayBlockingQueue<>(process.getQueueCapacity());
    }

    public void simulation() throws InterruptedException {
        Statistics statistics = new Statistics(this);
        process.setModel(this);
        create.setModel(this);
        process.setStatistics(statistics);
        create.setStatistics(statistics);
        ArrayList<Channel> channels = process.channels();
        ExecutorService channelPool = Executors.newFixedThreadPool(channels.size());

        ExecutorService statisticsPool = Executors.newFixedThreadPool(1);
//        final ScheduledFuture<?> beepHandler =
//                statisticsPool.scheduleAtFixedRate(new Statistics(this), 1, 2, TimeUnit.NANOSECONDS);
        Thread creator = new Thread(create);
        creator.start();
//        Thread stat = new Thread(new Statistics(this));
//        stat.start();

        for (Channel channel: channels){
            channelPool.execute(channel);
        }

        channelPool.shutdown();
//        statisticsPool.scheduleAtFixedRate(new GetStatistics(statistics), 2, 100, TimeUnit.SECONDS);

        while (!channelPool.isTerminated()){
            statisticsPool.execute(new GetStatistics(statistics));
        }
        statisticsPool.shutdownNow();
    }

    public void incrementTCurrent (double delay){
        lock.lock();
        try {
            tCurrent+=delay;
        }finally {
            lock.unlock();
        }
    }

    public synchronized double getTCurrent() {
        return tCurrent;
    }
}
