package task1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordLengthCounter {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private static ArrayList<Integer> minValuesGlobal = new ArrayList<>();
    private static ArrayList<Integer> maxValuesGlobal = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        WordLengthCounter wordLengthCounter = new WordLengthCounter();
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\teraz\\OneDrive\\Рабочий стол\\PDC\\lab4\\src\\ForkJoinWordLength\\Documents"));
        double[] res = wordLengthCounter.countWordLengthOnSingleThread(folder);
        int recMin = Collections.min(minValuesGlobal);
        double recAvg = wordLengthCounter.countWordLengthOnSingleThread(folder)[1] / 7;
        int recMax = Collections.max(maxValuesGlobal);

        double pMin = wordLengthCounter.countWordLengthInParallel(folder)[0];
        double pAvg = wordLengthCounter.countWordLengthInParallel(folder)[1];
        double pMax = wordLengthCounter.countWordLengthInParallel(folder)[2];

        double recTime = (double) experimentRecursive(folder);
        double parTime = (double) experimentParallel(folder);

        System.out.format("%10s%5s%5s%32s%32s", "Method", "Min", "Max", "Avg", "Time");
        System.out.println();
        System.out.format("%10s%5d%5d%32f%32f", "Recursive", recMin, recMax, recAvg, recTime);
        System.out.println();
        System.out.format("%10s%5d%5d%32f%32f", "ForkJoin", (int) pMin, (int) pMax, pAvg, parTime);
        System.out.println();
        System.out.println("Speed up: " + recTime/parTime);
        System.out.println("Words recursive: " + (int) wordLengthCounter.countWordLengthOnSingleThread(folder)[3]);
        System.out.println("Words fork/join: " + (int) wordLengthCounter.countWordLengthInParallel(folder)[3]);
    }

    double[] countWordLengthInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    double[] wordStat(Document document) {
        double[] wordStatistics = new double[4];
        double avgFile = 0;
        double min = Double.MAX_VALUE;
        double max = 0;
        int count = 0;
        for (String line : document.getLines()) {
            double avgLine = 0;
            int wordCount = 0;

            for (String word : wordsIn(line)) {
                if (word.length()!=0){
                    if (word.length()<min)
                        min = word.length();
                    else if (word.length()>max)
                        max = word.length();
                    avgLine += (double) word.length();
                    wordCount++;
                    count++;
                }
            }

            if (wordCount!=0){
                avgFile += avgLine / wordCount;
            }
        }
        wordStatistics[3] = (double) count;
        wordStatistics[0] = min;
        wordStatistics[1] = avgFile / document.getLines().size();
        wordStatistics[2] = max;
        return wordStatistics;
    }

    class DocumentSearchTask extends RecursiveTask<double[]> {
        private final Document document;
        DocumentSearchTask(Document document) {
            super();
            this.document = document;
        }
        @Override
        protected double[] compute() {
            return wordStat(document);
        }
    }

    class FolderSearchTask extends RecursiveTask<double[]> {
        private final Folder folder;
        FolderSearchTask(Folder folder) {
            super();
            this.folder = folder;
        }
        @Override
        protected double[] compute() {
            double count = 0;
            ArrayList<Double> minElements = new ArrayList<>();
            ArrayList<Double> maxElements = new ArrayList<>();
            double[] result = new double[4];
            List<RecursiveTask<double[]>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderSearchTask task = new FolderSearchTask(subFolder);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentSearchTask task = new DocumentSearchTask(document);
                forks.add(task);
                task.fork();
            }
            for (RecursiveTask<double[]> task : forks) {
                minElements.add(task.join()[0]);
                count = count + task.join()[1];
                maxElements.add(task.join()[2]);
                result[3] += task.join()[3];
            }
            result[0] = Collections.min(minElements);
            result[1] = count / forks.size();
            result[2] = Collections.max(maxElements);
            return result;
        }
    }

    double[] countWordLengthOnSingleThread(Folder folder) {

        double[] result = new double[4];
        ArrayList<Integer> minValues = new ArrayList<>();
        ArrayList<Integer> maxValues = new ArrayList<>();
        for (Folder subFolder : folder.getSubFolders()) {
            minValuesGlobal.add((int)countWordLengthOnSingleThread(subFolder)[0]);
            maxValuesGlobal.add((int)countWordLengthOnSingleThread(subFolder)[2]);
            result[1] += countWordLengthOnSingleThread(subFolder)[1];
            result[3] += countWordLengthOnSingleThread(subFolder)[3];
        }
        for (Document document : folder.getDocuments()) {
            minValues.add((int) wordStat(document)[0]);
            result[1] += wordStat(document)[1];
            maxValues.add((int) wordStat(document)[2]);
            result[3] += wordStat(document)[3];
        }
        result[0] = Collections.min(minValues);
        //result[1] /= folder.getDocuments().size();
        result[2] = Collections.max(maxValues);
        minValuesGlobal.add((int)result[0]);
        maxValuesGlobal.add((int)result[2]);
        return result;
    }

    static long experimentRecursive(Folder folder){
        WordLengthCounter wrl = new WordLengthCounter();
        long avgTime = 0;
        for (int i=0; i<4; i++)
        {
            long start = System.currentTimeMillis();
            wrl.countWordLengthOnSingleThread(folder);
            avgTime += System.currentTimeMillis() - start;
        }
        return avgTime/4;
    }

    static long experimentParallel(Folder folder){
        WordLengthCounter wrl = new WordLengthCounter();
        long avgTime = 0;
        for (int i=0; i<4; i++)
        {
            long start = System.currentTimeMillis();
            wrl.countWordLengthInParallel(folder);
            avgTime += System.currentTimeMillis() - start;
        }
        return avgTime/4;
    }
}
