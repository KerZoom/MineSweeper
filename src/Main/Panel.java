package Main;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    public Panel(){

        setPanelSize();

    }
    private void setPanelSize(){
        Dimension size = new Dimension(1000,1000);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
}
