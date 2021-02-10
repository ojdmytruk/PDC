package Counter;

public class CounterTest {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);
        String [] methods = new String[] {"asynchronous", "synchronized method", "synchronized block", "locker"};

        for (int i=0; i<methods.length; i++){
            System.out.println(methods[i] + ":");
            Thread increment = new Thread(new IncrementThread(counter, methods[i]));
            Thread decrement = new Thread(new DecrementThread(counter, methods[i]));
            increment.start();
            decrement.start();
            increment.join();
            decrement.join();
            System.out.println(counter.getCounter());
            counter.setCounter(0);
        }

        System.out.println("SEVERAL THREADS");
        for (int i=0; i<methods.length; i++){
            System.out.println(methods[i] + ":");
            Thread increment1 = new Thread(new IncrementThread(counter, methods[i]));
            Thread decrement1 = new Thread(new DecrementThread(counter, methods[i]));
            Thread increment2 = new Thread(new IncrementThread(counter, methods[i]));
            Thread decrement2 = new Thread(new DecrementThread(counter, methods[i]));
            increment1.start();
            decrement1.start();
            increment2.start();
            decrement2.start();
            increment1.join();
            decrement1.join();
            increment2.join();
            decrement2.join();
            while (increment1.isAlive() || decrement1.isAlive() || increment2.isAlive() || decrement2.isAlive()){}
            System.out.println(counter.getCounter());
            counter.setCounter(0);
        }

    }
}
