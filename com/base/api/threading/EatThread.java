package scripts.CombatAIO.com.base.api.threading;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
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

	private static int TEMP_EAT_AT_PERCENT = 50;

	@Override
	public void run() {
		while (true) {
			if ((SKILLS.HITPOINTS.getCurrentLevel() / SKILLS.HITPOINTS
					.getActualLevel()) < TEMP_EAT_AT_PERCENT) {
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_EATING);
				eat();
				Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_EATING);
			}
			General.sleep(250);
		}
	}

	private void eat() {
		RSItem[] food = Inventory.find((String) Dispatcher.get()
				.get(ValueType.FOOD_NAME, null).getValue());
		if (food.length > 0) {
			food[0].click("Eat");
		}

	}

	public Value<?> getFoodName() {
		return new StringValue(this.food_name);
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
