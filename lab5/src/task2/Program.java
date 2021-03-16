package task2;

import javax.management.AttributeValueExp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        double avgQueueLength = 0;
        double avgFailureProbability = 0;

        Create create = new Create(0.1, 0.0, "Uniform");
        Process process = new Process(0.6, 0.0, "Uniform", 2, 1);

        ExecutorService modelPool = Executors.newSingleThreadExecutor();
        ArrayList<Model> tasks = new ArrayList<>();
        List<Future<Statistics>> statisticsList;
        for (int i=0; i<5; i++){
            tasks.add(new Model(process, create, 10000.0));
        }
        statisticsList = modelPool.invokeAll(tasks);
        modelPool.shutdown();
        System.out.println("\n--------------------Statistic result--------------------");

        for (int i = 0; i < statisticsList.size(); i++) {
            Future<Statistics> future = statisticsList.get(i);
            try {
                Statistics statistics = future.get();
                avgFailureProbability += statistics.getFailureProbability();
                avgQueueLength += statistics.getAvgQueue();
                System.out.println("EXPERIMENT " + (i+1) + ": Failure probability = " + statistics.getFailureProbability()
                        + "% ; Average queue length = " + statistics.getAvgQueue());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        avgFailureProbability /= statisticsList.size();
        avgQueueLength /= statisticsList.size();

        System.out.println("\n--------------------Average statistics--------------------");
        System.out.println("Average failure probability: " + avgFailureProbability);
        System.out.println("Average queue length: " + avgQueueLength);

    }
}
