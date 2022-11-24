package Main;

import java.awt.image.BufferedImage;

/** This class is responsible for creating a board with a pre-determined quantity of mines
 * then determining how many mines surround each cell*/

public class BoardCreator {

    private int width, height, difficulty;
    private final BufferedImage[] tileSprites;
    private int[] temporaryArray;
    private int[][] numericalBoardPositions;
    private SpritesImporter importer;

    public BoardCreator(int width, int height, int difficulty, int x, int y) {

        this.width = width;
        this.height = height;
        this.difficulty = difficulty;

        tileSprites = new BufferedImage[16];
        numericalBoardPositions = new int[width][height];

        importer = new SpritesImporter();
        BufferedImage img = importer.importSpriteSheet("/tiles.png");
        for (int i = 0; i < tileSprites.length; i++) {
            tileSprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }

        temporaryArray = generateMineLocations(x,y);
        arrayTransfer(temporaryArray, numericalBoardPositions, width, height);
        generateNumbers(numericalBoardPositions,width,height);
    }

    /**
     *  GenerateMineLocations generates mine locations using
     *  Math.random(), to ensure mines aren't placed on top of each other it first checks if the
     *  selected index has a mine, if it doesn't a mine is added to that index, else the while loop resets
     *  and tries a different index
     *
     *  To make sure a mine is not placed directly under the cursor on the first turn, the firstTurnIndex
     *  is calculated using the rounded XY coordinates obtained from mouseInput, the same check that ensures
     *  a mine is not placed on top of another mine also checks it isn't the same index as firstTurnIndex.
    */

    // Originally I used a shuffle for this and in hindsight I probably should have just used that
    public int[] generateMineLocations(int x, int y) {
        int firstTurnIndex;
        firstTurnIndex = (y * width + x + 1);
        int[] temporaryArray = new int[width * height];
        for (int i = 0; i < this.difficulty; i++) {
            boolean valid = false;
            while (!valid) {
                int tempnum = (int) (Math.random() * width * height);
                if (temporaryArray[tempnum] != 13 && tempnum != firstTurnIndex) {
                    System.out.print(tempnum + " ");
                    temporaryArray[tempnum] = 13;
                    valid = true;
                }
            }
        }
        return temporaryArray;
    }

    /** This method simply transfers a 1 dimension array to a 2 dimensional array, you might notice that
     * the entire board is represented as numbers, if you multiply those numbers by 16 it gives
     * you the XY coordinates of each sprite in the numbers.png file*/
    private void arrayTransfer(int[] oneDArray, int[][] twoDArray, int width, int height){
        int e = 0;
        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++) {
                twoDArray[i][j] = oneDArray[e];
                e++;
            }
        }
    }

    /** This method generates the numbers for each cell by counting in a clockwise pattern
     * how many mines surround a cell, the maximum is 9, it then sets the origin cell the number of
     * mines counted
     *
     * If you encounter a bug where a mine appears under your cursor at the start of the game,
     * blame this method.
     * Fixing it would have required at least a week of play testing to narrow down exactly which specific
     * pattern of mines and XY coordinates is causing it to somehow count to exactly 13*/

    public void generateNumbers(int[][] array, int width, int height){
    int mineCount;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mineCount = 0;
                if (array[i][j] != 13) {
                    if (i > 0 && j > 0 && array[i - 1][j - 1] == 13) {
                        mineCount++;
                    }
                    if (i > 0 && array[i - 1][j] == 13) {
                        mineCount++;
                    }
                    if (i > 0 && j < height-1 && array[i - 1][j + 1] == 13) {
                        mineCount++;
                    }
                    if (j > 0 && array[i][j - 1] == 13) {
                        mineCount++;
                    }
                    if (j < height-1 && array[i][j + 1] == 13) {
                        mineCount++;
                    }
                    if (i < width-1 && j > 0 && array[i + 1][j - 1] == 13) {
                        mineCount++;
                    }
                    if (i < width-1  && array[i + 1][j] == 13) {
                        mineCount++;
                    }
                    if (i < width-1  && j < height-1 && array[i + 1][j + 1] == 13) {
                        mineCount++;
                    }
                    array[i][j] = mineCount;
                }
            }
        }
    }

    /** This method returns the sprite associated with the number at a given XY coordinate, 0-9 are numbers 1-8,
     * 13 is a mine*/
    public BufferedImage getSpriteAtPosXY(int x, int y){
        return tileSprites[numericalBoardPositions[x][y]];
    }
    /** This method returns the number at a given XY coordinate, 0-9 are numbers 1-8,
     * 13 is a mine*/
    public int returnNumerical(int x, int y){
        return numericalBoardPositions[x][y];
    }
    /** It was easier to just add a method here to return total mines rather than having an instance of mainMenu in barpanel
     */
    public int returnTotalMineCount(){
        return this.difficulty;
    }
}
