package scripts.CombatAIO.com.base.api.walking.custom.actions;

import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.walking.custom.types.DAction;
import scripts.webwalker_logic.WebWalker;

public class DWalkToTile implements DAction {

	private int x, y;

	public DWalkToTile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private static final long serialVersionUID = 1583897354518605326L;

	@Override
	public void execute() {
		WebWalker.walkTo(new RSTile(x, y));
	}

	@Override
	public String toString() {
		return "walk to tile (" + x + "," + y + ")";
	}

}
