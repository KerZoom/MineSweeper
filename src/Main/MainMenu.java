package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**This class creates the main menu you see at the start of the program,
 * it's just a jframe with a button, a combobox and an instance of the LeaderBoard
 *
 * One interesting function I've never used is "abstract action", this is used to detect
 * when the new game button is clicked
 *
 * Another cool function is "lambdas", Intellij recommend one so after researching them
 * I added one for the combo box. It also uses an enhanced for loop, for some reason
 * enhanced for loops don't support the default case so by default it just sets it to 15x15*/

public class MainMenu {
    private int width;
    private int height;
    private int difficulty;
    private final JFrame frame;
    private final LeaderBoard leaderBoard;

    public MainMenu(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));

        leaderBoard = new LeaderBoard();

        JPanel panel = new JPanel();
        Dimension size = new Dimension(300, 50);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);

        JButton start = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        start.setText("Start game!");

        //These are default values, if you don't select anything in the combo box it will use these instead
        setHeight(300);
        setWidth(300);
        setDifficulty(1);      // <-- Change this to 1 and don't select anything in the menu for an easy win

        String[] sizes = {"15x15","25x25","40x40","80x40"};
        JComboBox<String> sizeSelect = new JComboBox<>(sizes);
        sizeSelect.addActionListener(e -> {
            String sizeChoice = sizeSelect.getItemAt(sizeSelect.getSelectedIndex());

            switch (sizeChoice) {
                case ("15x15") -> {
                    setHeight(300);
                    setWidth(300);
                    setDifficulty(25);
                }
                case ("25x25") -> {
                    setHeight(500);
                    setWidth(500);
                    setDifficulty(80);
                }
                case ("40x40") -> {
                    setHeight(800);
                    setWidth(800);
                    setDifficulty(200);
                }
                case ("80x40") -> {
                    setHeight(800);
                    setWidth(1600);
                    setDifficulty(600);
                }
            }
        });
        panel.add(start);
        panel.add(sizeSelect);
        frame.add(panel);
        frame.add(leaderBoard);
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

    /**This is what actually starts a new game of Mine sweeper */
    public void newGame(){
        frame.setVisible(false);
        MineSweeper mineSweeper = new MineSweeper(getWidth(), getHeight(), getDifficulty());
    }
}
