package Main;

// References are at the bottom of the file and are indexed by code E.g. Re.1

import javax.swing.*;

public class Window {
    private JFrame jframe;

    public Window(barPanel barPanel, gamePanel gamePanel){
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);      //Re.1
        jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
        jframe.add(barPanel);
        jframe.add(gamePanel);
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
