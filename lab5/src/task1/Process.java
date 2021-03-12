package task1;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Process{

    private double delayAvg;
    private double delayDev;
    private String distribution;
    private int channelsNum;
    private int queueCapacity;
    private Model model;
    private Statistics statistics;


    public Process(double delayAvg, double delayDev, String distribution, int channelsNum, int queueCapacity){
        this.delayAvg = delayAvg;
        this.delayDev = delayDev;
        this.distribution = distribution;
        this.channelsNum = channelsNum;
        this.queueCapacity = queueCapacity;
    }

    public ArrayList<Channel> channels(){
        ArrayList<Channel> channels = new ArrayList<>();
        for(int i=0; i<channelsNum; i++){
            channels.add(new Channel("Channel" + (i+1), model, delayAvg, delayDev, distribution, statistics));
        }
        return channels;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
