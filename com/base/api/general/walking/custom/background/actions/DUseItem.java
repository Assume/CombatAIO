package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DUseItem implements DAction {

	private static final long serialVersionUID = 5921019993899581512L;

	private int id;
	private String action;

	public DUseItem(String action, int id) {
		this.action = action;
		this.id = id;
	}

	@Override
	public void execute() {
		MonitorMain.pause();
		RSItem[] items = Inventory.find(id);
		if (items.length == 0)
			return;
		items[0].click(action);
		MonitorMain.unpause();
	}

	@Override
	public String toString() {
		return "click item " + id + " with action " + action;
	}
}
