package scripts.CombatAIO.com.base.main.utils;

import scripts.starfox.interfaces.ui.Listable;

/**
 *
 * @author Spencer
 */
public class DumpItem implements Listable {

    private final String name;
    private final int id;
    private final boolean stackable;
    private final boolean membersOnly;

    public DumpItem(String name, int id, boolean stackable, boolean membersOnly) {
        this.name = name;
        this.id = id;
        this.stackable = stackable;
        this.membersOnly = membersOnly;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isStackable() {
        return stackable;
    }

    public boolean isMembersOnly() {
        return membersOnly;
    }
    
    public boolean matches(int id) {
        return id == this.id;
    }

    @Override
    public String getListDisplay() {
        return name + " [" + id + "]";
    }

    @Override
    public String searchName() {
        return getListDisplay();
    }

    @Override
    public String getPulldownDisplay() {
        return getListDisplay();
    }

    
    public static DumpItem fromString(String string) {
        try {
            String[] strings = string.split("\\:");
            String name = strings[0];
            int id = Integer.parseInt(strings[1]);
            boolean stackable = Boolean.parseBoolean(strings[2]);
            boolean members = Boolean.parseBoolean(strings[3]);
            return new DumpItem(name, id, stackable, members);
        } catch (Exception e) {
            return null;
        }
    }
}
