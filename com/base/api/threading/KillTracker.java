package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.IntegerValue;

public class KillTracker implements Runnable {

	private int kills;
	private RSNPC target;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	protected void setTarget() {
		this.target = target;
	}

	public Object getTotalKills() {
		return new IntegerValue(kills);
	}

}
