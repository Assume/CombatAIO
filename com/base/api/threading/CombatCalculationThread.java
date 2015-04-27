package scripts.CombatAIO.com.base.api.threading;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class CombatCalculationThread implements Runnable {

	private CombatThread combat_thread;

	public CombatCalculationThread(CombatThread combat_thread) {
		this.combat_thread = combat_thread;
	}

	@Override
	public void run() {
		while (true) {
			RSCharacter npc = Player.getRSPlayer().getInteractingCharacter();
			if (npc == null || !(npc instanceof RSNPC)
					|| !isAttackable((RSNPC) npc))
				combat_thread.setMonsters(getMonsters());
			General.sleep(600);
		}

	}

	public void forceCalcuation() {
		combat_thread.setMonsters(getMonsters());
	}

	private RSNPC[] getMonsters() {
		RSNPC[] npcs = filter_one(NPCs.find((String[]) Dispatcher.get()
				.get(ValueType.MONSTER_NAMES, null).getValue()));
		return filter_two(npcs);
	}

	private RSNPC[] filter_one(RSNPC[] npcs) {
		List<RSNPC> possible_npcs = new ArrayList<RSNPC>();
		for (RSNPC x : npcs) {
			if (x.isInteractingWithMe())
				return new RSNPC[] { x };
			if (!x.isInCombat()) {
				if ((Boolean) Dispatcher.get().get(ValueType.IS_RANGING, null)
						.getValue()) {
					possible_npcs.add(x);
					continue;
				}
				if (Player.getPosition().distanceTo(x) <= 12
						&& PathFinding.canReach(x, false))
					possible_npcs.add(x);
			}
		}
		return NPCs.sortByDistance(Player.getPosition(),
				possible_npcs.toArray(new RSNPC[possible_npcs.size()]));
	}

	private RSNPC[] filter_two(RSNPC[] npcs) {
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

	private boolean isAttackable(RSNPC npc) {
		return npc.getCombatLevel() > 0;
	}
}
