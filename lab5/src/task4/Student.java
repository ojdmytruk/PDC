package task4;

import java.util.ArrayList;
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

    public void rateSync(double mark) {
        lock.lock();
        try {
            this.marks.add(mark);
            weekCounter++;
        }finally {
            lock.unlock();
        }

    }

    public void rateAsync(double mark){
        this.marks.add(mark);
        weekCounter++;
    }

    public ArrayList<Double> getMarks(){
        return this.marks;
    }

    public String getName(){
        return this.name;
    }

    public double getTotalMarkSync(){
        double getMark;
        lock.lock();
        try {
            getMark = this.marks.stream().mapToDouble(mark -> mark).sum(); // /18;
        }finally {
            lock.unlock();
        }
        return getMark;
        //return this.marks.stream().mapToDouble(mark -> mark).sum();

    }

    public double getTotalMarkAsync(){
        return this.marks.stream().mapToDouble(mark -> mark).sum();
    }

    public synchronized int getWeekCounter(){
        return weekCounter;
    }

}
