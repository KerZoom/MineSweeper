package Main;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private int time;
    private String name;

    public PlayerData(int time, String name){
        this.time = time;
        this.name = name;
    }

    public int getTime() {
        return time;
    }
    public String getName() {
        return name;
    }
}
