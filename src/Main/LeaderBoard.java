package Main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LeaderBoard extends JPanel {

    public JPanel panel = new JPanel();
    private String[] names;
    private int[] scores;
    private String[] output;
    private boolean fileExists = false;
    private ArrayList<PlayerData> leaderBoardData;
    private JTextArea textArea;
    public LeaderBoard(){

        Dimension size = new Dimension(250, 200);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);
        panel.setBackground(Color.WHITE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced",Font.PLAIN,12));

        names = new String[10];
        scores = new int[]{0,0,0,0,0,0,0,0,0,0};
        output = new String[10];
        Arrays.fill(names, " ");

        loadFile();
        printScores();
 /*     readFromFile();
        */
        panel.add(textArea);
        add(panel);
    }
    public void isInTopTen(String name, int score){
        int index = 0;
        for (int i=0;i<10;i++){
            if (score < scores[i]){
                for (int e=i;e<scores.length;e++){
                    int tempInt = scores[e];
                    scores[e] = score;
                    score = tempInt;
                }
            }
        }
        for (int i=0;i<10;i++){
            if (scores[i] == 0){
                index = i;
                break;
            }
        }
        System.out.print(index);
        scores[index] = score;
        names[index] = name;
   //     System.out.print(Arrays.toString(scores));
  //      System.out.print(Arrays.toString(names));
        //updateOutput();
    }
    public void loadFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("leaderBoardData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            leaderBoardData = (ArrayList<PlayerData>) objectInputStream.readObject();
            objectInputStream.close();
        }catch (IOException | ClassNotFoundException ignored){}
    }
    public void printScores(){
        String tempString = "Name ..................... Score\n";
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
            tempString += data.getName() + " " + dots + " " + data.getTime() + "\n";
        }
        for (PlayerData data: leaderBoardData){
            System.out.println(data.getName());
            System.out.println((data.getTime()));
        }
        System.out.println(tempString);
        textArea.append(tempString);
    }
}
