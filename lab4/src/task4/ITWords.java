package task4;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ITWords {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private static ArrayList<ArrayList<String>> documentsITWordsLists = new ArrayList<>();

    static List<String> itWordsToCompare = Arrays.asList("data", "internet",
            "computer", "software", "database", "electronics", "information", "system", "engineering",
            "informatics", "network", "computing", "technology");

    public static void main(String[] args) throws IOException {
        ITWords itWords = new ITWords();
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\teraz\\OneDrive\\Рабочий стол\\PDC\\lab4\\src\\task4\\Documents"));
        ArrayList<DocumentResult> documentResults = itWords.processITWords(folder);
        for (DocumentResult doc: documentResults){
            System.out.println("Document: " + doc.getDocumentName());
            System.out.println("Match percent :" + (double) doc.itWords.size()/itWordsToCompare.size()*100 + "%");
            for(String word: doc.itWords){
                System.out.println(word);
            }
        }
    }

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    ArrayList<DocumentResult> processITWords(Folder folder){

        return forkJoinPool.invoke(new FolderHashTask(folder));
    }

    public ArrayList<DocumentResult> getWordsFromFile(Document document) {
        Set<String> uniqueWordsInText = new HashSet<>();
        ArrayList<DocumentResult> result = new ArrayList<>();
        DocumentResult documentResult = new DocumentResult(document.getName());
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (itWordsToCompare.contains(word))
                    uniqueWordsInText.add(word.toLowerCase(Locale.ROOT));
            }
        }
        documentResult.itWords.addAll(uniqueWordsInText);
        result.add(documentResult);
        return result;
    }

    class DocumentHashTask extends RecursiveTask<ArrayList<DocumentResult>>{
        private final Document document;

        DocumentHashTask(Document document) {
            this.document = document;
        }

        @Override
        protected ArrayList<DocumentResult> compute() {
            return getWordsFromFile(document);
        }
    }

    class FolderHashTask extends RecursiveTask<ArrayList<DocumentResult>>{
        private final Folder folder;

        FolderHashTask(Folder folder) {
            this.folder = folder;
        }

        @Override
        protected ArrayList<DocumentResult> compute() {
            ArrayList<DocumentResult> result = new ArrayList<>();
            LinkedList<RecursiveTask<ArrayList<DocumentResult>>> forks = new LinkedList<>();
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
            for (RecursiveTask<ArrayList<DocumentResult>> task : forks) {
                result.addAll(task.join());
            }
            return result;
        }
    }
}
