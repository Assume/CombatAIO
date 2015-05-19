package scripts.CombatAIO.com.base.api.progression.moves;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;

public class ChangeFoodMove extends CProgressionAction {

	private Food food;

	public ChangeFoodMove(Food food) {
		this.food = food;
	}

	@Override
	public void execute() {
		Dispatcher.get().set(ValueType.FOOD, new Value<Food>(food));
	}

}
