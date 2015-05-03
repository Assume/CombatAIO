package scripts.CombatAIO.com.base.api.general.walking;

import java.util.ArrayList;
import java.util.List;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.conditions.RSArea;
import scripts.CombatAIO.com.base.api.general.walking.types.CustomMovement;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class WalkingManager {

	private static final List<CustomMovement> TO_BANK = new ArrayList<CustomMovement>();

	private static final List<CustomMovement> TO_MONSTER = new ArrayList<CustomMovement>();

	public static void addMovement(MovementType type, DFullHolder dfh,
			RSArea area, String name) {
		CustomMovement temp = new CustomMovement(area, dfh, name);
		if (type == MovementType.TO_BANK)
			TO_BANK.add(temp);
		else
			TO_MONSTER.add(temp);
	}

}
