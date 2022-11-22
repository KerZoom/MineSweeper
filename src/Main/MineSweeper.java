package Main;

// References are at the bottom of the file and are indexed by code E.g. Re.1

public class MineSweeper implements Runnable{       //Re.1

    private final GameWindow gameWindow;
    private final gameContainer gameContainer;
    private final barPanel barPanel;
    private final gamePanel gamePanel;
    private Thread gameThread; //Re.1
    private final int width;
    private final int height;
    public int difficulty;

    //No argument constructor
    public MineSweeper(int width, int height, int difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;


        gameContainer = new gameContainer(width+20, height+20);       // Size of window needs to be modifiable to allow multiple difficulty modes
        barPanel = new barPanel(width+20, 40, difficulty);
        gamePanel = new gamePanel(getWidth(), getHeight(), getDifficulty(), barPanel);
        gameContainer.add(gamePanel);
        gameWindow = new GameWindow( barPanel, gameContainer);

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
                lastFrame = System.nanoTime();
                if (barPanel.isCounting()) {
                    barPanel.incrementTime();
                    barPanel.repaint();
                }
            }
        }
    }

    private int getHeight() {
        return this.height;
    }
    private int getWidth() {
        return this.width;
    }
    private int getDifficulty() {
        return this.difficulty;
    }
    public String name(){
        return gamePanel.getName();
    }
    public int time(){
        return barPanel.getTime();
    }
}


/* References
    Re.1 https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
         https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html

    Re.2 //https://dewitters.com/dewitters-gameloop/

 */