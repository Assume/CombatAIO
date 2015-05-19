package scripts.CombatAIO.com.base.api.progression.moves;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class ChangeFoodMove extends CProgressionAction {

	private String food_name;

	public ChangeFoodMove(String name) {
		this.food_name = name;
	}

	@Override
	public void execute() {
		Dispatcher.get().set(ValueType.FOOD_NAME, new Value<String>(food_name));
	}

}
