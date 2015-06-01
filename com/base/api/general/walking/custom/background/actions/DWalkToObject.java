package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;

public class DWalkToObject implements DAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1358350808817698199L;
	private int id;

	public DWalkToObject(int id) {
		this.id = id;
	}

	@Override
	public void execute() {
		RSObject[] objects = Objects.findNearest(50, id);
		if (objects.length == 0)
			return;
		Walking.walkTo(objects[0]);
	}

	@Override
	public String toString() {
		return "walk to object "+id;
	}
	
}
