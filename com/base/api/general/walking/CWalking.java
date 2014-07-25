package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.CustomPaths;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class CWalking {

	public static void walk(MovementType type) {
		CustomPaths path = CustomPaths.getCustomPath((String) Dispatcher.get()
				.get(ValueType.FIRST_MONSTER_NAME).get());
		if (path == null) {
			if (type.equals(MovementType.TO_BANK))
				WebWalking.walkToBank();
			else
				WebWalking.walkTo((Positionable) Dispatcher.get()
						.get(ValueType.HOME_TILE).get());
			return;
		}
		while (path.hasIndicesLeft(type)) {
			final RSArea next_deactivation_area = path
					.getWebWalkingDeactivationArea(type);
			WebWalking.walkTo(path.getDestination(type), new Condition() {
				@Override
				public boolean active() {
					if (next_deactivation_area != null)
						return next_deactivation_area.contains(Player
								.getRSPlayer());
					return false;
				}
			}, 1000);
		}

	}
}
