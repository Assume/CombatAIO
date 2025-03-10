package scripts.CombatAIO.com.base.api.walking.custom.conditions;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.CombatAIO.com.base.api.walking.custom.types.DCondition;

public class DObjectIsOnScreenCondition implements DCondition {

	private static final long serialVersionUID = 6143724176927913399L;
	private int id;

	public DObjectIsOnScreenCondition(int id) {
		this.id = id;
	}

	@Override
	public boolean validate() {
		RSObject[] npcs = Objects.findNearest(50, id);
		if (npcs.length == 0)
			return false;
		return npcs[0].isOnScreen();
	}
	
	@Override
	public String toString() {
		return "object "+id +" is on screen";
	}

}
