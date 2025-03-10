package scripts.CombatAIO.com.base.api.tasks.threads;

import org.tribot.api.General;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Threadable;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.api.scriptapi.antiban.AntiBan;

public class KillTracker extends Threadable {

	public KillTracker(CombatTask combat_thread) {
		super(null);
		this.combat_thread = combat_thread;
	}

	private CombatTask combat_thread;
	private int kills;

	@Override
	public void run() {
		while (true) {
			RSNPC target = (RSNPC) Dispatcher.get()
					.get(ValueType.CURRENT_TARGET).getValue();
			if (target != null
					&& target.getHealth() == 0
					&& target.isInCombat()
					&& (target.isInteractingWithMe() || (Boolean) Dispatcher
							.get().get(ValueType.ATTACK_MONSTERS_IN_COMBAT)
							.getValue())) {
				kills++;
				General.sleep(1000);
				combat_thread.resetTarget();
				AntiBan.setLastUnderAttackTime(System.currentTimeMillis());
			}
			General.sleep(500);
		}

	}

	public Value<Integer> getTotalKills() {
		return new Value<Integer>(kills);
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
