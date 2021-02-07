package Symbols;

public class InTurnOneThread implements Runnable{
    Symbol symbol;

    public InTurnOneThread(Symbol symbol){
        this.symbol = symbol;
    }


    @Override
    public void run() {
        for (int i=0; i<50; i++){
            symbol.setAndPrintOne();
        }
    }
}
