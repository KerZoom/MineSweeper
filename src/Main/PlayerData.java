package Main;

import java.io.Serializable;

/**This is PlayerData object, it stores the time and name of the player
   to be serialized later*/

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
