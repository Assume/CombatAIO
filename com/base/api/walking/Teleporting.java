package scripts.CombatAIO.com.base.api.walking;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Combat;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.walking.types.Teleport;
import scripts.CombatAIO.com.base.main.utils.Logger;

public class Teleporting {

	public static JeweleryTeleport attemptToTeleport(RSTile pos) {
		if (Combat.getWildernessLevel() > 20)
			WebWalking.walkTo(pos, new Condition() {
				@Override
				public boolean active() {
					return Combat.getWildernessLevel() <= 20;
				}
			}, 1000);
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
			Logger.getLogger().print(Logger.SCRIPTER_ONLY,
					"Attemtping to teleport");
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
