package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.WebWalking;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class CWalking {

	public static void walk(MovementType type) {
		if (type.equals(MovementType.TO_BANK))
			WebWalking.walkToBank();
		else
			WebWalking.walkTo((Positionable) Dispatcher.get()
					.get(ValueType.HOME_TILE).getValue());
		return;

	}
}
