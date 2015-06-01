package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;

public class DWalkToNPC implements DAction {

	
	private static final long serialVersionUID = 823898076809429382L;
	private int id;

	public DWalkToNPC(int id) {
		this.id = id;
	}

	@Override
	public void execute() {
		RSNPC[] objects = NPCs.findNearest(50, id);
		if (objects.length == 0)
			return;
		Walking.walkTo(objects[0]);
	}
	
	@Override
	public String toString() {
		return "walk to NPC "+id;
	}
	

}
