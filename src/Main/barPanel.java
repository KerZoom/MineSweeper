package Main;

import javax.swing.*;
import java.awt.*;

public class barPanel extends JPanel {

    public barPanel(int width, int height){

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
