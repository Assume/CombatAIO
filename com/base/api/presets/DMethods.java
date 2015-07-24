package scripts.CombatAIO.com.base.api.presets;

import org.tribot.api.Clicking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.util.DPathNavigator;

public class DMethods {

	public static void clickObject(int id, String action) {
		RSObject[] objects = Objects.findNearest(99999, id);
		if (objects.length == 0)
			return;
		if (!objects[0].isOnScreen()) {
			new DPathNavigator().traverse((objects[0].getPosition()));
			Camera.turnToTile(objects[0]);
		}
		Clicking.click(action, objects[0]);
	}

}
