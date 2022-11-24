package Main;

import javax.swing.*;
import java.awt.*;

/** This is just a jpanel, nothing else*/

public class GameContainer extends JPanel {

    public GameContainer(int width, int height){

        Dimension size = new Dimension(width,height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        Color lightishGrey = new Color(189,189,189,189);
        setBackground(lightishGrey);
    }
}
