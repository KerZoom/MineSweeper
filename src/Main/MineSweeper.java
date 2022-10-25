package Main;

// References are at the bottom of the file and are indexed by code E.g. Re.1

public class MineSweeper implements Runnable{       //Re.1

    private Window window;
    private gameContainer gameContainer;
    private barPanel barPanel;
    private gamePanel gamePanel;
    private Thread gameThread; //Re.1
    private Thread clockThread;
    private int width = 200, height = 200;

    //No argument constructor
    public MineSweeper() {
        gameContainer = new gameContainer(width+20, height+15);       // Size of window needs to be modifiable to allow multiple difficulty modes
        gamePanel = new gamePanel(width, height);
        gameContainer.add(gamePanel);
        barPanel = new barPanel(width+20, 60);
        window = new Window( barPanel, gameContainer);
        startLoop();
    }

    private void startLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        int FPSlimit = 100;
        long lastCheck = System.currentTimeMillis(), lastFrame = System.nanoTime(),
                timePerFrame = 1000000000; //Re.2
        double frame = 1000000000.0 / FPSlimit, now;

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {  //Finished FPS control, actually works now
                                                    //Repaints the JFrame every frame
                lastFrame = System.nanoTime();      //Real time isn't actually necessary but I will
                                                    //Need this for the timer later on
            }
        }
    }
}


/* References
    Re.1 https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
         https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html

    Re.2 //https://dewitters.com/dewitters-gameloop/

 */