package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.general.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.general.walking.types.Teleport;
import scripts.CombatAIO.com.base.main.utils.Logger;

public class Teleporting {

	public static JeweleryTeleport attemptToTeleport(RSTile pos) {
		Teleport t = Teleport.getTeleportNearestTo(pos);
		JeweleryTeleport jt = Jewelery.getNearestJewleryTeleport(pos);
		if (t == null && jt == null)
			return null;
		if (!t.canTeleport() && !jt.canTeleport()) {
			Logger.getLogger().print(Logger.SCRIPTER_ONLY, "Cannot teleport");
			return null;
		}
		if (t.getSpellLocationResult().distanceTo(pos) < jt
				.getTeleportLocation().getTeleportTile().distanceTo(pos)
				&& t.canTeleport()) {
			Logger.getLogger().print(Logger.SCRIPTER_ONLY, "Attemtping to teleport");
			t.teleport();
			return null;
		} else {
			Logger.getLogger().print(Logger.SCRIPTER_ONLY,
					"Cannot use normal teleport: " + t.canTeleport());
			if (jt.canTeleport()) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"Attemtping to teleport, jewelery");
				jt.operate();
				return jt;
			} else if (t.canTeleport()) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"Attemtping to teleport");
				t.teleport();
				return null;
			}
		}
		return null;
	}

}
