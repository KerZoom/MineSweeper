package Main;

import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class barPanel extends JPanel{

    private int flags = 0, width, height, time = 0;
    private final BufferedImage[] numberSprites;
    private final BufferedImage[] faceSprites;
    private BufferedImage face;
    private BufferedImage img;
    private boolean counting = false;

    public barPanel(int width, int height, int difficulty) {
        Dimension size = new Dimension(width, height+5);
        numberSprites = new BufferedImage[11];
        faceSprites = new BufferedImage[5];
        setFace(0);
        setFlags(difficulty);
        this.width = width/20;
        this.height = height/20;

        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        Color backgroundColour = new Color(192,192,192); // Color white
        setBackground(backgroundColour);
        importTileSprites();
        repaint();
    }

    private void importTileSprites() {
        try {
            InputStream stream = getClass().getResourceAsStream("/numbers.png"); //Re.1
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - numbers.png");
        }
        for (int i = 0; i < numberSprites.length; i++) {
            numberSprites[i] = img.getSubimage(i * 13, 0, 13, 23);
        }

        try {
            InputStream stream = getClass().getResourceAsStream("/faces.png"); //Re.1
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - faces.png");
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

    public void incrementTime() {
        this.time++;
    }
    public int getTime(){
        return this.time;
    }
    public void startCounting(){
        this.counting = true;
    }
    public void stopCounting(){
        this.counting = false;
    }
    public boolean isCounting(){
        return counting;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(face,(getWidth()/2)-12,10,25,25,null);

        int x = 13 + (13*Integer.toString(getTime()).length());
        String timeString = Integer.toString(getTime());
            for (int i = 0; i < Integer.toString(time).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(timeString.charAt(i)))], width * 20 - x + (i * 13), 10, 13, 23, null);
            }

        String flagsString = Integer.toString(getFlags());
        if (flags >= 0 && flags < 10) {
            g.drawImage(numberSprites[0], 11, 10, 13, 23, null);
            for (int i = 0; i < Integer.toString(flags).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(flagsString.charAt(i)))], 24 + (i * 13), 10, 13, 23, null);
            }
        }
        else if (flags < 0){
            g.drawImage(numberSprites[10], 11, 10, 13, 23, null);
            for (int i = 1; i < Integer.toString(flags).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(flagsString.charAt(i)))], 11 + (i * 13), 10, 13, 23, null);

            }
        }
        else{
            for (int i = 0; i < Integer.toString(flags).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(flagsString.charAt(i)))], 11 + (i * 13), 10, 13, 23, null);
            }
        }
    }
}