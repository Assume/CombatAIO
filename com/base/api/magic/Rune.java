package scripts.CombatAIO.com.base.api.magic;

import java.io.Serializable;

/**
 * @author Starfox
 * Rune constants.
 */
public enum Rune implements Serializable {
    AIR("Air rune", 556),
    FIRE("Fire rune", 554),
    WATER("Water rune", 555),
    EARTH("Earth rune", 557),
    MIND("Mind rune", 558),
    BODY("Body rune", 559),
    COSMIC("Cosmic rune", 564),
    CHAOS("Chaos rune", 562),
    NATURE("Nature rune", 561),
    LAW("Law rune", 563),
    ASTRAL("Astral rune", 9075),
    SOUL("Soul rune", 566),
    BLOOD("Blood rune", 565),
    DEATH("Death rune", 560);
    
    private final String name;
    private final int id;
    
    Rune(String name, int id) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * Gets the name of the Rune.
     * @return The name of the Rune.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Gets the id of the Rune.
     * @return The id of the Rune.
     */
    public int getId() {
        return this.id;
    }
}
