package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;

public class DWaitUntilObjectOnScreen implements DAction {

	private static final long serialVersionUID = -6850168527967421709L;

	int id;

	public DWaitUntilObjectOnScreen(int id) {
		this.id = id;
	}

	@Override
	public void execute() {
		Timing.waitCondition(new Condition() {

			@Override
			public boolean active() {
				RSObject[] npc = Objects.findNearest(50, id);
				if (npc.length == 0)
					return false;
				return npc[0].isOnScreen();
			}
		}, 10000);

	}

}
