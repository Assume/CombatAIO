package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.types.Teleport;

public class Teleporting {

	public static boolean attemptToTeleport(RSTile pos) {
		Teleport t = Teleport.getTeleportNearestTo(pos);
		if (t == null)
			return false;
		if (t.canTeleport())
			return t.teleport();
		return false;
	}

}
