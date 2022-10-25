package Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class boardCreator {

    private int width, height;
    private int difficulty = 0;
    private BufferedImage[][] boardContents;
    private BufferedImage img;
    private BufferedImage[] numberSprites;
    private BufferedImage[] tileSprites;

    public boardCreator(int width, int height, int difficulty) {

        numberSprites = new BufferedImage[8];
        boardContents = new BufferedImage[width/20][height/20];
        tileSprites = new BufferedImage[8];
        this.difficulty = difficulty;

        difficultySelector();
        generateBoardContents();
        importTileSprites();
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
            InputStream stream = getClass().getResourceAsStream("/numberTiles.png");
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - numberSprites");
        }
        for (int i = 0; i < numberSprites.length; i++) {
            numberSprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }

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

    public void generateBoardContents(){
        int randomNum;
        int mineCount;
        for (int i = 0; i < width/20-1; i++) {
            for (int j = 0; j < height/20-1; j++) {
                randomNum = (int) (Math.random() * 100);
                if (randomNum < difficulty) {
                    boardContents[i][j] = numberSprites[5];
                } else {
                    boardContents[i][j] = numberSprites[1];
                }
            }
        }
        for (int i = 0; i < width/20; i++) {
            for (int j = 0; j < height/20; j++) {
                mineCount = 0;
                if (boardContents[i][j] != numberSprites[5]) {
                    if (i > 0 && j > 0 && boardContents[i - 1][j - 1] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (i > 0 && boardContents[i - 1][j] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (i > 0 && j < height/20 - 1 && boardContents[i - 1][j + 1] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (j > 0 && boardContents[i][j - 1] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (j < height/20 - 1 && boardContents[i][j + 1] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (i < width/20 - 1 && j > 0 && boardContents[i + 1][j - 1] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (i < width/20 - 1 && boardContents[i + 1][j] == numberSprites[5]) {
                        mineCount++;
                    }
                    if (i < width/20 - 1 && j < height/20 - 1 && boardContents[i + 1][j + 1] == numberSprites[5]) {
                        mineCount++;
                    }
                    boardContents[i][j] = numberSprites[i-1];
                }
            }
        }
    }
    public BufferedImage[][] getboardContents(){
        return this.boardContents;
    }
}
