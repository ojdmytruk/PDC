package task3;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class CommonWords {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static void main(String[] args) throws IOException {
        CommonWords words = new CommonWords();
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\teraz\\OneDrive\\Рабочий стол\\PDC\\lab4\\src\\task4\\Documents"));
        ArrayList<String> uniqueWords = words.uniqueWords(folder);
//        for (String commonWord: uniqueWords){
//            System.out.println(commonWord);
//        }
        ArrayList<String> nonCommonWords = words.nonCommonWords(folder, uniqueWords);
        ArrayList<String> commonWords = words.commonWords(uniqueWords, nonCommonWords);
        System.out.println("Common words:");
        for (String commonWord: commonWords){
            System.out.println(commonWord);
        }
    }

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    ArrayList<String> uniqueWords(Folder folder) {
        return forkJoinPool.invoke(new FolderHashTask(folder));
    }

    ArrayList<String> nonCommonWords(Folder folder, ArrayList<String> uniqueWords){
        return forkJoinPool.invoke(new FolderNonCommonSearchTask(folder, uniqueWords));
    }

    public ArrayList<String> getWordsFromFile(Document document) {
        Set<String> uniqueWordsInText = new HashSet<>();
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (word.matches("^[a-zA-Z]*$") && word.length()!=1){
                    uniqueWordsInText.add(word.toLowerCase(Locale.ROOT));
                }
            }
        }
        return new ArrayList<String>() {{
            addAll(uniqueWordsInText);
        }};
    }

    class DocumentHashTask extends RecursiveTask<ArrayList<String>>{
        private final Document document;

        DocumentHashTask(Document document) {
            this.document = document;
        }

        @Override
        protected ArrayList<String> compute() {
            return getWordsFromFile(document);
        }
    }

    class FolderHashTask extends RecursiveTask<ArrayList<String>>{
        private final Folder folder;

        FolderHashTask(Folder folder) {
            this.folder = folder;
        }

        @Override
        protected ArrayList<String> compute() {
            ArrayList<String> commonWords = new ArrayList<>();
            LinkedList<RecursiveTask<ArrayList<String>>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderHashTask task = new FolderHashTask(subFolder);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentHashTask task = new DocumentHashTask(document);
                forks.add(task);
                task.fork();
            }
            for (RecursiveTask<ArrayList<String>> task : forks) {
                commonWords.addAll(task.join());
            }
            commonWords = (ArrayList<String>) commonWords.stream().distinct().collect(Collectors.toList());
            Collections.sort(commonWords);
            return commonWords;
        }
    }

    public ArrayList<String> findNonCommon(Document document, ArrayList<String> otherDocWords){
        ArrayList<String> nonCommonWords = new ArrayList<>();
        for(String word: otherDocWords)
        {
            if(!getWordsFromFile(document).contains(word.toLowerCase(Locale.ROOT)))
            {
                nonCommonWords.add(word);
            }
        }
        return nonCommonWords;
    }

    public ArrayList<String> commonWords(ArrayList<String> allWords, ArrayList<String> nonCommonWords){
        ArrayList<String> commonWords = allWords;
        for (String nonCommonWord: nonCommonWords){
            commonWords.remove(nonCommonWord);
        }

        return commonWords;
    }

    class DocumentNonCommonSearchTask extends RecursiveTask<ArrayList<String>> {
        private final Document document;
        public ArrayList<String> uniqueWords;

        DocumentNonCommonSearchTask(Document document, ArrayList<String> uniqueWords) {
            super();
            this.document = document;
            this.uniqueWords = uniqueWords;
        }

        @Override
        protected ArrayList<String> compute() {
            return findNonCommon(document, uniqueWords);
        }
    }

    class FolderNonCommonSearchTask extends RecursiveTask<ArrayList<String>> {
        private final Folder folder;
        public ArrayList<String> uniqueWords;

        FolderNonCommonSearchTask(Folder folder, ArrayList<String> uniqueWords) {
            this.folder = folder;
            this.uniqueWords = uniqueWords;
        }

        @Override
        protected ArrayList<String> compute() {
            ArrayList<String> nonCommon = new ArrayList<>();
            LinkedList<RecursiveTask<ArrayList<String>>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderNonCommonSearchTask task = new FolderNonCommonSearchTask(subFolder, uniqueWords);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentNonCommonSearchTask task = new DocumentNonCommonSearchTask(document, uniqueWords);
                forks.add(task);
                task.fork();
            }
            for (RecursiveTask<ArrayList<String>> task : forks) {
                nonCommon.addAll(task.join());
            }

            return nonCommon;
        }
    }

}
