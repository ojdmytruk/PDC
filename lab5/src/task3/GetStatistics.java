package task3;

public class GetStatistics implements Runnable{
    private Statistics statistics;

    public GetStatistics(Statistics statistics){
        this.statistics = statistics;
    }

    @Override
    public void run() {
        if (statistics.getStatisticThreadTCurrent() != statistics.model.getTCurrent()){
            statistics.setStatisticThreadTCurrent(statistics.model.getTCurrent());
            System.out.println("\nTime: " + statistics.model.getTCurrent());
            System.out.println("Average queue: " + statistics.getAvgQueue());
            System.out.println("Failure probability: " + statistics.getFailureProbability());
        }
        else return;
    }
}
