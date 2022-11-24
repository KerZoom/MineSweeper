package Main;

/**This is the Minesweeper class, it's essentially a driver class and creates instances of
  the other classes, it also implements "Runnable" which allows the game to run in real time
  on a thread*/

public class MineSweeper implements Runnable{

    private final GameWindow gameWindow;
    private final GameContainer gameContainer;
    private final BarPanel barPanel;
    private final CoreGameMechanics CoreGameMechanics;
    private Thread gameThread; //Re.1
    private final int width;
    private final int height;
    public int difficulty;

    public MineSweeper(int width, int height, int difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;


        gameContainer = new GameContainer(width+20, height+20);       // Size of window needs to be modifiable to allow multiple difficulty modes
        barPanel = new BarPanel(width+20, 40, difficulty);
        CoreGameMechanics = new CoreGameMechanics(getWidth(), getHeight(), getDifficulty(), barPanel);
        gameContainer.add(CoreGameMechanics);
        gameWindow = new GameWindow( barPanel, gameContainer);

        startLoop();
    }

    /**This creates a new thread for the game to run on and assigns it this class */
    private void startLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**This is the actual run function, it controls the frame rate of the game and
     * increments the clock accordingly*/
    public void run() {
        long lastFrame = System.nanoTime(), timePerFrame = 1000000000; //Re.2
        double now;

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
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
}


/* References
    Re.1 https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
         https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html

    Re.2 //https://dewitters.com/dewitters-gameloop/

 */