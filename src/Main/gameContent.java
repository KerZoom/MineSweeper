package Main;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class gameContent extends JPanel {

    public gameContent(int width, int height){

        setPanelProperties(width, height);

    }
    private void setPanelProperties(int width, int height){
        Dimension size = new Dimension(width,height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        Color borderColor = new Color(89, 89, 89);
        setBorder(BorderFactory.createLineBorder(borderColor, 5));
    }
}

/* References
    Re.1 https://docs.oracle.com/javase/tutorial/uiswing/components/border.html

 */
