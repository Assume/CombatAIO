package scripts.CombatAIO.com.base.api.progression.moves;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class CChangeFoodMove extends CProgressionAction {

	private Food food;

	public CChangeFoodMove(Food food) {
		this.food = food;
	}

	@Override
	public void execute() {
		Dispatcher.get().set(ValueType.FOOD, new Value<Food>(food));
	}

}
