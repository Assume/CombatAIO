package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;

public class KillTracker implements Runnable {

	private int kills;
	private RSNPC target;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	protected void setTarget(RSNPC target) {
		// TODO
		this.target = target;
	}

	public Value getTotalKills() {
		return new IntegerValue(kills);
	}

}
