package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.general.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.general.walking.types.Teleport;

public class Teleporting {

	public static JeweleryTeleport attemptToTeleport(RSTile pos) {
		Teleport t = Teleport.getTeleportNearestTo(pos);
		JeweleryTeleport jt = Jewelery.getNearestJewleryTeleport(pos);
		if (t == null && jt == null)
			return null;
		if (!t.canTeleport() && !jt.canTeleport())
			return null;
		if (t.getSpellLocationResult().distanceTo(pos) < jt
				.getTeleportLocation().getTeleportTile().distanceTo(pos)
				&& t.canTeleport())
			return null;
		else {
			if (jt.canTeleport()) {
				return jt;
			} else if (t.canTeleport())
				return null;
		}
		return null;
	}

}
