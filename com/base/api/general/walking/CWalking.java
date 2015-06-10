package scripts.CombatAIO.com.base.api.general.walking;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.types.Bank;
import scripts.CombatAIO.com.base.api.general.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.general.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.general.walking.types.Teleport;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.main.GenericMethods;

public class CWalking {

	public static JeweleryTeleport walk(MovementType type) {
		JeweleryTeleport teleported = null;
		RSTile end_tile = (type == MovementType.TO_BANK ? Bank.getNearestBank()
				.getTile() : (RSTile) Dispatcher.get().get(ValueType.HOME_TILE)
				.getValue());
		Bank nearest = Bank.getNearestBank();
		GenericMethods.println("Nearest bank: " + nearest);
		if (isFasterToTeleport(end_tile)) {
			GenericMethods.println("It is faster to teleport");
			teleported = Teleporting.attemptToTeleport(end_tile);
		}
		WalkingManager.walk(type, end_tile);
		return teleported;
	}

	private static boolean isFasterToTeleport(RSTile end_tile) {
		Teleport tele = Teleport.getTeleportNearestTo(end_tile);
		GenericMethods.println("Closest teleport: " + tele);
		JeweleryTeleport jewel = Jewelery.getNearestJewleryTeleport(end_tile);
		int distance_tele = tele.getSpellLocationResult().distanceTo(end_tile);
		int distance_jewel = jewel.getTeleportLocation().getTeleportTile()
				.distanceTo(end_tile);
		int distance_walk = Player.getPosition().distanceTo(end_tile);
		return Player.getPosition().getPlane() != 0
				|| distance_tele < distance_walk
				|| distance_jewel < distance_walk;
	}

}
