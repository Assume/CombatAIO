package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api2007.Banking;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class COutOfFoodInBank extends CProgressionCondition {


	private static final long serialVersionUID = 1348927205943411999L;

	@Override
	protected boolean should_progress() {
		if (Banking.isBankScreenOpen())
			return Banking.find((((Food) Dispatcher.get().get(ValueType.FOOD)
					.getValue()).getId())).length == 0;
		return false;
	}

	@Override
	public String toString() {
		return "out of food in bank";
	}

}
