package scripts.CombatAIO.com.base.api.threading.threads;

import org.tribot.api.General;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;

public class KillTracker extends Threadable implements Runnable {
	// TODO FIGURE OUT
	private static final int WILDERNESS_START_Y_VALUE = 0;

	public KillTracker(CombatTask combat_thread) {
		super(null);
		this.combat_thread = combat_thread;
	}

	private CombatTask combat_thread;
	private int kills;

	@Override
	public void run() {
		while (true) {
			RSTile home_tile = (RSTile) Dispatcher.get()
					.get(ValueType.HOME_TILE).getValue();
			if (home_tile.getY() < WILDERNESS_START_Y_VALUE)
				break;
			RSNPC target = (RSNPC) Dispatcher.get()
					.get(ValueType.CURRENT_TARGET).getValue();
			if (target != null && target.getHealth() == 0
					&& target.isInCombat()) {
				kills++;
				General.sleep(1000);
				combat_thread.resetTarget();
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
