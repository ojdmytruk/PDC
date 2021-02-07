package Pool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    private Pockets pockets;
    private JLabel jLabelInPockets;

    public void add(Ball b){
        this.balls.add(b);
    }
    public void addPockets(Pockets p){
        this.pockets = p;
    }

    public BallCanvas (JLabel label){
        this.jLabelInPockets = label;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        pockets.draw(g2);
        for(int i=0; i<balls.size();i++){
            Ball b = balls.get(i);
            b.draw(g2);
        }
        int ballsInPocket = balls.stream().filter(x -> x.getColor() == Color.BLACK).toArray().length;
        jLabelInPockets.setText(Integer.toString(ballsInPocket));
        for (int i=0; i<balls.size(); i++){
            if (balls.get(i).getColor()==Color.BLACK){
                balls.remove(balls.get(i));
            }
        }
    }

}
