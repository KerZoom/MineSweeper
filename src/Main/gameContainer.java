package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class gameContainer extends JPanel {

    public gameContainer(int width, int height){

        Dimension size = new Dimension(width,height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        Color lightishGrey = new Color(189,189,189,189);
        setBackground(lightishGrey);
    }
}
