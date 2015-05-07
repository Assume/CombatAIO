package scripts.CombatAIO.com.base.api.threading.helper;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.threads.CombatTask;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class StaticTargetCalculator {

	public static void set(CombatTask combat_thread) {
		RSCharacter[] entities = Combat.getAttackingEntities();
		if (entities.length > 0) {
			if (entities[0] instanceof RSNPC) {
				combat_thread.setMonsters(new RSNPC[] { (RSNPC) entities[0] });
				return;
			}
		}
		RSCharacter npc = Combat.getTargetEntity();
		if (npc == null || !(npc instanceof RSNPC)
				|| !isAttackable((RSNPC) npc))
			combat_thread.setMonsters(getMonsters());
	}

	public static RSNPC[] getPaintableMonsters() {
		return getMonsters();
	}

	private static RSNPC[] getMonsters() {
		RSNPC[] npcs = filter_one(NPCs.find((String[]) Dispatcher.get()
				.get(ValueType.MONSTER_NAMES).getValue()));
		return filter_two(npcs);
	}

	private static RSNPC[] filter_one(RSNPC[] npcs) {
		List<RSNPC> possible_npcs = new ArrayList<RSNPC>();
		for (RSNPC x : npcs) {
			if (x.isInteractingWithMe())
				return new RSNPC[] { x };
			if (!x.isInCombat()) {
				if ((Boolean) Dispatcher.get().get(ValueType.IS_RANGING)
						.getValue()) {
					possible_npcs.add(x);
					continue;
				}
				DPathNavigator test = new DPathNavigator();
				test.overrideDoorCache(true, null);
				if (Player.getPosition().distanceTo(x) <= 12
						&& test.findPath(x.getPosition()).length != 0)
					possible_npcs.add(x);
			}
		}
		return NPCs.sortByDistance(Player.getPosition(),
				possible_npcs.toArray(new RSNPC[possible_npcs.size()]));
	}

	private static RSNPC[] filter_two(RSNPC[] npcs) {
		List<RSNPC> list = new ArrayList<RSNPC>();
		for (RSNPC x : npcs) {
			int distance = Player.getPosition().distanceTo(x);
			if (distance <= 3)
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
}
