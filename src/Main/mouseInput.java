package Main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseInput implements MouseListener {

    private final gamePanel content;
    private boolean gameStart = true;
    private final barPanel barpanel;

    public mouseInput(gamePanel content, barPanel barpanel){
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
                        content.clearCells(roundX, roundY);
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        content.flagTile(roundX, roundY);
                        content.winCondition();
                    }
                }
            } else {
                content.startGame(roundX, roundY);
                gameStart = false;
                content.revealSprite(roundX, roundY);
                content.clearCells(roundX, roundY);
                barpanel.startCounting();
            }
        }
    }
    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
