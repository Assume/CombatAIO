package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.Dispatchable;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.StringValue;

public class EatThread implements Runnable, Pauseable, Dispatchable {

	private String food_name;

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		/*
		 * while(true) if need to eat pause banking, combat, looting after
		 * finished reenable combat, banking, and looting
		 */
	}

	public Value<?> getFoodName() {
		return new StringValue(this.food_name);
	}

}
