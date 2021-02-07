package Counter;

public class DecrementThread implements Runnable{
    private Counter counter;
    private String method;

    public DecrementThread(Counter counter, String method){
        this.counter = counter;
        this.method = method;
    }

    @Override
    public void run() {
        switch (method){
            case ("asynchronous"):
                for (int i=0; i<100000; i++){
                    counter.asyncDecrement();
                }
                break;
            case ("synchronized method"):
                for (int i=0; i<100000; i++){
                    counter.syncDecrementMethod();
                }
                break;
            case ("synchronized block"):
                for (int i=0; i<100000; i++){
                    counter.syncDecrementBlock();
                }
            case ("locker"):
                for (int i=0; i<100000; i++){
                    counter.lockDecrement();
                }

        }

    }
}
