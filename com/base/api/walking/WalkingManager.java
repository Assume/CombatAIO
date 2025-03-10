package scripts.CombatAIO.com.base.api.walking;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.walking.custom.types.CustomMovement;
import scripts.CombatAIO.com.base.api.walking.custom.types.DFullHolder;
import scripts.CombatAIO.com.base.api.walking.types.Bank;
import scripts.CombatAIO.com.base.api.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.walking.types.Teleport;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.api.scriptapi.logging.Logger;
import scripts.webwalker_logic.WebWalker;
import scripts.webwalker_logic.local.walker_engine.WalkingCondition;
import scripts.webwalker_logic.teleport_logic.TeleportManager;

public class WalkingManager {

	private static List<CustomMovement> MOVEMENTS = new ArrayList<CustomMovement>();

	public static void addMovement(MovementType type, DFullHolder dfh, RSTile area, String name, int rad) {
		CustomMovement cmovement = getMovementForName(name);
		if (cmovement != null) {
			cmovement.setMovementType(type);
			cmovement.setFullHolder(dfh);
			cmovement.setCenterTile(area);
			cmovement.setRadius(rad);
		} else {
			CustomMovement temp = new CustomMovement(area, dfh, name, type, rad);
			MOVEMENTS.add(temp);
		}
	}

	public static JeweleryTeleport walk(MovementType type) {
		JeweleryTeleport teleported = null;
		/*
		 * RSTile end_tile = (type == MovementType.TO_BANK ? Bank.getNearestBank()
		 * .getTile() : (RSTile) Dispatcher.get().get(ValueType.HOME_TILE) .getValue());
		 * Bank nearest = Bank.getNearestBank();
		 * Logger.getLogger().print(Logger.SCRIPTER_ONLY, "Nearest bank: " + nearest);
		 * if (type == MovementType.TO_BANK && isFasterToTeleport(end_tile) &&
		 * Player.getPosition().distanceTo(end_tile) > 100) {
		 * Logger.getLogger().print(Logger.SCRIPTER_ONLY, "It is faster to teleport");
		 * teleported = Teleporting.attemptToTeleport(end_tile); }
		 */
		if (type == MovementType.TO_BANK)
			WebWalker.walkToBank();
		else
			WebWalker.walkTo((RSTile) Dispatcher.get().get(ValueType.HOME_TILE).getValue());

		if (TeleportManager.previousAction == null)
			return null;
		switch (TeleportManager.previousAction.getTeleportMethod()) {
		case GAMES_NECKLACE:
			return new JeweleryTeleport(Jewelery.GAMES_NECKLACE, null);
		case DUELING_RING:
			return new JeweleryTeleport(Jewelery.RING_OF_DUELING, null);
		case GLORY:
			return new JeweleryTeleport(Jewelery.GLORY, null);
		default:
			break;
		}
		return teleported;
	}

	private static boolean isFasterToTeleport(RSTile end_tile) {
		Teleport tele = Teleport.getTeleportNearestTo(end_tile);
		Logger.getLogger().print(Logger.SCRIPTER_ONLY, "Closest teleport: " + tele);
		JeweleryTeleport jewel = Jewelery.getNearestJewleryTeleport(end_tile);
		if (tele == null && jewel == null)
			return false;
		int distance_tele = tele.getSpellLocationResult().distanceTo(end_tile);
		int distance_jewel = jewel.getTeleportLocation().getTeleportTile().distanceTo(end_tile);
		int distance_walk = Player.getPosition().distanceTo(end_tile);
		return Player.getPosition().getPlane() != 0 || distance_tele < distance_walk || distance_jewel < distance_walk;
	}

	private static void removeMovementForName(String name) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getName().equalsIgnoreCase(name))
				MOVEMENTS.remove(x);

	}

	public static boolean shouldStop(MovementType type) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getMovementType() == type && x.getActivationArea().contains(Player.getPosition()))
				return true;
		return false;
	}

	public static void walk(MovementType type, RSTile end_tile) {
		while (Player.getPosition().distanceTo(end_tile) >= 10) {
			WebWalker.walkTo(end_tile, getStoppingCondition(type));
			CustomMovement execute = getMovementToExecute(type);
			if (execute == null)
				return;
			execute.execute();
			end_tile = (type == MovementType.TO_BANK ? Bank.getNearestBank().getTile()
					: (RSTile) Dispatcher.get().get(ValueType.HOME_TILE).getValue());
			General.sleep(500);
		}

	}

	private static CustomMovement getMovementToExecute(MovementType type) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getMovementType() == type && x.getActivationArea().contains(Player.getPosition()))
				return x;
		return null;
	}

	private static final WalkingCondition getStoppingCondition(final MovementType type) {
		final WalkingCondition STOPPING_CONDITION = new WalkingCondition() {
			@Override
			public State action() {
				if (WalkingManager.shouldStop(type))
					return State.EXIT_OUT_WALKER_SUCCESS;
				return State.CONTINUE_WALKER;
			}
		};
		return STOPPING_CONDITION;
	}

	public static final List<CustomMovement> getMovements() {
		return MOVEMENTS;
	}

	public static final void setMovements(List<CustomMovement> nmovements) {
		MOVEMENTS = nmovements;
	}

	public static String[] getAllNames() {
		List<String> temp = new ArrayList<String>();
		for (CustomMovement x : MOVEMENTS)
			temp.add(x.getName());
		return temp.toArray(new String[temp.size()]);
	}

	public static CustomMovement getMovementForName(String name) {
		for (CustomMovement x : MOVEMENTS)
			if (x.getName().equalsIgnoreCase(name))
				return x;
		return null;
	}

}
