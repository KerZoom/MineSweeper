package Main;

// References are at the bottom of the file and are indexed by code E.g. Re.1

import java.awt.*;

public class MineSweeper implements Runnable{       //Re.1

    private Window window;
    private gamePanel gamePanel;
    private barPanel barPanel;
    private gameContent gameContent;
    private Thread gameThread; //Re.1
    private Thread clockThread;
    private int width = 900, height = 480;

    //No argument constructor
    public MineSweeper() {
        gamePanel = new gamePanel(width+20, height+15);       // Size of window needs to be modifiable to allow multiple difficulty modes
        gameContent = new gameContent(width, height);
        gamePanel.add(gameContent);
        barPanel = new barPanel(width+20, 60);
        window = new Window( barPanel, gamePanel);
        startLoop();
    }

    private void startLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        while (true){
            gamePanel.repaint();
        }
    }
}


/* References
    Re.1 https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
         https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html

 */