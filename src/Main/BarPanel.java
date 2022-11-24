package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 This class controls the bar at the top of the minesweeper screen containing
 the flag count, timer and smiley face indicator
 */

public class BarPanel extends JPanel{

    private int flags = 0, width, height, time = 0;
    private final BufferedImage[] numberSprites;                /** Number sprites are used for the timer*/
    private final BufferedImage[] faceSprites;                  /** Face sprites are used for the smiley face*/
    private BufferedImage face;
    private BufferedImage img;
    private boolean counting = false;                           /** By default, the timer is disabled*/

    public BarPanel(int width, int height, int difficulty) {
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
        Color backgroundColour = new Color(192,192,192);
        setBackground(backgroundColour);
        importTileSprites();
        repaint();
    }

    /** ImportTileSprites imports all the number and face sprites ahead of time and stores them
     * in their corresponding arrays
     *
     * It does this using a for loop and multiplying i by the width of a sprite,
     * for example 1*13 = 13 so 13 pixels from the left gives you the second sprite in the sprites file*/
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

    /** The flags counter is incremented up*/
    public void incrementFlagsUp() {
        this.flags++;
        repaint();
    }
    /** The flags counter is incremented down*/
    public void incrementFlagsDown() {
        this.flags--;
        repaint();
    }

    /** The number of flags is set*/
    public void setFlags(int flags) {
        this.flags = flags;
        repaint();
    }
    /** The number of flags is returned*/
    public int getFlags() {
        return this.flags;
    }
    /** The current face sprite in use is set*/
    public void setFace(int index){
        this.face = faceSprites[index];
        repaint();
    }

    /**Increments the clock by 1
       I could have used a timer for the time, but since I already had runnable
       implemented it made more sense to just use that to control the clock*/
    public void incrementTime() {
        this.time++;
    }
    /**Returns the time */
    public int getTime(){
        return this.time;
    }
    /**Starts the timer counting */
    public void startCounting(){
        this.counting = true;
    }
    /**Stops the timer counting */
    public void stopCounting(){
        this.counting = false;
    }
    /**Returns if the timer is counting*/
    public boolean isCounting(){
        return counting;
    }

    /** This is used to draw the timer, smiley face and flag count.
     *
     * Timer:
     * It looks complicated but all its doing is getting the time in seconds and turning it into a string. It then gets
     * the length of the string and multiplies it by the width of a single sprite to figure out how far from the right
     * the first number should be painted. This ensures the numbers are printed from left to right in the correct order
     * and without overflowing off the side of the panel. I'm quite proud of how elegantly this works.
     *
     * Smiley face:
     * "face" is a variable containing a buffered image, the image is set using the setFace() method then painted when
     * repaint() is called
     *
     * Flags:
     * Works very similarly to timer, the only difference is a zero is printed to the left of the number being
     * printed if the number is less than 10 and it takes into account if the number becomes negative which can only
     * happen with the mines.
     * If the mine count becomes negative a minus symbol is added before the number.
     * */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //This prints the smiley face
        g.drawImage(face,(getWidth()/2)-12,10,25,25,null);

        //This prints the time
        int x = 13 + (13*Integer.toString(getTime()).length());
        String timeString = Integer.toString(getTime());
            for (int i = 0; i < Integer.toString(time).length(); i++) {
                g.drawImage(numberSprites[Integer.parseInt(Character.toString(timeString.charAt(i)))], width * 20 - x + (i * 13), 10, 13, 23, null);
            }

        //This prints the flags
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

/*
References:
https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html Drawing the numbers, flags and smiley face
*/