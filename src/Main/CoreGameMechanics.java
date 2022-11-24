package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/** This class is the metaphorical bread and butter of the entire game
 * It handles core game mechanics such as clearing tiles, flagging tiles,
 * clearing adjacent tiles and win/lose conditions*/

public class CoreGameMechanics extends JPanel {

    int width, height, difficulty;
    private BufferedImage img;
    private final int[][] toplayerCells;
    private final int[][] cellsClicked;
    private final BufferedImage[] sprites;
    private final BufferedImage[][] board;
    private final BarPanel barpanel;
    private BoardCreator boardCreator;
    private String name;
    private boolean mouseActive = true;
    private FileReader fileReader;
    private SpritesImporter importer;

    public CoreGameMechanics(int width, int height, int difficulty, BarPanel barpanel) {

        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.barpanel = barpanel;

        sprites = new BufferedImage[16];

        board = new BufferedImage[getWidth() / 20][getWidth() / 20];
        cellsClicked = new int[getWidth() / 20][getHeight() /  20];
        toplayerCells = new int[getWidth() / 20][getHeight() / 20];

        MouseInput input = new MouseInput(this, barpanel);
        addMouseListener(input);

        Dimension size = new Dimension(width, height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        importer = new SpritesImporter();
        BufferedImage img = importer.importSpriteSheet("/tiles.png");
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = img.getSubimage(i * 16, 0, 16, 16);
        }

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        blankBoard();

        fileReader = new FileReader();
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

    /** This method resets all game data to their default states and creates a board using BoardCreator*/
    public void resetGame(int x, int y){
        boardCreator = new BoardCreator(getWidth() / 20, getHeight() / 20, getDifficulty(), x, y);
        barpanel.setFlags(boardCreator.returnTotalMineCount());
        barpanel.setFace(0);
        resetNumericalBoards();
    }
    /**In this class there's an array called "topLayerCells", this is identical to "numericalBoardPositions",
     * I could have just made a method in BoardCreator to get the contents of "numericalBoardPositions"
     * but that would have involved ALOT of method calls.
     * This method just copies the contents of numericalBoardPositions to topLayerCells*/
    public void resetNumericalBoards() {
        for (int i = 0; i < toplayerCells.length; i++) {
            for (int e = 0; e < toplayerCells[0].length; e++){
                toplayerCells[i][e] = boardCreator.returnNumerical(i,e);
                cellsClicked[i][e] = 0;
            }
        }
        repaint();
    }

    /**This method fills the board array with the number 9 sprite which is the blank
     * unlocked sprite*/
    public void blankBoard(){
        for (int i = 0; i < toplayerCells.length; i++) {
            for (int e = 0; e < toplayerCells[0].length; e++){
                board[i][e] = sprites[9];
            }
        }
        repaint();
    }

    /** Cells are drawn from left to right to fill the entire panel*/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= getWidth() / 20 - 1; i++) {
            for (int e = 0; e <= getHeight() / 20 - 1; e++) {
                g.drawImage(board[i][e], i * 20, e * 20, 20, 20, null);
            }
        }
    }

    /**Cells are revealed by first checking if the cursor location is within the board,
     * then the sprite at board[x][y] is swapped with the corresponding sprite from boardCreator
     * the same location is marked with a 1 in the cellsClicked array, this is used for the win condition*/
    public void revealSprite(int x, int y) {
        if (insideBoard(x, y)) {
            board[x][y] = boardCreator.getSpriteAtPosXY(x, y);
            cellsClicked[x][y] = 1;
        }
        repaint();
    }

    /**This is a recursive function for clearing adjacent empty cells, it took an entire week to debug.
     * essentially this just takes in the [x][y] coordinates of the cursor and first checks if any of the N,S,E,W cells,
     * are empty, if they are it reveals that cells. For each of those N,S,E,W cells, if they are empty,
     * it checks the NE,SE,NW,SW cells and clears them if they are empty.
     *
     * Finally, it recursively runs clearAdjacentCells on the N,S,E,W cells and continues doing so until no cells
     * are empty or a number*/
    public void clearAdjacentCells(int x, int y){

        if (insideBoard(x,y)){
            if (board[x][y] != sprites[0] && toplayerCells[x][y] == 0){

                if (insideBoard(x-1,y) && toplayerCells[x-1][y] > 0 && toplayerCells[x-1][y] < 9) {
                    revealSprite(x-1,y);
                    cellsClicked[x-1][y] = 1;
                    if (insideBoard(x-1,y+1)){
                        if (toplayerCells[x-1][y+1] > 0 && toplayerCells[x-1][y+1] < 9) {
                            revealSprite(x - 1, y+1);
                            cellsClicked[x - 1][y+1] = 1;
                        }
                    }
                    if (insideBoard(x-1,y-1)){
                        if (toplayerCells[x-1][y-1] > 0 && toplayerCells[x-1][y-1] < 9) {
                            revealSprite(x - 1, y-1);
                            cellsClicked[x-1][y-1] = 1;
                        }
                    }
                }
                if (insideBoard(x+1,y) && toplayerCells[x+1][y] > 0 && toplayerCells[x+1][y] < 9) {
                    revealSprite(x+1,y);
                    cellsClicked[x+1][y] = 1;
                    if (insideBoard(x+1,y+1)){
                        if (toplayerCells[x+1][y+1] > 0 && toplayerCells[x+1][y+1] < 9) {
                            revealSprite(x+1, y+1);
                            cellsClicked[x+1][y+1] = 1;
                        }
                    }
                    if (insideBoard(x+1,y-1)){
                        if (toplayerCells[x+1][y-1] > 0 && toplayerCells[x+1][y-1] < 9) {
                            revealSprite(x + 1, y-1);
                            cellsClicked[x+1][y-1] = 1;
                        }
                    }
                }
                if (insideBoard(x,y+1) && toplayerCells[x][y+1] > 0 && toplayerCells[x][y+1] < 9) {
                    revealSprite(x,y+1);
                    cellsClicked[x][y+1] = 1;
                }
                if (insideBoard(x,y-1)  && toplayerCells[x][y-1] > 0 && toplayerCells[x][y-1] < 9) {
                    revealSprite(x,y-1);
                    cellsClicked[x][y-1] = 1;
                }
                board[x][y] = sprites[0];
                cellsClicked[x][y] = 1;
                clearAdjacentCells(x - 1, y);
                clearAdjacentCells(x + 1, y);
                clearAdjacentCells(x, y - 1);
                clearAdjacentCells(x, y + 1);
            }
        }
        repaint();
    }

    /**This function is used exclusively by the lose condition, if you click on a mine it reveals all cells on the board
     * and disables mouse function*/
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

    /**This function, as you probably have already figured out changes a cells sprite to that of a flag.
     * It also increments the flag count in the BarPanel up or down depending on if a flag has been placed
     * or removed, it also sets the corresponding cell in the topLayerCells function to 10 which
     * corresponds to the "cleared mine" sprite. This is used for the win and lose condition later*/
    public void flagTile(int x, int y){
        if (insideBoard(x, y)){
            if (board[x][y] == sprites[9]){
                board[x][y] = sprites[10];
                toplayerCells[x][y] = 10;
                barpanel.incrementFlagsDown();
            } else if (board[x][y] == sprites[10]){
                board[x][y] = sprites[9];
                toplayerCells[x][y] = boardCreator.returnNumerical(x,y);
                barpanel.incrementFlagsUp();
            }
        }
        repaint();
    }
    /**Checks if the tile selected is already flagged */
    public boolean isFlagged(int x, int y){
        return toplayerCells[x][y] == 10;
    }
    /**Checks if the mouse is active */
    public boolean isMouseActive(){
        return mouseActive;
    }
    /**Checks if index selected is inside the board*/
    public boolean insideBoard(int x, int y){
        return x >= 0 && y >= 0 && x < getWidth() / 20 && y < getHeight() / 20;
    }

    /**To Determine if the game is lost it just checks if the cell clicked on contains "13" which corresponds to a mine.
     * If this does happen then the entire board is revealed and the Clock on the BarPanel is stopped*/
    public void loseCondition(int x, int y){
        if (insideBoard(x,y) && board[x][y] != sprites[9]) {
            if (toplayerCells[x][y] == 13) {
                revealFullBoard();
                board[x][y] = sprites[14];
                barpanel.setFace(4);
                barpanel.stopCounting();
            }
        }
    }

    /**Determining if a game is win is done by counting how many flags are correct (flags placed on mines)
     * and counting how many other tiles were cleared(clicked), if the result equals the total amount of cells
     * then the player must have won
     *
     * If the game is won then the barPanel face is set to cool shades face, the mouse is disabled and the clock is stopped
     * the game then asks for a name and saves it as a playerData object to be stored later. If a name isn't entered then
     * the score isn't saved*/
    public void winCondition(){
        int correctFlags = 0, tilesClicked = 0;
        for (int x= 0; x <= getWidth() / 20 - 1; x++) {
            for (int y = 0; y <= getHeight() / 20 - 1; y++) {
                if (toplayerCells[x][y] == 10){
                    if(toplayerCells[x][y] == 10 && boardCreator.returnNumerical(x,y) == 13) {
                        correctFlags++;
                    }
                }
            }
        }
        for (int x= 0; x < getWidth() / 20; x++) {                              //This is a really stupid way of creating a win condition
            for (int y = 0; y < getHeight() / 20; y++) {                        //But it's the only method I could get to work reliably
                if (cellsClicked[x][y] == 1){
                    tilesClicked++;
                }
            }
        }
        if (tilesClicked + correctFlags == ((getWidth() / 20) * (getHeight() / 20))) {
            barpanel.setFace(3);
            barpanel.stopCounting();
            mouseActive = false;
            boolean validName = false;
            do{
                name = JOptionPane.showInputDialog("Enter a name");
                if (name.length() < 10 && name.length() > 1){
                    validName = true;
                    PlayerData player = new PlayerData(barpanel.getTime(), name);
                    fileReader.addNewPlayer(player);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Error: Name cannot be less than 1 character and greater than 10","Error", JOptionPane.ERROR_MESSAGE);
                }
            }while(!validName);
        }
    }
}