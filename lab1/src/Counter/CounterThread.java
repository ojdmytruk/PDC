package Counter;

public class CounterThread extends Thread{
    private Counter counter;

    public CounterThread(Counter c){
        counter = c;
    }

    @Override
    public void run(){
        synchronized (counter){
            for(int i=1; i<100000; i++){
                counter.decrement();
            }
        }

    }

}
