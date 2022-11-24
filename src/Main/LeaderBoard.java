package Main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/** This class creates and displays the leaderboard on the main menu
 * the names are retrieved using the FileReader object from a .txt file and stored in an ArrayList*/

public class LeaderBoard extends JPanel {

    public JPanel panel = new JPanel();
    private ArrayList<PlayerData> leaderBoardData;
    private final JTextArea textArea;
    private FileReader fileReader;
    public LeaderBoard(){

        Dimension size = new Dimension(250, 200);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);
        panel.setBackground(Color.WHITE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced",Font.PLAIN,12));

        fileReader = new FileReader();
        leaderBoardData = fileReader.loadFile();
        printScores();
        panel.add(textArea);
        add(panel);
    }

    /** This method gets objects stored in the arrayList and displays them in a textarea,
     * this text area is contained in the above JPanel
     * The scores are also sorted from lowest to highest*/
    public void printScores(){
        String topString = "Name ..................... Score\n";
        PlayerData tempPlayer;

        for (int i=0;i<leaderBoardData.size()-1;i++){
            if (leaderBoardData.get(i).getTime() > leaderBoardData.get(i+1).getTime()){
                tempPlayer = leaderBoardData.get(i);
                leaderBoardData.set(i, leaderBoardData.get(i+1));
                leaderBoardData.set(i+1,tempPlayer);
                i = 0;
            }
            if (leaderBoardData.get(i).getTime() > leaderBoardData.get(i+1).getTime()){
                tempPlayer = leaderBoardData.get(i);
                leaderBoardData.set(i, leaderBoardData.get(i+1));
                leaderBoardData.set(i+1,tempPlayer);
            }
        }
        for (PlayerData data: leaderBoardData) {
            String dots = "";
            for (int e=0;e<30-(data.getName().length()+Integer.toString(data.getTime()).length());e++){
                dots += ".";
            }
            topString += data.getName() + " " + dots + " " + data.getTime() + "\n";
        }
        textArea.append(topString);
    }
}
