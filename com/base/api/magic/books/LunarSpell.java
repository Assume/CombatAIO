package scripts.CombatAIO.com.base.api.magic.books;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceMaster;

import scripts.CombatAIO.com.base.api.magic.Rune;
import scripts.CombatAIO.com.base.api.magic.Staff;




/**
 * Constants that represent spells from the Lunar spell book.
 * 
 * @author Nolan
 */
public enum LunarSpell implements Serializable {

    //TELEPORT SPELLS
    MOONCLAN_TELEPORT("Moonclan Teleport", 69, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.EARTH}, new int[]{1, 2, 2}),
    TELE_GROUP_MOONCLAN("Tele Group Moonclan", 70, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.EARTH}, new int[]{1, 2, 4}),
    WATERBIRTH_TELEPORT("Waterbirth Teleport", 72, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{1, 2, 1}),
    TELE_GROUP_WATERBIRTH("Tele Group Waterbirth", 73, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{1, 2, 5}),
    BARBARIAN_TELEPORT("Barbarian Teleport", 75, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.FIRE}, new int[]{2, 2, 3}),
    TELE_GROUP_BARBARIAN("Tele Group Barbarian", 76, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.FIRE}, new int[]{2, 2, 6}),
    KHAZARD_TELEPORT("Khazard Teleport", 78, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{2, 2, 4}),
    TELE_GROUP_KHAZARD("Tele Group Khazard", 79, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{2, 2, 8}),
    FISHING_GUILD_TELEPORT("Fishing Guild Teleport", 85, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{3, 3, 10}),
    TELE_GROUP_FISHING_GUILD("Tele Group Fishing Guild", 86, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{3, 3, 14}),
    CATHERBY_TELEPORT("Catherby Teleport", 87, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{3, 3, 10}),
    TELE_GROUP_CATHERBY("Tele Group Catherby", 88, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{3, 3, 15}),
    ICE_PLATEAU_TELEPORT("Ice Plateau Teleport", 89, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{3, 3, 8}),
    TELE_GROUP_ICE_PLATEAU("Tele Group Ice Plateau", 90, new Rune[]{Rune.LAW, Rune.ASTRAL, Rune.WATER}, new int[]{3, 3, 16}),
    
    //OTHER SPELLS
    BAKE_PIE("Bake Pie", 65, new Rune[]{Rune.ASTRAL, Rune.FIRE, Rune.WATER}, new int[]{1, 5, 4}),
    HUMIDIFY("Humidify", 68, new Rune[]{Rune.ASTRAL, Rune.FIRE, Rune.WATER}, new int[]{1, 1, 3}),
    HUNTER_KIT("Hunter Kit", 71, new Rune[]{Rune.ASTRAL, Rune.EARTH}, new int[]{2, 3}),
    SUPERGLASS_MAKE("Superglass Make", 77, new Rune[]{Rune.ASTRAL, Rune.FIRE, Rune.AIR}, new int[]{2, 6, 10}),
    STRING_JEWELRY("String Jewelry", 80, new Rune[]{Rune.ASTRAL, Rune.EARTH, Rune.WATER}, new int[]{2, 10, 5}),
    PLANK_MAKE("Plank Make", 86, new Rune[]{Rune.NATURE, Rune.ASTRAL, Rune.EARTH}, new int[]{1, 2, 15});

    public static final int LUNAR_BOOK_MASTER_INDEX = 430;
    
    private final String name;
    private final int requiredLevel;
    private final Rune[] requiredRunes;
    private final int[] numberOfRunes;

    LunarSpell(final String name, int requiredLevel, Rune[] requiredRunes, int[] numberOfRunes) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.requiredRunes = requiredRunes;
        this.numberOfRunes = numberOfRunes;
    }

    /**
     * Gets the name of the spell.
     * 
     * @return The name of the spell.
     */
    public final String getName() {
        return this.name;
    }
    
    /**
     * Gets the level required to cast the spell.
     * 
     * @return The level required to cast the spell.
     */
    public final int getRequiredLevel() {
        return this.requiredLevel;
    }
    
    /**
     * Gets the required runes to cast the Spell.
     *
     * @return The required runes to cast the Spell.
     */
    public Rune[] getRequiredRunes() {
        return this.requiredRunes;
    }

    /**
     * Gets the number of runes required to cast the Spell.
     *
     * @return The number of runes required to cast the Spell.
     */
    private int[] getNumberOfRunes() {
        return this.numberOfRunes;
    }

    /**
     * Gets the id's of the required Runes to cast the spell.
     *
     * @return An array of ints representing the id's of the Runes.
     */
    public int[] getRequiredRuneIds() {
        int[] temp = new int[getRequiredRunes().length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = getRequiredRunes()[i].getId();
        }
        return temp;
    }
    
    /**
     * Checks to see if the player has the required level to cast this spell.
     * 
     * @return True if the player has the required level, false otherwise.
     */
    public final boolean hasRequiredLevel() {
        return Skills.SKILLS.MAGIC.getActualLevel() >= getRequiredLevel();
    }

    /**
     * Checks to see if the player has the required runes to cast the Spell. Handles all staffs.
     *
     * @return true if the player has the required runes; false otherwise.
     */
    public boolean hasRequiredRunes() {
        final List<Rune> currentRunes = new ArrayList<>();
        final int requiredAmount = getRequiredRunes().length;

        Staff staff = Staff.getEquipped();

        //check the runes in the inventory
        for (int i = 0; i < getRequiredRunes().length; i++) {
            if (Inventory.getCount(getRequiredRunes()[i].getId()) >= getNumberOfRunes()[i]) {
                currentRunes.add(getRequiredRunes()[i]);
            }
        }

        //check the runes that the staff provides
        if (staff != null && currentRunes.size() < requiredAmount) {
            for (Rune r : staff.getRunes()) {
                for (Rune r2 : getRequiredRunes()) {
                    if (r.getId() == r2.getId() && !currentRunes.contains(r)) {
                        currentRunes.add(r);
                    }
                }
            }
        }
        
        //check the amount of the current runes against the amount of required runes; if they are the same, we have the required runes.
        return currentRunes.size() == requiredAmount;
    }

    /**
     * Gets the interface of the spell.
     * 
     * @return The interface of the spell.
     */
    public final RSInterface getInterface() {
        final RSInterfaceMaster book = Interfaces.get(LUNAR_BOOK_MASTER_INDEX);
        if (book != null) {
            RSInterface[] children = book.getChildren();
            if (children != null && children.length > 0) {
                for (RSInterface child : children) {
                    if (child != null) {
                        String spellName = child.getComponentName();
                        if (spellName != null && spellName.contains(getName())) {
                            return child;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Selects the spell.
     * <p>
     * This method will return true if the spell is already selected.
     * This method will open the magic tab if it is not already open.
     * 
     * @return True if the spell was selected successfully, false otherwise.
     */
    public boolean select() {
        String uptext = Game.getUptext();
        if (uptext != null && uptext.contains(getName() + " ->")) {
            return true;
        }
        RSInterface spell = getInterface();
        if (spell == null) {
            return false;
        }
        return TABS.MAGIC.open() && Clicking.click("Cast", spell);
    }
    
    /**
     * Hovers the spell.
     * <p>
     * This method will open the magic tab if it is not already open.
     * 
     * @return True if the spell was hovered successfully, false otherwise.
     */
    public boolean hover() {
        RSInterface spell = getInterface();
        if (spell == null) {
            return false;
        }
        return TABS.MAGIC.open() && Clicking.hover(spell);
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    
}
