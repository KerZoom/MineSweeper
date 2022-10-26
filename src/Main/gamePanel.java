package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class gamePanel extends JPanel {

    int width, height;
    private BufferedImage img;
    private BufferedImage[] sprites;
    private BufferedImage[][] board;

    private boardCreator boardCreation;

    public gamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        sprites = new BufferedImage[16];
        board = new BufferedImage[width / 20][height / 20];

        mouseInput input = new mouseInput(this);
        addMouseListener(input);

        Dimension size = new Dimension(width, height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        importTileSprites();

        setBackground(Color.LIGHT_GRAY);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        boardCreation = new boardCreator(width / 20, height / 20, 1);
        fillBoard(boardCreation.getboardContents());

    }

    private void importTileSprites() {
        try {
            InputStream stream = getClass().getResourceAsStream("/tiles.png"); //Re.1
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - Game Panel");
        }
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    public void fillBoard(BufferedImage[][] contents) {
        for (int i = 0; i < board.length; i++) {
            for (int e = 0; e < board[0].length; e++) {
                board[i][e] = contents[i][e];
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= getWidth() / 20 - 1; i++) {
            for (int e = 0; e <= getHeight() / 20 - 1; e++) {
                g.drawImage(board[i][e], i * 20, e * 20, 20, 20, null);
            }
        }
    }

    public void revealSprite(int x, int y) {
        int roundX = (int) Math.floor(x/2 * 0.1);
        int roundY = (int) Math.floor(y/2 * 0.1);
        if (roundX >= 0 && roundY >= 0 && roundX < getWidth() / 20 && roundY < getHeight() / 20)
            board[roundX][roundY] = boardCreation.getSpriteAtPosXY(roundX, roundY);

        repaint();
    }
    public void revealFullBoard() {
        for (int i = 0; i <= getWidth() / 20 - 1; i++) {
            for (int e = 0; e <= getHeight() / 20 - 1; e++) {
                board[i][e] = boardCreation.getSpriteAtPosXY(i, e);
            }
        }
        repaint();
    }
    public void loseCondition(int x, int y){
        int roundX = (int) Math.floor(x / 2 * 0.1);
        int roundY = (int) Math.floor(y / 2 * 0.1);
        if (roundX >= 0 && roundY >= 0 && roundX < getWidth() / 20 && roundY < getHeight() / 20) {
            if (boardCreation.returnNumerical(roundX, roundY) == 13) {
                revealFullBoard();
                board[roundX][roundY] = sprites[14];
            }
        }
    }
}

/* References
    Re.1 https://docs.oracle.com/javase/tutorial/uiswing/components/border.html

 */
