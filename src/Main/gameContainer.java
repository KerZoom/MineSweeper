package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class gameContainer extends JPanel {

    public gameContainer(int width, int height){

        Dimension size = new Dimension(width,height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setBackground(Color.GRAY);

    }
}

/* References
    Re.1 https://github.com/andreasisnes/pygame-minesweeper-sprites

 */
