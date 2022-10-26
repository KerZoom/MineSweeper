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
    private int[][] numericalBoard;
    private final BufferedImage[] sprites;
    private final BufferedImage[][] board;
    private int roundX, roundY, tilesClicked = 0, totalTiles;
    private boolean mouseActive = true;

    private final boardCreator boardCreation;

    public gamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.totalTiles = width * height;
        sprites = new BufferedImage[16];
        board = new BufferedImage[width / 20][height / 20];
        numericalBoard = new int[width / 20][ width / 20];

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
        newNumericalBoard();

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

    public void newNumericalBoard() {
        for (int i = 0; i < numericalBoard.length; i++) {
            for (int e = 0; e < numericalBoard[0].length;e++){
                numericalBoard[i][e] = 9;
                board[i][e] = sprites[numericalBoard[i][e]];
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
        if (roundX >= 0 && roundY >= 0 && roundX < getWidth() / 20 && roundY < getHeight() / 20 && board[roundX][roundY] != sprites[10]) {
            board[roundX][roundY] = boardCreation.getSpriteAtPosXY(roundX, roundY);
            tilesClicked++;
        }
        repaint();
        if (boardCreation.returnNumerical(roundX,roundY) == 0) {
            numericalBoard[roundX][roundY] = 15;
            adjacentEmptyCellRemover(roundX, roundY);
        }
    }
    public void revealSpriteTemporary(int x, int y){
            board[x][y] = boardCreation.getSpriteAtPosXY(x, y);
            tilesClicked++;
            adjacentEmptyCellRemover(x, y);
            repaint();

    }
    public void revealFullBoard() {
        for (int i = 0; i <= getWidth() / 20 - 1; i++) {
            for (int e = 0; e <= getHeight() / 20 - 1; e++) {
                board[i][e] = boardCreation.getSpriteAtPosXY(i, e);
                mouseActive = false;
            }
        }
        repaint();
    }
    public void flagTile(int x, int y){
        int roundX = (int) Math.floor(x / 2 * 0.1);
        int roundY = (int) Math.floor(y / 2 * 0.1);
        if (roundX >= 0 && roundY >= 0 && roundX < getWidth() / 20 && roundY < getHeight() / 20) {
            switch (numericalBoard[roundX][roundY]) {
                case 9:
                    board[roundX][roundY] = sprites[10];
                    numericalBoard[roundX][roundY] = 10;
                    break;
                case 10:
                    board[roundX][roundY] = sprites[9];
                    numericalBoard[roundX][roundY] = 9;
                    break;
            }
        }
        repaint();
    }
    public boolean isFlagged(int x, int y){
        int roundX = (int) Math.floor(x / 2 * 0.1);
        int roundY = (int) Math.floor(y / 2 * 0.1);
        return numericalBoard[roundX][roundY] == 10;
    }
    public boolean isMouseActive(){
        return mouseActive;
    }

    public void adjacentEmptyCellRemover(int i, int j) {

                    if (i > 0 && boardCreation.returnNumerical(i - 1,j) == 0 && numericalBoard[i-1][j] != 15) {
                        revealSpriteTemporary(i-1,j);
                        numericalBoard[i-1][j] = 15;
                    }
                    if (j > 0 && boardCreation.returnNumerical(i ,j-1) == 0 && numericalBoard[i][j-1] != 15) {
                        revealSpriteTemporary(i,j-1);
                        numericalBoard[i][j-1] = 15;
                    }
                    if (j < height/20 - 1 && boardCreation.returnNumerical(i,j+1) == 0 && numericalBoard[i][j+1] != 15) {
                        revealSpriteTemporary(i,j+1);
                        numericalBoard[i][j+1] = 15;
                    }
                    if (i < width/20 - 1 && boardCreation.returnNumerical(i + 1,j) == 0 && numericalBoard[i+1][j] != 15) {
                        revealSpriteTemporary(i+1,j);
                        numericalBoard[i+1][j] = 15;
                    }
        }
    public void loseCondition(int x, int y){
        this.roundX = (int) Math.floor(x / 2 * 0.1);
        this.roundY = (int) Math.floor(y / 2 * 0.1);
        if (roundX >= 0 && roundY >= 0 && roundX < getWidth() / 20 && roundY < getHeight() / 20) {
            if (numericalBoard[roundX][roundY] == 9) {
                if (boardCreation.returnNumerical(roundX, roundY) == 13) {
                    revealFullBoard();
                    board[roundX][roundY] = sprites[14];
                }
            }
        }
    }
    public void winCondition(){
  /*      int flags = 0, correctFlags = 0;
        boolean allBombsFound = false;
        for (int i = 0; i <= getWidth() / 20 - 1; i++) {
            for (int e = 0; e <= getHeight() / 20 - 1; e++) {
                if (numericalBoard[i][e] == 10){
                    flags++;
                    if(numericalBoard[i][e] == 10 && boardCreation.returnNumerical(i,e) == 13) {
                        correctFlags++;
                    }
                }
            }
        }
        if (correctFlags == flags){
            allBombsFound = true;
        }*/
        if (tilesClicked + boardCreation.returnTotalMineCount() == ((getHeight() / 20) + (getHeight() / 20))){
            mouseActive = false;
            JOptionPane.showMessageDialog(null,"You win!","Winner", JOptionPane.PLAIN_MESSAGE);
        }

    }
}

/* References
    Re.1 https://docs.oracle.com/javase/tutorial/uiswing/components/border.html

 */
