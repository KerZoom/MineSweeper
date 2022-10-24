package Main;

// References are at the bottom of the file and are indexed by code E.g. Re.1

public class MineSweeper implements Runnable{       //Re.1

    private Window window;
    private Panel panel;
    private Thread gameThread; //Re.1

    //No argument constructor
    public MineSweeper() {
        panel = new Panel();
        window = new Window(panel);
        startLoop();
    }

    private void startLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        while (true){
            panel.repaint();
        }
    }
}


/* References
    Re.1 https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
         https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html

 */