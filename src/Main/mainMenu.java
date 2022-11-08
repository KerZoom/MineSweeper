package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class mainMenu {
    private int width;
    private int height;
    private int difficulty;
    private JFrame frame;

    public mainMenu(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        Dimension size = new Dimension(400, 600);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);

        JButton start = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        start.setText("Start game!");


        setHeight(300);
        setWidth(300);
        setDifficulty(30);
        String[] sizes = {"15x15","25x25","40x40","100x45"};
        JComboBox<String> sizeSelect = new JComboBox<>(sizes);
        sizeSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sizeChoice = sizeSelect.getItemAt(sizeSelect.getSelectedIndex());

                switch(sizeChoice){
                    case("15x15"):
                        setHeight(300);
                        setWidth(300);
                        setDifficulty(30);
                        break;
                    case("25x25"):
                        setHeight(500);
                        setWidth(500);
                        setDifficulty(80);
                        break;
                    case("40x40"):
                        setHeight(800);
                        setWidth(800);
                        setDifficulty(200);
                        break;
                    case("100x45"):
                        setHeight(900);
                        setWidth(2000);
                        setDifficulty(800);
                        break;
                }
            }
        });

        panel.add(start);
        panel.add(sizeSelect);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getDifficulty() {
        return difficulty;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void newGame(){
        frame.setVisible(false);
        new MineSweeper(getWidth(),getHeight(),getDifficulty());
    }

}
