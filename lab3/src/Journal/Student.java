package Journal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Student {

    private String name;
    private ArrayList<Double> marks;
    private  final ReentrantLock lock = new ReentrantLock();
    private int weekCounter = 0;

    public Student(String name){
        this.name = name;
        this.marks = new ArrayList<>();
    }

    public void rate(double mark) {
        synchronized (marks){
            this.marks.add(mark);
            weekCounter++;
        }
//        lock.lock();
//        try {
//            this.marks.add(mark);
//        }finally {
//            lock.unlock();
//        }
        //this.marks.add(mark);

    }

    public ArrayList<Double> getMarks(){
        return this.marks;
    }

    public String getName(){
        return this.name;
    }

    public double getTotalMark(){

        return this.marks.stream().mapToDouble(mark -> mark).sum()/18;
    }

    public synchronized int getWeekCounter(){
        return weekCounter;
    }

}
