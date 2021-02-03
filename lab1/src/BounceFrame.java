import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        //button initialization
        JButton buttonSimpleBall = new JButton("Simple Ball");
        JButton buttonBlueBall = new JButton("Blue Ball");
        JButton buttonRedBall = new JButton("Red Ball");
        JButton buttonRedVSBlue = new JButton("Red VS Blue Ball");
        JButton buttonStop = new JButton("Stop");

        ///////////////listeners///////////////

        // creates balls from example
        buttonSimpleBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas, Color.darkGray);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });

        // creates blue balls with low thread priority (1)
        buttonBlueBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas, Color.blue);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.setPriority(1);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });

        // creates red balls with high thread priority (5)
        buttonRedBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas, Color.red);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.setPriority(5);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });

        // creates red balls with high thread priority (5) or
        // blue balls with low thread priority (1)
        buttonRedVSBlue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball redBall = new Ball(canvas, Color.red);
                Ball blueBall = new Ball(canvas, Color.blue);
                canvas.add(redBall);
                canvas.add(blueBall);
                BallThread threadRed = new BallThread(redBall);
                BallThread threadBlue = new BallThread(blueBall);
                threadBlue.setPriority(1);
                threadBlue.start();
                threadRed.setPriority(5);
                threadRed.start();

                System.out.println("Thread (red) name = " +
                        threadRed.getName());
                System.out.println("Thread (blue) name = " +
                        threadRed.getName());
            }
        });


        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

        buttonPanel.add(buttonSimpleBall);
        buttonPanel.add(buttonBlueBall);
        buttonPanel.add(buttonRedBall);
        buttonPanel.add(buttonRedVSBlue);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}