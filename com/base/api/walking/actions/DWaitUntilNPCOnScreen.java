package scripts.CombatAIO.com.base.api.walking.actions;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.walking.types.DAction;

public class DWaitUntilNPCOnScreen implements DAction {

	private static final long serialVersionUID = -816891936087515484L;
	
	
	int id;

	public DWaitUntilNPCOnScreen(int id) {
		this.id = id;
	}

	@Override
	public void execute() {
		Timing.waitCondition(new Condition() {

			@Override
			public boolean active() {
				RSNPC[] npc = NPCs.findNearest(id);
				if (npc.length == 0)
					return false;
				return npc[0].isOnScreen();
			}
		}, 10000);

	}

	@Override
	public String toString() {
		return "wait until NPC "+id+ "is on screen";
	}
	
}
