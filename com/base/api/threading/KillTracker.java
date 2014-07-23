package scripts.CombatAIO.com.base.api.threading;

import java.util.List;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;

public class KillTracker extends Threadable implements Runnable {

	public KillTracker() {
		this(null);
	}

	private KillTracker(List<PauseType> pause_types) {
		super(pause_types);
	}

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
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
