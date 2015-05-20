package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.general.walking.types.Bank;
import scripts.CombatAIO.com.base.api.general.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.general.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.general.walking.types.Teleport;

public class CWalking {

	public static JeweleryTeleport walk(MovementType type) {
		JeweleryTeleport teleported = null;
		RSTile end_tile = (type == MovementType.TO_BANK ? Bank.getNearestBank()
				.getTile() : (RSTile) Dispatcher.get().get(ValueType.HOME_TILE)
				.getValue());
		if (isFasterToTeleport(end_tile))
			teleported = Teleporting.attemptToTeleport(end_tile);
		if (type.equals(MovementType.TO_BANK))
			WebWalking.walkToBank();
		else
			WebWalking.walkTo(end_tile);
		return teleported;
	}

	private static boolean isFasterToTeleport(RSTile end_tile) {
		Teleport tele = Teleport.getTeleportNearestTo(end_tile);
		JeweleryTeleport jewel = Jewelery.getNearestJewleryTeleport(end_tile);
		int distance_tele = tele.getSpellLocationResult().distanceTo(end_tile);
		int distance_jewel = jewel.getTeleportLocation().getTeleportTile()
				.distanceTo(end_tile);
		int distance_walk = Player.getPosition().distanceTo(end_tile);
		return distance_tele < distance_walk || distance_jewel < distance_walk;
	}

}
