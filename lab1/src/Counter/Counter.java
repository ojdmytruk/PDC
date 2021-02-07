package Counter;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private int counter ;
    private ReentrantLock lock = new ReentrantLock();

    public Counter(int c){
        this.counter = c;
    }

    public void asyncIncrement(){
        this.counter++;
    }
    public void asyncDecrement(){
        this.counter--;
    }

    public synchronized void syncIncrementMethod(){this.counter++;}
    public synchronized void syncDecrementMethod(){this.counter--;}

    public void syncIncrementBlock(){
        synchronized (this){
            this.counter++;
        }
    }
    public void syncDecrementBlock(){
        synchronized (this){
            this.counter--;
        }
    }

    public void lockIncrement(){
        lock.lock();
        try {
            this.counter++;
        } finally {
            lock.unlock();
        }
    }
    public void lockDecrement(){
        lock.lock();
        try {
            this.counter--;
        } finally {
            lock.unlock();
        }
    }

    public void setCounter(int c){
        this.counter = c;
    }

    public int getCounter(){
        return this.counter;
    }

}
