package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class gamePanel extends JPanel {

    int width, height;
    private BufferedImage img;
    private int[][] numericalBoard;
    private final BufferedImage[] sprites;
    private final BufferedImage[][] board;
    private int tilesClicked = 0, totalTiles;
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

        boardCreation = new boardCreator(width / 20, height / 20, 0);
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
                numericalBoard[i][e] = boardCreation.returnNumerical(i,e);
                board[i][e] = sprites[9];
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
        if (insideBoard(x,y)) {
            board[x][y] = boardCreation.getSpriteAtPosXY(x, y);
            tilesClicked++;
        }
        repaint();
    }
    public void clearCells(int x, int y){
        if (insideBoard(x,y)){
            if (board[x][y] != sprites[0] && numericalBoard[x][y] == 0){

                if (insideBoard(x-1,y) && numericalBoard[x-1][y] > 0 && numericalBoard[x-1][y] < 9) {
                    revealSprite(x-1,y);
                    if (insideBoard(x-1,y+1)){
                        if (numericalBoard[x-1][y+1] > 0 && numericalBoard[x-1][y+1] < 9) {
                            revealSprite(x - 1, y+1);
                        }
                    }
                    if (insideBoard(x-1,y-1)){
                        if (numericalBoard[x-1][y-1] > 0 && numericalBoard[x-1][y-1] < 9) {
                            revealSprite(x - 1, y-1);
                        }
                    }
                }
                if (insideBoard(x+1,y) && numericalBoard[x+1][y] > 0 && numericalBoard[x+1][y] < 9) {
                    revealSprite(x+1,y);
                    if (insideBoard(x+1,y+1)){
                        if (numericalBoard[x+1][y+1] > 0 && numericalBoard[x+1][y+1] < 9) {
                            revealSprite(x + 1, y+1);
                        }
                    }
                    if (insideBoard(x+1,y-1)){
                        if (numericalBoard[x+1][y-1] > 0 && numericalBoard[x+1][y-1] < 9) {
                            revealSprite(x + 1, y-1);
                        }
                    }
                }
                if (insideBoard(x,y+1) && numericalBoard[x][y+1] > 0 && numericalBoard[x][y+1] < 9) {
                    revealSprite(x,y+1);

                }
                if (insideBoard(x,y-1)  && numericalBoard[x][y-1] > 0 && numericalBoard[x][y-1] < 9) {
                    revealSprite(x,y-1);
                }
                    board[x][y] = sprites[0];
                    clearCells(x - 1, y);
                    clearCells(x + 1, y);
                    clearCells(x, y - 1);
                    clearCells(x, y + 1);
            }
    }
           repaint();
    }
    public void revealFullBoard() {
        for (int i = 0; i <= getWidth() / 20 - 1; i++) {
            for (int e = 0; e <= getHeight() / 20 - 1; e++) {
                if (board[i][e] == sprites[10] && boardCreation.returnNumerical(i, e) == 13)
                    board[i][e] = sprites[15];
                else {
                    board[i][e] = boardCreation.getSpriteAtPosXY(i, e);
                }
            }
            mouseActive = false;
            repaint();
        }
    }
    public void flagTile(int x, int y){
        if (insideBoard(x,y)) {
            if (board[x][y] == sprites[9]) {
                board[x][y] = sprites[10];
                numericalBoard[x][y] = 10;
            }
            else{
                board[x][y] = sprites[9];
                numericalBoard[x][y] = 9;
            }
        }
        repaint();
    }
    public boolean isFlagged(int x, int y){
        return numericalBoard[x][y] == 10;
    }
    public boolean isMouseActive(){
        return mouseActive;
    }
    public boolean insideBoard(int x, int y){
        return x >= 0 && y >= 0 && x < getWidth() / 20 && y < getHeight() / 20;
    }


    public void loseCondition(int x, int y){
        if (x >= 0 && y >= 0 && x < getWidth() / 20 && y < getHeight() / 20 && board[x][y] != sprites[9]) {
            if (numericalBoard[x][y] == 13) {
                revealFullBoard();
                board[x][y] = sprites[14];
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
