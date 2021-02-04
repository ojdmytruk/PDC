package Counter;

public class Counter {
    private int counter = 0;

    public Counter(int c){
        this.counter = c;
    }

    public void increment(){
        counter++;
    }

    public void decrement(){
        counter--;
    }
}
