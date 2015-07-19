package scripts.CombatAIO.com.base.api.tasks.helper;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.tasks.Dispatcher;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;

public class StaticTargetCalculator {

	public static RSNPC[] calculate() {
		RSCharacter[] entities = Combat.getAttackingEntities();
		if (entities.length > 0) {
			if (entities[0] instanceof RSNPC && ((RSNPC) entities[0]).isValid()) {
				if (PathFinding.canReach(entities[0], false)) {
					if (((RSNPC) entities[0]).isValid()) {
						return new RSNPC[] { (RSNPC) entities[0] };
					}
				}
			}
		}
		RSCharacter npc = Player.getRSPlayer().getInteractingCharacter();
		if (npc == null
				|| !(npc instanceof RSNPC)
				|| !isAttackable((RSNPC) npc)
				&& ((Boolean) Dispatcher.get().get(ValueType.IS_RANGING)
						.getValue() || PathFinding.canReach(npc, false))) {
			RSNPC[] mobs = getMonsters(true);
			if (mobs.length > 0)
				return mobs;
			else
				return getMonsters(false);
		}
		return new RSNPC[0];
	}

	public static RSNPC[] getPaintableMonsters() {
		return (RSNPC[]) Dispatcher.get().get(ValueType.POSSIBLE_MONSTERS)
				.getValue();
	}

	private static boolean isBeingSplashed(RSNPC n) {
		return n.getInteractingCharacter() != null;
	}

	private static RSNPC[] getMonsters(boolean reachable) {
		RSNPC[] npcs = filter_one(
				NPCs.findNearest((int[]) Dispatcher.get()
						.get(ValueType.MONSTER_IDS).getValue()), reachable);
		return filter_two(npcs);
	}

	private static RSNPC[] filter_one(RSNPC[] npcs, boolean reachable) {
		List<RSNPC> possible_npcs = new ArrayList<RSNPC>();
		int radius = (Integer) Dispatcher.get().get(ValueType.COMBAT_RADIUS)
				.getValue();
		RSTile home_tile = (RSTile) Dispatcher.get().get(ValueType.HOME_TILE)
				.getValue();
		for (RSNPC x : npcs) {
			if (x.isInteractingWithMe())
				if (PathFinding.canReach(x, false))
					return new RSNPC[] { x };
			if (home_tile.distanceTo(x) < radius) {
				if (!x.isInCombat() && !isBeingSplashed(x)) {
					if ((Boolean) Dispatcher.get().get(ValueType.IS_RANGING)
							.getValue()) {
						possible_npcs.add(x);
						continue;
					}
					if (!reachable
							|| (Player.getPosition().distanceTo(x) <= radius * 2 && PathFinding
									.canReach(x, false)))
						possible_npcs.add(x);
				}
			}
		}
		return NPCs.sortByDistance(Player.getPosition(),
				possible_npcs.toArray(new RSNPC[possible_npcs.size()]));
	}

	private static RSNPC[] filter_two(RSNPC[] npcs) {
		List<RSNPC> list = new ArrayList<RSNPC>();
		for (RSNPC x : npcs) {
			int distance = new DPathNavigator().findPath(x).length;
			if (distance <= 4)
				list.add(x);
		}
		if (list.size() > 1)
			return list.toArray(new RSNPC[list.size()]);
		else
			return npcs;
	}

	private static boolean isAttackable(RSNPC npc) {
		return npc.getCombatLevel() > 0;
	}

	public static boolean verifyTarget(RSNPC current_target) {
		return current_target != null && !current_target.isInCombat()
				&& !isBeingSplashed(current_target);
	}
}
