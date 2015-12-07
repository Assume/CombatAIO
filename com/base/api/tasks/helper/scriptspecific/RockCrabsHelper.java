package scripts.CombatAIO.com.base.api.tasks.helper.scriptspecific;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.constants.MonsterArea;
import scripts.CombatAIO.com.base.api.types.constants.MonsterIDs;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class RockCrabsHelper {

	private static final RSTile RELLEKKA_EAST_CAVE_TILE = new RSTile(2730, 3712);

	private static final RSTile RELLEKKA_WEST_RESET_TILE = new RSTile(2671,
			3679);

	private static final int RELLEKKA_EAST_CAVE_OUTSIDE_ID = 5008;
	private static final int RELLEKKA_EAST_CAVE_INSIDE_ID = 5014;

	private static int times_failed_wakeup_crab;

	public static void wakeUpCrabs() {
		RSNPC[] idle_crabs = NPCs.findNearest(Filters.NPCs.inArea(
				MonsterArea.getArea()).combine(
				Filters.NPCs.idEquals(MonsterIDs.ROCK_CRAB_ASLEEP_IDS), true));
		if (idle_crabs.length == 0)
			return;
		if (times_failed_wakeup_crab >= 5) {
			resetCrabs();
			times_failed_wakeup_crab = 0;
		}
		for (RSNPC crab : idle_crabs) {
			Walking.walkTo(crab.getPosition());
			while (Player.isMoving() && !Player.getRSPlayer().isInCombat())
				General.sleep(200);
			if (Player.getRSPlayer().isInCombat())
				return;
			if (isCrabIdle(crab))
				times_failed_wakeup_crab++;
			else {
				times_failed_wakeup_crab = 0;
				return;
			}
		}
	}

	private static void resetCrabs() {
		switch (Dispatcher.get().getPreset()) {
		case RELLEKKA_WEST_ROCK_CRABS:
			new DPathNavigator().traverse(RELLEKKA_WEST_RESET_TILE);
			new DPathNavigator().traverse((RSTile) Dispatcher.get()
					.get(ValueType.HOME_TILE).getValue());
			return;
		case RELLEKKA_EAST_ROCK_CRABS:
			resetEastCrabs();
			return;
		default:
			break;
		}

	}

	private static void resetEastCrabs() {
		new DPathNavigator().traverse(RELLEKKA_EAST_CAVE_TILE);
		while (Player.isMoving())
			General.sleep(100);
		RSObject[] obs = Objects.find(100, RELLEKKA_EAST_CAVE_OUTSIDE_ID);
		if (obs.length == 0)
			return;
		obs[0].click("Enter Tunnel");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Objects.find(100, RELLEKKA_EAST_CAVE_OUTSIDE_ID).length == 0;
			}
		}, 6000);
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Objects.find(100, RELLEKKA_EAST_CAVE_INSIDE_ID).length > 0;
			}
		}, 3000);
		obs = Objects.find(100, RELLEKKA_EAST_CAVE_INSIDE_ID);
		if (obs.length == 0)
			return;
		obs[0].click("Enter Tunnel");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Objects.find(100, RELLEKKA_EAST_CAVE_OUTSIDE_ID).length > 0;
			}
		}, 6000);
		new DPathNavigator().traverse((RSTile) Dispatcher.get()
				.get(ValueType.HOME_TILE).getValue());
	}

	private static boolean isCrabIdle(RSNPC crab) {
		for (int x : MonsterIDs.ROCK_CRAB_ASLEEP_IDS)
			if (crab.getID() == x)
				return true;
		return false;
	}

}
