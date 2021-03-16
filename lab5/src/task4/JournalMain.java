package task4;

import java.util.ArrayList;

public class JournalMain {
    private long avgTime = 0;
    private int ratesCount = 0;

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

        Journal journal = new Journal(groups);
        JournalMain main = new JournalMain();

        Thread lecturer = new Thread(new RateThread(journal, "Lecturer", main));
        Thread as1 = new Thread(new RateThread(journal, "As1", main));
        Thread as2 = new Thread(new RateThread(journal, "As2", main));
        Thread as3 = new Thread(new RateThread(journal, "As3", main));

        lecturer.start();
        as1.start();
        as2.start();
        as3.start();

        lecturer.join();
        as1.join();
        as3.join();

        System.out.println("\nAverage delay: " + main.getAvgTime() / main.ratesCount);
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

    public synchronized void incrementAvgTime(long time){
        this.avgTime += time;
        this.ratesCount ++;
    }

    public long getAvgTime() {
        return avgTime;
    }

    public int getRatesCount() {
        return ratesCount;
    }
}
