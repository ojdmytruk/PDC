package task2;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Student {

    private String name;
    private ArrayList<Double> marks;
    private  final ReentrantLock lock = new ReentrantLock();

    public Student(String name){
        this.name = name;
        this.marks = new ArrayList<>();
    }

    public void rateSync(double mark) {
        lock.lock();
        try {
            this.marks.add(mark);
        }finally {
            lock.unlock();
        }
    }

    public void rateAsync(double mark){
        this.marks.add(mark);
    }

    public ArrayList<Double> getMarks(){
        ArrayList<Double> marksList;
        lock.lock();
        try {
            marksList = this.marks; // /18;
        }finally {
            lock.unlock();
        }
        return marksList;
    }

    public String getName(){
        return this.name;
    }

    public double getTotalMarkSync(){
        double getMark;
        lock.lock();
        try {
            getMark = this.marks.stream().mapToDouble(mark -> mark).sum();
        }finally {
            lock.unlock();
        }
        return getMark;
    }

    public double getTotalMarkAsync(){
        return this.marks.stream().mapToDouble(mark -> mark).sum();
    }

}
