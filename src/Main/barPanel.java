package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class barPanel extends JPanel {

    private int flags = 0, width, height;
    private final BufferedImage[] numberSprites;
    private final BufferedImage[] faceSprites;
    private BufferedImage face;
    private BufferedImage img;
    boolean active = false;

    public barPanel(int width, int height) {
        Dimension size = new Dimension(width, height);
        FlowLayout layout = new FlowLayout();
        numberSprites = new BufferedImage[11];
        faceSprites = new BufferedImage[5];
        setFace(0);

        this.width = width/20;
        this.height = height/20;

        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setBackground(Color.GRAY);
        importTileSprites();
        repaint();
    }

    private void importTileSprites() {
        try {
            InputStream stream = getClass().getResourceAsStream("/numbers.png"); //Re.1
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - numbers");
        }
        for (int i = 0; i < numberSprites.length; i++) {
            numberSprites[i] = img.getSubimage(i * 13, 0, 13, 23);
        }

        try {
            InputStream stream = getClass().getResourceAsStream("/faces.png"); //Re.1
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - faces");
        }
        for (int i = 0; i < faceSprites.length; i++) {
            faceSprites[i] = img.getSubimage(i * 24, 0, 24, 24);
        }
    }


    public void incrementFlagsUp() {
        this.flags++;
        repaint();
    }
    public void incrementFlagsDown() {
        this.flags--;
        repaint();
    }

    public void setFlags(int flags) {
        this.flags = flags;
        repaint();
    }
    public int getFlags() {
        return this.flags;
    }

    public void setFace(int index){
        this.face = faceSprites[index];
        repaint();
    }

    private void incrementTime() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        String flagsString = Integer.toString(getFlags());
        if (flags >= 0) {
            for (int i = 0; i < Integer.toString(flags).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(flagsString.charAt(i)))], 20 + (i * 13), 10, 13, 23, null);
            }
        }
        else {
            g.drawImage(numberSprites[10], 20, 10, 13, 23, null);
            for (int i = 1; i < Integer.toString(flags).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(flagsString.charAt(i)))], 20 + (i * 13), 10, 13, 23, null);

            }
        }

        g.drawImage(face,width*20/2-12,10,25,25,null);
    }
}
