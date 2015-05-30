package scripts.CombatAIO.com.base.api.threading.threads;

import org.tribot.api.General;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class KillTracker extends Threadable implements Runnable {
	// TODO FIGURE OUT

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
			if (target != null && target.getHealth() == 0
					&& target.isInCombat() && target.isInteractingWithMe()) {
				kills++;
				General.sleep(1000);
				combat_thread.resetTarget();
				System.out.println("new kill, total kills: " + kills);

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
