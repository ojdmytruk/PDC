package Symbols;

public class SymbolThread extends Thread {
    private String symbol;
    private int sleapTime;

    public SymbolThread(String s, int sleap){
        this.symbol = s;
        this.sleapTime = sleap;
    }

    @Override
    public void run(){
        try{
            for(int i=1; i<50; i++){
                System.out.println(symbol);
            }
            Thread.sleep(sleapTime);

        } catch(InterruptedException ex){

        }
    }
}
