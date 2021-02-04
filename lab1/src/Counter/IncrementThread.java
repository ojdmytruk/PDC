package Counter;

public class IncrementThread extends Thread{
    private Counter counter;

    public IncrementThread(Counter c){
        counter = c;
    }

    @Override
    public void run(){
        for(int i=1; i<100000; i++){
            counter.increment();
        }
    }
}
