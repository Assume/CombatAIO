package scripts.CombatAIO.com.base.api.threading;

import java.util.List;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.StringValue;

public class EatThread extends Threadable implements Runnable {

	public EatThread() {
		this(null);
	}

	private EatThread(List<PauseType> pause_types) {
		super(pause_types);
		// TODO Auto-generated constructor stub
	}

	private String food_name;

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

	@Override
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
