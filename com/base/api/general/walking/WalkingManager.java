package scripts.CombatAIO.com.base.api.general.walking;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.general.walking.types.CustomMovement;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class WalkingManager {

	private static List<CustomMovement> MOVEMENTS = new ArrayList<CustomMovement>();

	public static void addMovement(MovementType type, DFullHolder dfh,
			RSTile area, String name, String rad) {
		CustomMovement ya = getMovementForName(name);
		if (ya != null) {
			ya.setMovementType(type);
			ya.setFullHolder(dfh);
			ya.setCenterTile(area);
			ya.setRadius(rad);
		} else {
			CustomMovement temp = new CustomMovement(area, dfh, name, type, rad);
			MOVEMENTS.add(temp);
		}
	}

	private static void removeMovementForName(String name) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getName().equalsIgnoreCase(name))
				MOVEMENTS.remove(x);

	}

	public static boolean shouldStop(MovementType type) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getMovementType() == type
					&& x.getActivationArea().contains(Player.getPosition()))
				return true;
		return false;
	}

	public static void walk(MovementType type, RSTile end_tile) {
		while (Player.getPosition().distanceTo(end_tile) >= 10) {
			WebWalking.walkTo(end_tile, getStoppingCondition(type), 1000);
			CustomMovement execute = getMovementToExecute(type);
			if (execute == null)
				return;
			execute.execute();
			General.sleep(500);
		}

	}

	private static CustomMovement getMovementToExecute(MovementType type) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getMovementType() == type
					&& x.getActivationArea().contains(Player.getPosition()))
				return x;
		return null;
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

	public static final List<CustomMovement> getMovements() {
		return MOVEMENTS;
	}

	public static final void setMovements(List<CustomMovement> nmovements) {
		MOVEMENTS = nmovements;
	}

	public static String[] getAllNames() {
		List<String> temp = new ArrayList<String>();
		for (CustomMovement x : MOVEMENTS)
			temp.add(x.getName());
		return temp.toArray(new String[temp.size()]);
	}

	public static CustomMovement getMovementForName(String name) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getName().equalsIgnoreCase(name))
				return x;
		return null;
	}

}
