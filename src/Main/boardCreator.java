package Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class boardCreator {

    private int width, height, difficulty, totalMineCount = 0;
    private BufferedImage img;
    private final BufferedImage[] tileSprites;
    private final int[][] numericalBoardPositions;

    public boardCreator(int width, int height, int difficulty, int x, int y) {

        this.width = width;
        this.height = height;
        this.difficulty = difficulty;

        tileSprites = new BufferedImage[16];
        numericalBoardPositions = new int[width][height];

        importTileSprites();
        generateRandomBoard(x,y);
    }

    private void importTileSprites() {
        try {
            InputStream stream = getClass().getResourceAsStream("/tiles.png");
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found - tiles.png");
        }
        for (int i = 0; i < tileSprites.length; i++) {
            tileSprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    public void generateRandomBoard(int x, int y) {
        int mineCount, indexNum;

        indexNum = (y*width + x);
        System.out.print(indexNum);
        int[] temporaryArray = new int[width*height];
        for (int i=0;i < this.difficulty;i++){
            boolean valid = false;
            while (!valid) {
                int tempnum = (int)(Math.random() * width*height);
                if (temporaryArray[tempnum] != 13 && tempnum != indexNum) {
                    temporaryArray[tempnum] = 13;
                    valid = true;
                }
            }
        }

        int e = 0;
        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++) {
                numericalBoardPositions[i][j] = temporaryArray[e];
                e++;
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

    public int returnNumerical(int x, int y){
        return numericalBoardPositions[x][y];
    }
    public int returnTotalMineCount(){
        return this.difficulty;
    }
}
