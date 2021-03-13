package task2;

import java.util.concurrent.locks.ReentrantLock;

public class Statistics{
    int disposed = 0;
    int failed = 0;
    double avgQueue = 0;

    double avgQueueCount = 0;
    long tCurrent = 0L;
    long tNext = 0L;
    long time = 0L;
    ReentrantLock lock = new ReentrantLock();
    public Model model;
    private double statisticThreadTCurrent = 0;

    public Statistics(Model model){
        this.model = model;
    }

    public void setStatisticThreadTCurrent(double tCurrent){
        lock.lock();
        try {
            this.statisticThreadTCurrent = tCurrent;
        }finally {
            lock.unlock();
        }
    }

    public synchronized double getStatisticThreadTCurrent(){
        return this.statisticThreadTCurrent;
    }

    synchronized void incrementDisposed(){
        this.disposed++;
    }

    synchronized void incrementFailed(){
        this.failed++;
    }

    synchronized double getFailureProbability(){
        if(disposed != 0 && failed != 0) {
            return (double) getFailed() / (getDisposed() + getFailed());
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
            avgQueueCount += ((tNext - tCurrent) * model.processQueue.size());
            avgQueue = avgQueueCount / (double) time;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void printStatistics(){
        System.out.println();
        System.out.println("--------------------Statistic--------------------");
        System.out.println("Time: " + model.getTCurrent());
        System.out.println("Failure probability: " + getFailureProbability()*100 + "%");
        System.out.println("Average queue length: " + getAvgQueue());
    }

    public synchronized double getAvgQueue() {
        return avgQueue;
    }

    public synchronized int getDisposed() {
        return disposed;
    }

    public synchronized int getFailed() {
        return failed;
    }

}
