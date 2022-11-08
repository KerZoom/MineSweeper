package Main;

// References are at the bottom of the file and are indexed by code E.g. Re.1

import javax.swing.*;

public class GameWindow {
    private final JFrame jframe;

    public GameWindow(barPanel barPanel, gameContainer gameContainer){
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
        jframe.add(barPanel);
        jframe.add(gameContainer);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
    }
}

/* References
    Re.1 https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html

    Re.2 https://www.tutorialspoint.com/when-can-we-use-the-pack-method-in-java

    Re.3 https://stackoverflow.com/questions/6325384/adding-multiple-jpanels-to-jframe

    Re.4 https://www.youtube.com/watch?v=uyqXnYacSTg
 */
