package task2;

import java.util.concurrent.locks.ReentrantLock;

public class Statistics {
    private int disposed = 0;
    private int failed = 0;
    private double avgQueue = 0;

    private double avgQueueCount = 0;
    private long tCurrent = 0L;
    private long tNext = 0L;
    private long time = 0L;
    private ReentrantLock lock = new ReentrantLock();
    private Model model;

    public Statistics(Model model){
        this.model = model;
    }

    synchronized void incrementDisposed(){
        this.disposed++;
    }

    synchronized void incrementFailed(){
        this.failed++;
    }

    synchronized double getFailureProbability(){
        if(this.getDisposed() != 0 && this.getFailed() != 0) {
            return (double) this.getFailed() / (this.getDisposed() + this.getFailed()) * 100;
        }
        else{
            return 0;
        }
    }

    public void avgQueueCount() {
        lock.lock();
        try {
            tCurrent = tNext;
            tNext = System.currentTimeMillis();
            time += (tNext - tCurrent);
            avgQueueCount += ((tNext - tCurrent) * model.processQueue.size()); //сколько времени простояла в очереди * на размер очереди
            avgQueue = avgQueueCount / (double) time;
        } finally {
            lock.unlock();
        }
    }

    public void printStatistics(){
        System.out.println();
        System.out.println("--------------------Statistic result--------------------");
        System.out.println("Failure probability: " + getFailureProbability() + "%");
        System.out.println("Average queue length: " + avgQueue);
    }

    public double getAvgQueue() {
        return avgQueue;
    }

    public int getDisposed() {
        return disposed;
    }

    public int getFailed() {
        return failed;
    }
}
