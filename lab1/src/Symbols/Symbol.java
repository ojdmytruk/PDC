package Symbols;

public class Symbol {
    public static void main(String[] args) throws InterruptedException {
        SymbolThread thread1 = new SymbolThread("-", 0);
        SymbolThread thread2 = new SymbolThread("|",0);
        thread1.start();
        thread2.start();
    }
}
