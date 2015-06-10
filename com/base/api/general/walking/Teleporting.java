package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.general.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.general.walking.types.Teleport;
import scripts.CombatAIO.com.base.main.GenericMethods;

public class Teleporting {

	public static JeweleryTeleport attemptToTeleport(RSTile pos) {
		Teleport t = Teleport.getTeleportNearestTo(pos);
		JeweleryTeleport jt = Jewelery.getNearestJewleryTeleport(pos);
		if (t == null && jt == null)
			return null;
		if (!t.canTeleport() && !jt.canTeleport()) {
			GenericMethods.println("Cannot teleport");
			return null;
		}
		if (t.getSpellLocationResult().distanceTo(pos) < jt
				.getTeleportLocation().getTeleportTile().distanceTo(pos)
				&& t.canTeleport()) {
			GenericMethods.println("Attemtping to teleport");
			t.teleport();
			return null;
		} else {
			GenericMethods.println("Cannot use normal teleport: "
					+ t.canTeleport());
			if (jt.canTeleport()) {
				GenericMethods.println("Attemtping to teleport, jewelery");
				jt.operate();
				return jt;
			} else if (t.canTeleport()) {
				GenericMethods.println("Attemtping to teleport");
				t.teleport();
				return null;
			}
		}
		return null;
	}

}
