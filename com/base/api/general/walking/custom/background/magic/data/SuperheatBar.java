package scripts.CombatAIO.com.base.api.general.walking.custom.background.magic.data;

import java.io.Serializable;
import org.tribot.api2007.Inventory;

/**
 * @author Starfox
 */
public enum SuperheatBar implements Serializable {
    
    BRONZE("Bronze bar", new String[]{"Tin ore", "Copper ore"}, new int[]{1, 1}),
    IRON("Iron bar", new String[]{"Iron ore"}, new int[]{1}),
    SILVER("Silver bar", new String[]{"Silver ore"}, new int[]{1}),
    STEEL("Steel bar", new String[]{"Iron ore", "Coal"}, new int[]{1, 2}),
    GOLD("Gold bar", new String[]{"Gold ore"}, new int[]{1}),
    MITHRIL("Mithril bar", new String[]{"Mithril ore", "Coal"}, new int[]{1, 4}),
    ADAMANT("Adamant bar", new String[]{"Adamantite ore", "Coal"}, new int[]{1, 6}),
    RUNE("Rune bar", new String[]{"Runite ore", "Coal"}, new int[]{1, 8});
    
    private final String name;
    private final String[] requiredOres;
    private final int[] numberOres;
    
    SuperheatBar(String name, String[] requiredOres, int[] numberOres) {
        this.name = name;
        this.requiredOres = requiredOres;
        this.numberOres = numberOres;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getRequiredOres() {
        return this.requiredOres;
    }
    
    public int[] getNumberOres() {
        return this.numberOres;
    }
    
    public int numberOfPossibleBars() {
        int total = 0;
        for (int i : getNumberOres()) {
            total += i;
        }
        int free = 28 - (Inventory.getAll().length - Inventory.find(getRequiredOres()).length);
        return free / total;
    }
    
    public int maxPossibleBars() {
        int total = 0;
        for (int i : getNumberOres()) {
            total += i;
        }
        return 27 / total;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
