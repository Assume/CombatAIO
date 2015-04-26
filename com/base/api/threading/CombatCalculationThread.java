package scripts.CombatAIO.com.base.api.threading;

import java.util.ArrayList;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

public class CombatCalculationThread implements Runnable {

	private CombatThread combat_thread;

	public CombatCalculationThread(CombatThread combat_thread) {
		this.combat_thread = combat_thread;
	}

	@Override
	public void run() {
		if (agressiveNPC().length > 0) {
			this.combat_thread.setMonsters(closestNPC(combatFilter(rangeFilter())));
		} else {
			this.combat_thread.setMonsters(closestNPC(agressiveNPC()));
		}
	}

	private void setMonster() {
		//TODO?
	}

	private RSNPC[] closestNPC(RSNPC[] a) {
		//TODO SORT
		return a;
	}

	private ArrayList<RSNPC> rangeFilter() {
		ArrayList<RSNPC> ourNPCs = new ArrayList<RSNPC>();
		for (RSNPC n : NPCs.find((String[]) combat_thread.getMonsterNames().get())) {
			RSTile loc = n.getPosition();
			if (loc.distanceTo((Positionable) combat_thread.getHomeTile().get()) < (Integer) combat_thread
					.getCombatDistance().get()) {
				if (!(Boolean) combat_thread.isRanging().get()
						&& PathFinding.canReach((Positionable) loc, false)) {
					ourNPCs.add(n);
				} else if (!(Boolean) combat_thread.isRanging().get()) {
					ourNPCs.add(n);
				}
			}
		}
		return ourNPCs;
	}

	private RSNPC[] combatFilter(ArrayList<RSNPC> npcs) {
		ArrayList<RSNPC> ourNPCs = new ArrayList<RSNPC>();
		for (RSNPC n : npcs) {
			if (!n.isInCombat())
				ourNPCs.add(n);

		}
		RSNPC[] NPCs = new RSNPC[ourNPCs.size()];
		NPCs = ourNPCs.toArray(NPCs);
		return NPCs;
	}

	private RSNPC[] agressiveNPC() {
		ArrayList<RSNPC> ourNPCs = new ArrayList<RSNPC>();
		for (RSNPC n : NPCs.getAll()) {
			if ((Boolean) combat_thread.isRanging().get()) {
				if (n.isInteractingWithMe()
						&& Player.getRSPlayer().getInteractingCharacter() == null
						&& isAttackable(n)) {
					ourNPCs.add(n);
				}
			} else {
				if (n.isInteractingWithMe()
						&& Player.getRSPlayer().getInteractingCharacter() == null
						&& PathFinding.canReach((Positionable) n.getPosition(),
								false) && isAttackable(n)) {
					ourNPCs.add(n);
				}
			}
		}
		RSNPC[] NPCs = new RSNPC[ourNPCs.size()];
		NPCs = ourNPCs.toArray(NPCs);
		return NPCs;
	}

	private boolean isAttackable(RSNPC npc) {
		return (npc.getCombatLevel() > 0);
	}
}
