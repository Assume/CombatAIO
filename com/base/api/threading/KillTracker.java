package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;

public class KillTracker implements Runnable, Pauseable {

	private int kills;
	private RSNPC target;

	@Override
	public void run() {
		/*
		 * while(true) RSNPC temp = Disptacher.get(ValueType.CURRENT_TARGET);
		 * if(temp != target) // checking same instance \\ target = temp track
		 * temp
		 */

	}

	public Value<?> getTotalKills() {
		return new IntegerValue(kills);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}
