package task1;

import java.util.concurrent.locks.ReentrantLock;

public class Statistics {
    int disposed = 0;
    int failed = 0;
    double avgQueue = 0;

    double avgQueueCount = 0;
    long tCurrent = 0L;
    long tNext = 0L;
    long time = 0L;
    ReentrantLock lock = new ReentrantLock();
    Model model;

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
        if(disposed != 0 && failed != 0) {
            return (double) failed / (disposed + failed);
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

    public void printStatistics(){
        System.out.println();
        System.out.println("--------------------Statistic result--------------------");
        System.out.println("Failure probability: " + getFailureProbability()*100 + "%");
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
