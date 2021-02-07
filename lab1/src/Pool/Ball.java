package Pool;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class Ball {
    private Component canvas;
    private Color ballColor;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;


    public Ball(Component c, Color color, int xCoordinate, int yCoordinate){
        this.canvas = c;
        this.ballColor = color;
        this.x = xCoordinate;
        this.y = yCoordinate;

    }

    public void draw (Graphics2D g2){
        g2.setColor(ballColor);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));

    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setColor(){
        this.ballColor = Color.BLACK;
    }

    public Color getColor(){
        return this.ballColor;
    }
}
