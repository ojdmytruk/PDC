package Pool;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

class Pockets {
    private Component canvas;
    private Color pocketColor;
    private static final int XSIZE = 22;
    private static final int YSIZE = 22;
    int [] xCoordinates;
    int[] yCoordinates;


    public Pockets(Component c, Color color, int[] x, int[] y){
        this.canvas = c;
        this.pocketColor = color;
        this.xCoordinates = x;
        this.yCoordinates = y;

    }

    public void draw (Graphics2D g){
        for (int i=0; i<xCoordinates.length; i++){
            g.setColor(pocketColor);
            g.fill(new Ellipse2D.Double(xCoordinates[i],yCoordinates[i],XSIZE,YSIZE));
        }
    }


}