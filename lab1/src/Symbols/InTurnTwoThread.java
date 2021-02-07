package Symbols;

public class InTurnTwoThread implements Runnable{
    Symbol symbol;

    public InTurnTwoThread(Symbol symbol){
        this.symbol = symbol;
    }


    @Override
    public void run() {
        for (int i=0; i<50; i++){
            symbol.setAndPrintTwo();
        }
    }
}
