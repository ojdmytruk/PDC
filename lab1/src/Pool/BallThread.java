package Pool;

public class BallThread extends Thread {
    private Ball b;
    int[] xCoordinates;
    int[] yCoordinates;
    private int movesCount = 10000;


    public BallThread(Ball ball, int[] x, int[] y){
        b = ball;
        xCoordinates = x;
        yCoordinates = y;
    }

    public BallThread(Ball ball, int[] x, int[] y, int moves){
        b = ball;
        xCoordinates = x;
        yCoordinates = y;
        movesCount = moves;
    }

    @Override
    public void run(){
        try{
            for(int i=1; i<movesCount; i++){
                b.move();
                System.out.println("Thread name = " + Thread.currentThread().getName());
                for (int k=0; k<xCoordinates.length; k++){
                    if (b.getX()==xCoordinates[k] && b.getY()==yCoordinates[k]){
                        System.out.println("Thread STOPS = " + Thread.currentThread().getName());
                        b.setColor();
                        this.currentThread().stop();
                        continue;
                    }
                }
                Thread.sleep(5);
            }

        } catch(InterruptedException ex){

        }
    }
}