package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api.General;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class CTimeElapsed extends CProgressionCondition {

	private long time_in_milliseconds;

	public CTimeElapsed(int minutes) {
		long temp = (long) (minutes * 60000);
		this.time_in_milliseconds = (long) General.randomDouble(temp - (temp * .05), temp
				+ (temp * .05));
	}

	@Override
	protected boolean should_progress() {
		return (Long) Dispatcher.get().get(ValueType.RUN_TIME).getValue() >= time_in_milliseconds;
	}

}
