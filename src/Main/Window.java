package Main;

import javax.swing.*;

public class Window {
    private JFrame jframe;

    public Window(Panel panel){
        jframe = new JFrame();


        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(panel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
    }
}
