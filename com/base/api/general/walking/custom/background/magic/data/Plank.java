package scripts.CombatAIO.com.base.api.general.walking.custom.background.magic.data;

import java.io.Serializable;

/**
 * @author Nolan
 */
public enum Plank implements Serializable {
    
    NORMAL("Logs", "Plank", 70),
    OAK("Oak logs", "Oak plank", 175),
    TEAK("Teak logs", "Teak plank", 350),
    MAHOGANY("Mahogany logs", "Mahogany plank", 1050);
    
    private final String logName;
    private final String plankName;
    private final int cost;
    
    Plank(String logName, String plankName, int cost) {
        this.logName = logName;
        this.plankName = plankName;
        this.cost = cost;
    }
    
    public final String getLogName() {
        return this.logName;
    }
    
    public final String getPlankName() {
        return this.plankName;
    }
    
    public final int getCost() {
        return this.cost;
    }
    
    @Override
    public String toString() {
        return getPlankName();
    }
}
