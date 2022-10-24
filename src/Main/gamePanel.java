package Main;

import javax.swing.*;
import java.awt.*;

public class gamePanel extends JPanel {

    public gamePanel(int width, int height){

        setPanelProperties(width, height);

    }
    private void setPanelProperties(int width, int height){
        Dimension size = new Dimension(width,height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setBackground(Color.GRAY);
    }
}

/* References
    Re.1 https://docs.oracle.com/javase/tutorial/uiswing/components/border.html

 */
