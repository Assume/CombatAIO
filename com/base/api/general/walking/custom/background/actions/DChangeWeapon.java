package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.Equipment;
import scripts.priv.drennon.background.MonitorMain;

public class DChangeWeapon implements DAction {

	private int id;

	public DChangeWeapon(int id) {
		this.id = id;
	}

	private static final long serialVersionUID = 7718032557953070654L;

	@Override
	public void execute() {
		MonitorMain.pause();
		Equipment.equip(id);
		MonitorMain.unpause();
	}
	
	@Override
	public String toString() {
		return "change weapon to "+id;
	}

}
