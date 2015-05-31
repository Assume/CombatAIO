package scripts.CombatAIO.com.base.api.general.walking;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.types.RSArea;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.general.walking.types.CustomMovement;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class WalkingManager {

	private static final List<CustomMovement> MOVEMENTS = new ArrayList<CustomMovement>();

	public static void addMovement(MovementType type, DFullHolder dfh,
			RSArea area, String name) {
		CustomMovement temp = new CustomMovement(area, dfh, name, type);
		MOVEMENTS.add(temp);
	}
}
