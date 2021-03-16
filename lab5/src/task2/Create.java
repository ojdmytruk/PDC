package task2;

public class Create implements Runnable{

    private double delayAvg;
    private double delayDev;
    private String distribution;
    private Model model;
    private Statistics statistics;

    public Create(double delayAvg, double delayDev, String distribution){
        this.delayAvg = delayAvg;
        this.delayDev = delayDev;
        this.distribution = distribution;
    }

    @Override
    public void run() {
        while (model.getTCurrent()<model.timeModeling){
            if (model.processQueue.offer(new Task())){
                statistics.avgQueueCount();
            }else {
                statistics.incrementFailed();
            }
            double delay = Distributions.delay(distribution, delayAvg, delayDev);
            model.incrementTCurrent(delay);
            try {
                Thread.sleep((long) delay);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
