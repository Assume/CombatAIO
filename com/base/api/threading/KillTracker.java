package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api.General;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;

public class KillTracker extends Threadable implements Runnable {

	public KillTracker(CombatThread combat_thread) {
		super(null);
		this.combat_thread = combat_thread;
	}

	private CombatThread combat_thread;
	private int kills;

	@Override
	public void run() {
		while (true) {
			RSNPC target = (RSNPC) Dispatcher.get()
					.get(ValueType.CURRENT_TARGET, null).getValue();
			if (target != null && target.getHealth() == 0
					&& target.isInCombat()) {
				kills++;
				combat_thread.resetTarget();
				General.sleep(1000);
				System.out.println("new kill, total kills: " + kills);
			}
			General.sleep(500);
		}

	}

	public Value<?> getTotalKills() {
		return new IntegerValue(kills);
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
