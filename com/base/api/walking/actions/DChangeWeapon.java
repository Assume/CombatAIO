package scripts.CombatAIO.com.base.api.walking.actions;

import scripts.CombatAIO.com.base.api.walking.types.CEquipment;
import scripts.CombatAIO.com.base.api.walking.types.DAction;

public class DChangeWeapon implements DAction {

	private int id;

	public DChangeWeapon(int id) {
		this.id = id;
	}

	private static final long serialVersionUID = 7718032557953070654L;

	@Override
	public void execute() {
		CEquipment.equip(id);
	}

	@Override
	public String toString() {
		return "change weapon to " + id;
	}

}
