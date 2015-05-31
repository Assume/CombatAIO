package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.Clicking;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;

public class DClickObject implements DAction {

	private static final long serialVersionUID = -7067377803304479486L;

	int id;
	String action;

	public DClickObject(int id, String action) {
		this.id = id;
		this.action = action;
	}

	@Override
	public void execute() {
		RSObject[] objects = Objects.findNearest(99999, id);
		if (objects.length == 0)
			return;
		if (objects[0].isOnScreen())
			Clicking.click(action, objects[0]);

	}

}
