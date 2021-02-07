package Symbols;

public class Symbol {
    private String symbol = "-";

    public synchronized void setAndPrintOne(){
        while (symbol == "|") {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        symbol = "|";
        System.out.println(symbol);
        notify();
    }

    public synchronized void setAndPrintTwo(){
        while (symbol == "-") {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        symbol = "-";
        System.out.println(symbol);
        notify();
    }
}
