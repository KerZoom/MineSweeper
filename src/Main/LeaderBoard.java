package Main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class LeaderBoard extends JPanel {

    public JPanel panel = new JPanel();
    private String[] names;
    private int[] scores;
    private String[] output;
    private boolean fileExists = false;

    public LeaderBoard(){

        Dimension size = new Dimension(300, 400);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);
        panel.setBackground(Color.WHITE);


        names = new String[10];
        scores = new int[]{0,0,0,0,0,0,0,0,0,0};
        output = new String[10];
        Arrays.fill(names, "...");
        isInTopTen("Bobby",20);
        isInTopTen("Jeff",10);

        try{
            File data = new File("leaderBoardData.txt");
            if (data.createNewFile()){
                System.out.print("New leader board data file created\n\n");
            }
            else{
                System.out.print("Leader board data file found\n\n");
            }
            fileExists = true;
            try {
                int lineIndex = 0;
                Scanner reader = new Scanner(data);
                while (reader.hasNextLine()) {
                    String text = reader.nextLine();
                    output[lineIndex++] = text;
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found\n\n");
            }
        }catch (IOException e){
            System.out.print("Error occurred whilst reading/creating file\n\n");
        }
        add(panel);
        repaint();
    }
    public void isInTopTen(String name, int score){
        int index = 0;
        for (int i=0;i<10;i++){
            if (score < scores[i]){
                int tempNum = 0;
                String tempString = "";
                tempNum = scores[i];
                tempString = names[i];
                scores[i] = score;
                if (i+1 < scores.length){
                    scores[i+1] = score;
                    names[i+1] = name;
                }
                System.out.println(index);
            }
        }
        updateDataFile();
    }
    public void updateDataFile(){
        if (fileExists) {
            try {
                FileWriter myWriter = new FileWriter("leaderBoardData.txt");
                for (int i = 0; i < 10; i++) {
                    myWriter.write(names[i] + " " + scores[i] + "\n");
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 100; i+=10) {
            g.drawString(output[i/10],80,i);
        }
    }
}
