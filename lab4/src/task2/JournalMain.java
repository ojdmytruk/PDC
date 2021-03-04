package task2;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class JournalMain {
    public static void main(String[] args) throws InterruptedException {

        ArrayList<Student> studentsIS71 = is71();
        ArrayList<Student> studentsIS72 = is72();
        ArrayList<Student> studentsIS73 = is73();

        Group group1 = new Group("IS-71", studentsIS71);
        Group group2 = new Group("IS-72", studentsIS72);
        Group group3 = new Group("IS-73", studentsIS73);

        ArrayList<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        Journal journal1 = new Journal(groups);
        long simpleExecTime = thirdLabImplementation(journal1);
        journal1.getMarks();

        Journal journal2 = new Journal(groups);
        long forkJoinExecTime = forkJoinImplementation(journal2);
        journal2.getMarks();
        System.out.println();
        System.out.println("Speed up: " + (double) simpleExecTime/forkJoinExecTime);
    }

    public static long thirdLabImplementation(Journal journal) throws InterruptedException {
        System.out.println("Simple threads creation:");
        long timeStart = System.currentTimeMillis();
        Thread lecturer = new Thread(new RateThread(journal, "Lecturer"));
        Thread as1 = new Thread(new RateThread(journal, "As1"));
        Thread as2 = new Thread(new RateThread(journal, "As2"));
        Thread as3 = new Thread(new RateThread(journal, "As3"));

        lecturer.start();
        as1.start();
        as2.start();
        as3.start();
        lecturer.join();
        as1.join();
        as2.join();
        as3.join();
        long execTime = System.currentTimeMillis() - timeStart;
        System.out.println("Execution time:" + execTime + " ms");
        return execTime;
    }

    public static long forkJoinImplementation(Journal journal){
        System.out.println("Fork/join:");
        long timeStart = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new RateTask(journal, " "));
        //pool.shutdown();
        long execTime = System.currentTimeMillis() - timeStart;
        System.out.println("Execution time:" + execTime + " ms");
        return execTime;
    }


    public static ArrayList<Student> is71(){
        ArrayList<Student> is71 = new ArrayList<>();
        String[] names = new String[]{"Alpaeva", "Anroshchuk", "Bolshoi"};
        for (int i=0; i<names.length; i++){
            Student student= new Student(names[i]);
            is71.add(student);
        }
        return is71;
    }

    public static ArrayList<Student> is72(){
        ArrayList<Student> is72 = new ArrayList<>();
        String[] names = new String[]{"Berezianko", "Borbenchuk", "Vasyliev"};
        for (int i=0; i<names.length; i++){
            Student student= new Student(names[i]);
            is72.add(student);
        }
        return is72;
    }

    public static ArrayList<Student> is73(){
        ArrayList<Student> is73 = new ArrayList<>();
        String[] names = new String[]{"Artemenko", "Boiko", "Val"};
        for (int i=0; i<names.length; i++){
            Student student= new Student(names[i]);
            is73.add(student);
        }
        return is73;
    }
}
