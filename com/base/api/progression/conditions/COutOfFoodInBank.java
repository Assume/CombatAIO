package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api2007.Banking;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;

public class COutOfFoodInBank extends CProgressionCondition {

	@Override
	protected boolean should_progress() {
		if (Banking.isBankScreenOpen())
			return Banking.find((((Food) Dispatcher.get().get(ValueType.FOOD)
					.getValue()).getId())).length == 0;
		return false;
	}

}
