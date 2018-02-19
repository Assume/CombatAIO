package scripts.CombatAIO.com.base.api.types.constants;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.main.Dispatcher;

public class MonsterArea {

	public static final RSArea RELLEKKA_ROCK_CRABS_WEST = new RSArea(
			new RSTile[] { new RSTile(2687, 3744, 0), new RSTile(2689, 3713, 0), new RSTile(2653, 3714, 0),
					new RSTile(2647, 3742, 0), new RSTile(2667, 3742, 0) });

	public static final RSArea RELLEKKA_ROCK_CRABS_EAST = new RSArea(new RSTile[] { new RSTile(2691, 3738, 0),
			new RSTile(2693, 3712, 0), new RSTile(2728, 3711, 0), new RSTile(2725, 3740, 0) });

	public static final RSArea getArea() {
		switch (Dispatcher.get().getPreset()) {
		case RELLEKKA_WEST_ROCK_CRABS:
			return RELLEKKA_ROCK_CRABS_WEST;
		case RELLEKKA_EAST_ROCK_CRABS:
			return RELLEKKA_ROCK_CRABS_EAST;
		case Automatic:
			break;
		default:
			break;
		}
		return null;
	}

}
