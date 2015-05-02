package scripts.CombatAIO.com.base.api.general.walking.custom.background.magic;

import java.io.Serializable;
import org.tribot.api2007.Equipment;

/**
 * @author Starfox
 * Staff constants.
 */
public enum  Staff implements Serializable {
    
    STAFF_OF_AIR("Staff of air", 1381, new Rune[]{Rune.AIR}),
    AIR_BATTLESTAFF("Air battlestaff", 1397, new Rune[]{Rune.AIR}),
    MYSTIC_AIR_STAFF("Mystic air staff", 1405, new Rune[]{Rune.AIR}),
    STAFF_OF_WATER("Staff of water", 1383, new Rune[]{Rune.WATER}),
    WATER_BATTLESTAFF("Water battlestaff", 1395, new Rune[]{Rune.WATER}),
    MYSTIC_WATER_STAFF("Mystic water staff", 1403, new Rune[]{Rune.WATER}),
    STAFF_OF_EARTH("Staff of earth", 1385, new Rune[]{Rune.EARTH}),
    EARTH_BATTLESTAFF("Earth battlestaff", 1399, new Rune[]{Rune.EARTH}),
    MYSTIC_EARTH_STAFF("Mystic earth staff", 1407, new Rune[]{Rune.EARTH}),
    STAFF_OF_FIRE("Staff of fire", 1387, new Rune[]{Rune.FIRE}),
    FIRE_BATTLESTAFF("Fire battlestaff", 1393, new Rune[]{Rune.FIRE}),
    MYSTIC_FIRE_STAFF("Mystic fire staff", 1401, new Rune[]{Rune.FIRE}),
    LAVA_BATTLESTAFF("Lava battlestaff", 3053, new Rune[]{Rune.FIRE, Rune.EARTH}),
    MYSTIC_LAVA_STAFF("Mystic lava staff", 3054, new Rune[]{Rune.FIRE, Rune.EARTH}),
    MUD_BATTLESTAFF("Mud battlestaff", 6562, new Rune[]{Rune.WATER, Rune.EARTH}),
    MYSTIC_MUD_STAFF("Mystic mud staff", 6563, new Rune[]{Rune.WATER, Rune.EARTH}),
    STEAM_BATTLESTAFF("Steam battlestaff", 11787, new Rune[]{Rune.WATER, Rune.FIRE}),
    MYSTIC_STEAM_STAFF("Mystic steam staff", 11738, new Rune[]{Rune.WATER, Rune.FIRE}),
    SMOKE_BATTLESTAFF("Smoke battlestaff", 11998, new Rune[]{Rune.FIRE, Rune.AIR}),
    MYSTIC_SMOKE_STAFF("Mystic smoke staff", 12000, new Rune[]{Rune.FIRE, Rune.AIR});

    private final String name;
    private final int id;
    private final Rune[] runes;
    
    /**
     * Constructs a new Staff.
     * 
     * @param name The name of the staff.
     * @param id The id of the staff.
     * @param runes The runes that the staff provides.
     */
    private Staff(String name, int id, Rune[] runes){
        this.name = name;
        this.id = id;
        this.runes = runes;
    }
    
    /**
     * Gets the real name of the Staff.
     * 
     * @return The real name of the Staff.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the id of the Staff.
     * 
     * @return The id of the Staff.
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Checks to see if the local player has the staff equipped.
     * 
     * @return True if the staff is equipped, false otherwise.
     */
    public boolean isEquipped() {
        return Equipment.isEquipped(getId());
    }
    
    /**
     * Gets the staff that the player has equipped.
     *
     * @return The staff equipped. Null if no staff is equipped.
     */
    public static Staff getEquipped() {
        for (Staff staff : values()) {
            if (Equipment.isEquipped(staff.getId())) {
                return staff;
            }
        }
        return null;
    }

    /**
     * Gets the runes that the Staff supplies.
     * @return The runes that the Staff supplies.
     */
    public Rune[] getRunes() {
        return this.runes;
    }
}
