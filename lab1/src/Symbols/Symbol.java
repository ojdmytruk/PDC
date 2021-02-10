package Symbols;

public class Symbol {
    private String symbol = "-";

    public synchronized void setAndPrintOne(){
        while (symbol.equals("|")) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        symbol = "|";
        System.out.print(symbol);
        notify();
    }

    public synchronized void setAndPrintTwo(){
        while (symbol.equals("-")) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        symbol = "-";
        System.out.print(symbol);
        notify();
    }
}
