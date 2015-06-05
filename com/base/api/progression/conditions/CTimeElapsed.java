package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api.General;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class CTimeElapsed extends CProgressionCondition {

	private long time;

	public CTimeElapsed(int hours) {
		long temp = (long) (hours * 3600);
		this.time = (long) General.randomDouble(temp - (temp * .05), temp
				+ (temp * .05));
	}

	@Override
	protected boolean should_progress() {
		return (Long) Dispatcher.get().get(ValueType.RUN_TIME).getValue() >= time;
	}

}
