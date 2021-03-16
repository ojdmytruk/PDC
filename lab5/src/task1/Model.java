package task1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Model {

    public BlockingQueue<Task> processQueue;
    private double tCurrent;
    public double timeModeling;
    private Process process;
    private Create create;
    private ReentrantLock lock = new ReentrantLock();

    public Model(Process process, Create create, double timeModeling){
        this.process = process;
        this.create = create;
        this.timeModeling = timeModeling;
        this.processQueue = new ArrayBlockingQueue<>(process.getQueueCapacity());
    }

    public void simulation() {
        Statistics statistics = new Statistics(this);
        process.setModel(this);
        create.setModel(this);
        process.setStatistics(statistics);
        create.setStatistics(statistics);
        ArrayList<Channel> channels = process.channels();
        ExecutorService channelPool = Executors.newFixedThreadPool(channels.size());
        Thread creator = new Thread(create);
        creator.start();
        for (Channel channel: channels){
            channelPool.execute(channel);
        }
        channelPool.shutdown();
        while (!channelPool.isTerminated()){

        }
        statistics.printStatistics();
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
