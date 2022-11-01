package Main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseInput implements MouseListener {

    private final gamePanel content;

    public mouseInput(gamePanel content){
        this.content = content;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        int roundX = (int) Math.floor(e.getX()/2 * 0.1);
        int roundY = (int) Math.floor(e.getY()/2 * 0.1);
        if (content.isMouseActive()) {
            if (SwingUtilities.isLeftMouseButton(e) && !content.isFlagged(roundX, roundY)) {
                content.revealSprite(roundX, roundY);
                content.loseCondition(roundX, roundY);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                content.flagTile(roundX, roundY);
            }
        }
        content.clearCells(roundX,roundY);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
