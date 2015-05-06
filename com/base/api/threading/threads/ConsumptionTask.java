package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class ConsumptionTask extends Threadable implements Runnable {

	public ConsumptionTask() {
		this(null);
	}

	private ConsumptionTask(List<PauseType> pause_types) {
		super(pause_types);
	}

	private String food_name = null;

	private static int TEMP_EAT_AT_PERCENT = 50;

	@Override
	public void run() {
		while (true) {
			if (getHPPercent() < TEMP_EAT_AT_PERCENT) {
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_EATING);
				eat();
				Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_EATING);
			}
			General.sleep(500);
		}
	}

	private double getHPPercent() {
		return (SKILLS.HITPOINTS.getCurrentLevel() / SKILLS.HITPOINTS
				.getActualLevel()) * 100;
	}

	private void eat() {
		RSItem[] food = Inventory.find((String) Dispatcher.get()
				.get(ValueType.FOOD_NAME).getValue());
		if (food.length > 0) {
			food[0].click("Eat");
		}

	}

	public Value<String> getFoodName() {
		return new Value<String>(this.food_name);
	}

	public void setFoodName(String name) {
		this.food_name = name;
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
