package Main;

import java.io.*;
import java.util.ArrayList;

/** This class is used for reading/writing player data to a save file as PlayerData Objects*/

public class FileReader {

    private ArrayList<PlayerData> array;

    /** This method creates a file input stream to take in a .txt file then uses an object
     * input stream to read in ArrayList stored within the .txt file.
     * If an existing LeaderBoardData.txt file exists it just reads in the data, if not it
     * creates a new LeaderBoardData.txt file*/
    public ArrayList<PlayerData> loadFile() {
        File dataFile;
        try {
            FileInputStream fileInputStream = new FileInputStream("leaderBoardData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            array = (ArrayList<PlayerData>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            dataFile = new File("leaderBoardData.txt");
            try {
                dataFile.createNewFile();
                System.out.print("New LeaderBoardData File created");
                array = new ArrayList<>();
            } catch (IOException ignored) {
            }
        }
        return array;
    }

    /** This method takes in a single PlayerData object and stores it in an array that was loaded
     * using the LoadFile() method then stores that array back into the .txt file*/
    public void addNewPlayer(PlayerData player) {
        loadFile();
        array.add(player);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("leaderBoardData.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(array);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException ignored) {
        }
    }
}