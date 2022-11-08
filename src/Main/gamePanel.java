package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class gamePanel extends JPanel {

    int width, height, difficulty;
    private BufferedImage img;

    private final int[][] numericalBoard;
    private final int[][] numericalWinBoard;
    private final BufferedImage[] sprites;
    private final BufferedImage[][] board;
    private final barPanel barpanel;
    private boardCreator boardCreator;

    private boolean mouseActive = true;

    public gamePanel(int width, int height, int difficulty, barPanel barpanel) {

        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.barpanel = barpanel;

        sprites = new BufferedImage[16];

        board = new BufferedImage[getWidth() / 20][getWidth() / 20];
        numericalWinBoard = new int[getWidth() / 20][getHeight() /  20];
        numericalBoard = new int[getWidth() / 20][getHeight() / 20];

        mouseInput input = new mouseInput(this, barpanel);
        addMouseListener(input);

        Dimension size = new Dimension(width, height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        importTileSprites();

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        blankBoard();

    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getDifficulty(){
        return difficulty;
    }

    public void startGame(int x, int y){
        boardCreator = new boardCreator(getWidth() / 20, getHeight() / 20, getDifficulty(), x, y);
        barpanel.setFlags(boardCreator.returnTotalMineCount());
        barpanel.setFace(0);
        resetNumericalBoards();

    }

    private void importTileSprites() {
        try {
            InputStream stream = getClass().getResourceAsStream("/tiles.png"); //Re.1
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - tiles.png");
        }
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }
    public void blankBoard(){
        for (int i = 0; i < numericalBoard.length; i++) {
            for (int e = 0; e < numericalBoard[0].length;e++){
                board[i][e] = sprites[9];
            }
        }
        repaint();
    }

    public void resetNumericalBoards() {
        for (int i = 0; i < numericalBoard.length; i++) {
            for (int e = 0; e < numericalBoard[0].length;e++){
                numericalBoard[i][e] = boardCreator.returnNumerical(i,e);
                numericalWinBoard[i][e] = 0;
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
        if (insideBoard(x, y)) {
            board[x][y] = boardCreator.getSpriteAtPosXY(x, y);
            numericalWinBoard[x][y] = 1;
        }
        repaint();
    }
    public void clearCells(int x, int y){

        if (insideBoard(x,y)){
            if (board[x][y] != sprites[0] && numericalBoard[x][y] == 0){

                if (insideBoard(x-1,y) && numericalBoard[x-1][y] > 0 && numericalBoard[x-1][y] < 9) {
                    revealSprite(x-1,y);
                    numericalWinBoard[x-1][y] = 1;
                    if (insideBoard(x-1,y+1)){
                        if (numericalBoard[x-1][y+1] > 0 && numericalBoard[x-1][y+1] < 9) {
                            revealSprite(x - 1, y+1);
                            numericalWinBoard[x-1][y+1] = 1;
                        }
                    }
                    if (insideBoard(x-1,y-1)){
                        if (numericalBoard[x-1][y-1] > 0 && numericalBoard[x-1][y-1] < 9) {
                            revealSprite(x - 1, y-1);
                            numericalWinBoard[x-1][y-1] = 1;
                        }
                    }
                }
                if (insideBoard(x+1,y) && numericalBoard[x+1][y] > 0 && numericalBoard[x+1][y] < 9) {
                    revealSprite(x+1,y);
                    numericalWinBoard[x+1][y] = 1;
                    if (insideBoard(x+1,y+1)){
                        if (numericalBoard[x+1][y+1] > 0 && numericalBoard[x+1][y+1] < 9) {
                            revealSprite(x + 1, y+1);
                            numericalWinBoard[x+1][y+1] = 1;
                        }
                    }
                    if (insideBoard(x+1,y-1)){
                        if (numericalBoard[x+1][y-1] > 0 && numericalBoard[x+1][y-1] < 9) {
                            revealSprite(x + 1, y-1);
                            numericalWinBoard[x+1][y-1] = 1;
                        }
                    }
                }
                if (insideBoard(x,y+1) && numericalBoard[x][y+1] > 0 && numericalBoard[x][y+1] < 9) {
                    revealSprite(x,y+1);
                    numericalWinBoard[x][y+1] = 1;
                }
                if (insideBoard(x,y-1)  && numericalBoard[x][y-1] > 0 && numericalBoard[x][y-1] < 9) {
                    revealSprite(x,y-1);
                    numericalWinBoard[x][y-1] = 1;
                }
                board[x][y] = sprites[0];
                numericalWinBoard[x][y] = 1;
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
                if (board[i][e] == sprites[10] && boardCreator.returnNumerical(i, e) == 13)
                    board[i][e] = sprites[15];
                else {
                    board[i][e] = boardCreator.getSpriteAtPosXY(i, e);
                }
            }
            mouseActive = false;
            repaint();
        }
    }
    public void flagTile(int x, int y){
        if (insideBoard(x, y)){
            if (board[x][y] == sprites[9]){
                board[x][y] = sprites[10];
                numericalBoard[x][y] = 10;
                barpanel.incrementFlagsDown();
            } else if (board[x][y] == sprites[10]){
                board[x][y] = sprites[9];
                numericalBoard[x][y] = boardCreator.returnNumerical(x,y);
                barpanel.incrementFlagsUp();
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
                barpanel.setFace(4);
                barpanel.stopCounting();
            }
        }
    }
    public void winCondition(){
        int correctFlags = 0, tilesClicked = 0;
        for (int x= 0; x <= getWidth() / 20 - 1; x++) {
            for (int y = 0; y <= getHeight() / 20 - 1; y++) {
                if (numericalBoard[x][y] == 10){
                    if(numericalBoard[x][y] == 10 && boardCreator.returnNumerical(x,y) == 13) {
                        correctFlags++;
                    }
                }
            }
        }

        for (int x= 0; x < getWidth() / 20; x++) {                              //This is a really stupid way of creating a win condition
            for (int y = 0; y < getHeight() / 20; y++) {                        //But it's the only method I could get to work reliably
                if (numericalWinBoard[x][y] == 1){
                    tilesClicked++;
                }
            }
        }
        if (tilesClicked + correctFlags == ((getWidth() / 20) * (getHeight() / 20))) {
            System.out.println("Winner");
            barpanel.setFace(3);
            mouseActive = false;
        }
    }
}
