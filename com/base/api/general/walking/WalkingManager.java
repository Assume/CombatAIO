package scripts.CombatAIO.com.base.api.general.walking;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

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

	public static boolean shouldStop(MovementType type) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getMovementType() == type
					&& x.getActivationArea().contains(Player.getPosition()))
				return true;
		return false;
	}

	public static void walk(MovementType type, RSTile end_tile) {
		while (Player.getPosition().distanceTo(end_tile) >= 10)
			WebWalking.walkTo(end_tile, getStoppingCondition(type), 1000);
	}

	private static final Condition getStoppingCondition(final MovementType type) {
		final Condition STOPPING_CONDITION = new Condition() {
			@Override
			public boolean active() {
				return WalkingManager.shouldStop(type);
			}
		};
		return STOPPING_CONDITION;
	}
}
