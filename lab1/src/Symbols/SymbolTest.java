package Symbols;

public class SymbolTest {
    public static void main(String[] args) throws InterruptedException {
        //test simple
        testSimple();

        //testInTurn();
    }

    static void testSimple(){
        Thread t1 = new Thread(new SymbolOneThread("-"));
        Thread t2 = new Thread(new SymbolTwoThread("|"));
        t2.start();
        t1.start();
    }

    static void testInTurn(){
        Symbol symbol = new Symbol();
        Thread t1 = new Thread(new InTurnOneThread(symbol));
        Thread t2 = new Thread(new InTurnTwoThread(symbol));
        t1.start();
        t2.start();
    }
}
