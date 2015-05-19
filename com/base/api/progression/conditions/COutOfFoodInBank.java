package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api2007.Banking;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class COutOfFoodInBank extends CProgressionCondition {


	@Override
	public boolean shouldProgress() {
		if (Banking.isBankScreenOpen())
			return Banking.find((String) (Dispatcher.get().get(
					ValueType.FOOD_NAME).getValue())).length == 0;
		return false;
	}

}
