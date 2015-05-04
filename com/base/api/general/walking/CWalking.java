package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class CWalking {

	public static void walk(MovementType type) {
		RSTile end_tile = (type == MovementType.TO_BANK ? getNearestBank()
				: (RSTile) Dispatcher.get().get(ValueType.HOME_TILE).getValue());
		Teleporting.attemptToTeleport(end_tile);
		if (type.equals(MovementType.TO_BANK))
			WebWalking.walkToBank();
		else
			WebWalking.walkTo(end_tile);
		return;

	}

	private static RSTile getNearestBank() {
		// TODO Auto-generated method stub
		return null;
	}
}
