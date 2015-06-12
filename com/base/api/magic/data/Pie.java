package scripts.CombatAIO.com.base.api.magic.data;

/**
 * @author Nolan
 */
public enum Pie {
    
    REDBERRY("Redberry pie", 10), 
    MEAT("Meat pie", 20), 
    MUD("Mud pie", 29), 
    APPLE("Apple pie", 30), 
    GARDEN("Garden pie", 34), 
    FISH("Fish pie", 47), 
    ADMIRAL("Admiral pie", 70), 
    WILD("Wild pie", 85), 
    SUMMER("Summer pie", 95);
    
    private final String name;
    private final int requiredLevel;
    
    Pie(String name, int requiredLevel) {
        this.name = name;
        this.requiredLevel = requiredLevel;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final int getRequiredLevel() {
        return this.requiredLevel;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
