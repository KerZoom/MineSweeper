package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class boardCreator {

    private int width, height;
    private int difficulty = 0;
    private BufferedImage[][] boardContents;
    private BufferedImage img;
    private BufferedImage[] tileSprites;
    private int[][] numericalBoardPositions;
    private int totalMineCount = 0;

    public boardCreator(int width, int height, int difficulty) {

        this.width = width;
        this.height = height;

        tileSprites = new BufferedImage[16];

        boardContents = new BufferedImage[width][height];
        numericalBoardPositions = new int[width][height];

        this.difficulty = difficulty;

        importTileSprites();

        difficultySelector();

        fullBoard();

        generateRandomBoard();

    }
    public void difficultySelector(){
        switch (difficulty) {
            case 1:
                difficulty = 17;
                break;
            case 2:
                difficulty = 20;
                break;
            default:
                difficulty = 10;
                break;
        }
    }
    private void importTileSprites() {
        try {
            InputStream stream = getClass().getResourceAsStream("/tiles.png");
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - Game Panel");
        }
        for (int i = 0; i < tileSprites.length; i++) {
            tileSprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }
    public void fullBoard(){
        for (int i = 0; i < boardContents.length; i++) {
            for (int e = 0; e < boardContents[0].length; e++) {
                boardContents[i][e] = tileSprites[9];
            }
        }
    }

    public void generateRandomBoard() {
        int randomNum;
        int mineCount;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                randomNum = (int) (Math.random() * 100);
                if (randomNum < difficulty) {
                    numericalBoardPositions[i][j] = 13;
                    totalMineCount++;
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mineCount = 0;
                if (numericalBoardPositions[i][j] != 13) {
                    if (i > 0 && j > 0 && numericalBoardPositions[i - 1][j - 1] == 13) {
                        mineCount++;
                    }
                    if (i > 0 && numericalBoardPositions[i - 1][j] == 13) {
                        mineCount++;
                    }
                    if (i > 0 && j < height-1 && numericalBoardPositions[i - 1][j + 1] == 13) {
                        mineCount++;
                    }
                    if (j > 0 && numericalBoardPositions[i][j - 1] == 13) {
                        mineCount++;
                    }
                    if (j < height-1 && numericalBoardPositions[i][j + 1] == 13) {
                        mineCount++;
                    }
                    if (i < width-1 && j > 0 && numericalBoardPositions[i + 1][j - 1] == 13) {
                        mineCount++;
                    }
                    if (i < width-1  && numericalBoardPositions[i + 1][j] == 13) {
                        mineCount++;
                    }
                    if (i < width-1  && j < height-1 && numericalBoardPositions[i + 1][j + 1] == 13) {
                        mineCount++;
                    }
                    numericalBoardPositions[i][j] = mineCount;
                }
            }
        }
    }

    public BufferedImage getSpriteAtPosXY(int x, int y){
        return tileSprites[numericalBoardPositions[x][y]];
    }
    public BufferedImage[][] getboardContents(){
        return this.boardContents;
    }
    public void flagBomb(int x, int y){
        numericalBoardPositions[x][y] = 15;
    }
    public int returnNumerical(int x, int y){
        return numericalBoardPositions[x][y];
    }
}
