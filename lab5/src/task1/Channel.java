package task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Channel implements Runnable {

    private String name;
    private double delayAvg;
    private double delayDev;
    private String distribution;
    private Model model;
    private double delay = 0;
    private Statistics statistics;


    public Channel(String name, Model model, double delayAvg, double delayDev, String distribution,
                   Statistics statistics){
        this.name = name;
        this.model = model;
        this.delayAvg = delayAvg;
        this.delayDev = delayDev;
        this.distribution = distribution;
        this.statistics = statistics;
    }

    @Override
    public void run() {
        while (model.getTCurrent()< model.timeModeling){
            try {
                model.processQueue.poll(1, TimeUnit.NANOSECONDS);
                statistics.avgQueueCount();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            statistics.incrementDisposed();
            System.out.println("Action in " + name + ", time=" + model.getTCurrent());
            delay = Distributions.delay(distribution, delayAvg, delayDev);
            model.incrementTCurrent(delay);
            try {
                Thread.sleep((long) delay);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
