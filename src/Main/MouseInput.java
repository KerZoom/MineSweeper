package Main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**This class controls all the mouse input for the game, the reason
   i used mouseReleased instead of mouseClicked is to allow the player to
   move the cursor if they accidentally clicked the wrong tile but haven't
   released it yet you can move the cursor to the correct tile

   roundX and roundY take the cursor coordinates and divide them by 2 and multiply them by 0.1
   to get a single digit that's a whole number

 * Example: X(220.8) Y(55.7) = X(22) Y(5), these coordinates are use as a 2d array index multiple times*/

public class MouseInput implements MouseListener {

    private final CoreGameMechanics content;
    private boolean gameStart = true;
    private final BarPanel barpanel;

    public MouseInput(CoreGameMechanics content, BarPanel barpanel){
        this.content = content;
        this.barpanel = barpanel;
    }

    public void mouseClicked(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {
        int roundX = (int) Math.floor(e.getX() / 2 * 0.1);
        int roundY = (int) Math.floor(e.getY() / 2 * 0.1);

        if (e.getSource() == content) {
            if (!gameStart) {
                if (content.isMouseActive()) {
                    if (SwingUtilities.isLeftMouseButton(e) && !content.isFlagged(roundX, roundY)) {
                        content.revealSprite(roundX, roundY);
                        content.loseCondition(roundX, roundY);
                        content.winCondition();
                        content.clearAdjacentCells(roundX, roundY);
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        content.flagTile(roundX, roundY);
                        content.winCondition();
                    }
                }
            } else {
                content.resetGame(roundX, roundY);
                gameStart = false;
                content.revealSprite(roundX, roundY);
                content.clearAdjacentCells(roundX, roundY);
                barpanel.startCounting();
            }
        }
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}
