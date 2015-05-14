package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api.General;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.progression.CProgressionMove;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class CTimeElapsed extends CProgressionMove {

	private long time;

	public CTimeElapsed(CProgressionAction action, int hours) {
		super(action);
		long temp = (long) (hours * 3600);
		this.time = (long) General.randomDouble(temp - (temp * .05), temp
				+ (temp * .05));
	}

	@Override
	public boolean shouldProgress() {
		return (Long) Dispatcher.get().get(ValueType.RUN_TIME).getValue() >= time;
	}

}
