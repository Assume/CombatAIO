package scripts.CombatAIO.com.base.api.general.walking.custom.background.conditions;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DCondition;

public class DNPCIsOnScreenCondition implements DCondition {

	private static final long serialVersionUID = 2902820086426435345L;

	private int id;

	public DNPCIsOnScreenCondition(int id) {
		this.id = id;
	}

	@Override
	public boolean validate() {
		RSNPC[] npcs = NPCs.findNearest(id);
		if (npcs.length == 0)
			return false;
		return npcs[0].isOnScreen();
	}

}
