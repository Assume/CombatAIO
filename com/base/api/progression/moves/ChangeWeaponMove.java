package scripts.CombatAIO.com.base.api.progression.moves;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.Equipment;
import scripts.CombatAIO.com.base.api.progression.CProgressionAction;

public class ChangeWeaponMove extends CProgressionAction {

	private int id;

	public ChangeWeaponMove(int id) {
		this.id = id;
	}

	@Override
	public void execute() {
		Equipment.equip(id);
	}

}
