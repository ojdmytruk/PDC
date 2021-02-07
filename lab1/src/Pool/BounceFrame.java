package Pool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 860;
    public static final int HEIGHT = 350;
    //pockets coordinates
    int[] xPocketsArray = new int[] {2, WIDTH/2, WIDTH-44, 2, WIDTH/2, WIDTH-44};
    int[] yPocketsArray = new int[] {2, 2, 2, HEIGHT-104, HEIGHT-104, HEIGHT-104};


    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Pool.Bounce programm");

        //labels and text fields initialization
        JLabel jLabelFieldTitle = new JLabel("Синіх кульок:");
        JTextField textFieldBlueBallsNum = new JTextField(5);
        JLabel jLabelNumInPocketTitle = new JLabel("Кульок у лузі: ");
        JLabel jLabelNumInPocket = new JLabel("0");

        this.canvas = new BallCanvas(jLabelNumInPocket);
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);


        Pockets p = new Pockets(canvas, Color.BLACK, xPocketsArray, yPocketsArray);
        canvas.addPockets(p);
        this.canvas.repaint();

        //button initialization
        JButton buttonSimpleBall = new JButton("Simple Ball");
        JButton buttonBlueBall = new JButton("Blue Ball");
        JButton buttonRedBall = new JButton("Red Ball");
        JButton buttonRedVSBlue = new JButton("Red VS Blue Ball");
        JButton buttonJoin = new JButton("Join");
        JButton buttonStop = new JButton("Stop");


        ///////////////listeners///////////////


        // creates balls from example
        buttonSimpleBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x, y;
                if(Math.random()<0.5){
                    x = new Random().nextInt(canvas.getWidth());
                    y = 0;
                }else{
                    x = 0;
                    y = new Random().nextInt(canvas.getHeight());
                }
                Ball b = new Ball(canvas, Color.darkGray, x, y);
                canvas.add(b);

                BallThread thread = new BallThread(b, xPocketsArray, yPocketsArray);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });

        // creates blue balls with low thread priority (1)
        buttonBlueBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x, y;
                if(Math.random()<0.5){
                    x = new Random().nextInt(canvas.getWidth());
                    y = 0;
                }else{
                    x = 0;
                    y = new Random().nextInt(canvas.getHeight());
                }
                Ball b = new Ball(canvas, Color.blue, x, y);
                canvas.add(b);
                BallThread thread = new BallThread(b, xPocketsArray, yPocketsArray);
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
                int x, y;
                if(Math.random()<0.5){
                    x = new Random().nextInt(canvas.getWidth());
                    y = 0;
                }else{
                    x = 0;
                    y = new Random().nextInt(canvas.getHeight());
                }
                Ball b = new Ball(canvas, Color.red, x, y);
                canvas.add(b);
                BallThread thread = new BallThread(b, xPocketsArray, yPocketsArray);
                thread.setPriority(5);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });

        // creates red balls with high thread priority (10) or
        // blue balls with low thread priority (1)
        buttonRedVSBlue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {



                if ((textFieldBlueBallsNum.getText() != "") && (Integer.valueOf(textFieldBlueBallsNum.getText()) > 0)) {
                    for (int i = 0; i < Integer.valueOf(textFieldBlueBallsNum.getText()); i++) {
                        Ball blueBall = new Ball(canvas, Color.blue, canvas.getWidth() / 2, canvas.getHeight() / 2);
                        canvas.add(blueBall);
                        BallThread threadBlue = new BallThread(blueBall, xPocketsArray, yPocketsArray);
                        threadBlue.setPriority(Thread.MIN_PRIORITY);
                        threadBlue.start();
                        System.out.println("Thread (blue) name = " +
                                threadBlue.getName());
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        Ball blueBall = new Ball(canvas, Color.blue, canvas.getWidth() / 2, canvas.getHeight() / 2);
                        canvas.add(blueBall);
                        BallThread threadBlue = new BallThread(blueBall, xPocketsArray, yPocketsArray);
                        threadBlue.setPriority(Thread.MIN_PRIORITY);
                        threadBlue.start();
                        System.out.println("Thread (blue) name = " +
                                threadBlue.getName());
                    }



                }
                Ball redBall = new Ball(canvas, Color.red, canvas.getWidth() / 2, canvas.getHeight() / 2);
                canvas.add(redBall);
                BallThread threadRed = new BallThread(redBall, xPocketsArray, yPocketsArray);
                threadRed.setPriority(10);
                threadRed.start();


                System.out.println("Thread (red) name = " +
                        threadRed.getName());
            }
        });

        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinVisualization();
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
        buttonPanel.add(jLabelFieldTitle);
        buttonPanel.add(textFieldBlueBallsNum);
        buttonPanel.add(buttonRedVSBlue);
        buttonPanel.add(jLabelNumInPocketTitle);
        buttonPanel.add(jLabelNumInPocket);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonStop);



        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void joinVisualization(){
        Ball bGreen = new Ball(canvas, Color.green, WIDTH / 2 - 100, HEIGHT / 2);
        Ball bYellow = new Ball(canvas, Color.yellow, WIDTH / 2 + 100, HEIGHT / 2);
        canvas.add(bGreen);
        canvas.add(bYellow);
        BallThread threadGreen = new BallThread(bGreen, xPocketsArray, yPocketsArray, 500);
        BallThread threadYellow = new BallThread(bYellow, xPocketsArray, yPocketsArray, 500);
        threadGreen.start();
        try{
            threadGreen.join();
        }catch(InterruptedException exception){}

        threadYellow.start();
    }

}