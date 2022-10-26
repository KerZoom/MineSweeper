package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseInput implements MouseListener {

    private gamePanel content;

    public mouseInput(gamePanel content){
        this.content = content;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        content.revealSprite(e.getX(),e.getY());
        content.loseCondition(e.getX(),e.getY());
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
