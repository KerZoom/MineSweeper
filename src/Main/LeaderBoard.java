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
    private File data;
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


        createOrFindFile();
        readFromFile();
        printScores();
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
        updateOutput();
    }
    public void createOrFindFile(){
        try{
            data = new File("leaderBoardData.txt");
            if (data.createNewFile()){
                System.out.print("New leader board data file created\n\n");
            }
            else{
                System.out.print("Leader board data file found\n\n");
            }
            fileExists = true;
        }catch (IOException e){
            System.out.print("Error occurred whilst reading/creating file\n\n");
        }
    }
    public void readFromFile(){
        try {
            int lineIndex = 0;
            Scanner reader = new Scanner(data);
            String text;
            while (lineIndex < 9) {
                text = reader.nextLine();
                lineIndex++;
                output[lineIndex] = text;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found\n\n");
        }
    }
    public void updateOutput(){
        String dots = "";
        if (fileExists) {
            try {
                FileWriter myWriter = new FileWriter("leaderBoardData.txt");
                for (int i = 0; i < 10; i++) {
                    dots = "";
                    for (int e=0;e<30-(names[i].length()+Integer.toString(scores[i]).length());e++){
                        dots += ".";
                    }
                    myWriter.write(names[i] + " " + dots + " " + scores[i] + "\n");

                    try {
                        int lineIndex = 0;
                        Scanner reader = new Scanner(data);
                        int score = 0, a = 0;
                        String text, name, stringScore = "";
                        char A;
                        while (lineIndex < 9) {
                            text = reader.nextLine();
                            a = 0;
                            do{
                                A = text.charAt((text.length()-1)-a);
                                stringScore += Character.toString(A);
                                a++;
                            }while (A != ' ');
                            score = Integer.parseInt(new StringBuilder(stringScore).reverse().toString());
                            scores[lineIndex] = score;
                            lineIndex++;
                        }
                        System.out.print(Arrays.toString(scores));
                        reader.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found\n\n");
                    }
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        }
    }
    public void printScores(){
        String tempString = "Name ..................... Score\n";
        for (int i = 1; i < 10; i++) {
            tempString += output[i] + "\n";
        }
        System.out.println(tempString);
        textArea.append(tempString);
    }
}
