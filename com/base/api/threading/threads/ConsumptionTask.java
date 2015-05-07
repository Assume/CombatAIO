package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.magic.books.NormalSpell;
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

	private String food_name = "Salmon";

	@Override
	public void run() {
		while (true) {
			if (Combat.getHPRatio() < Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_EAT_AT
					.next()) {
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_EATING);
				eat();
				executeBonesToPeaches();
				Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_EAT_AT.reset();
				Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_EATING);
			}
			General.sleep(500);
		}
	}

	private void eat() {
		RSItem[] food = Inventory.find((String) Dispatcher.get()
				.get(ValueType.FOOD_NAME).getValue());
		if (food.length > 0) {
			food[0].click("Eat");
		}

	}

	public boolean isUsingBonesToPeaches() {
		return "peach".equalsIgnoreCase(this.food_name);
	}

	private void executeBonesToPeaches() {
		if (food_name.equalsIgnoreCase("peach")) {
			RSItem[] food = Inventory.find("Peach");
			if (food.length < 3) {
				RSItem[] bones = Inventory.find("Bones");
				if (bones.length > 0) {
					RSItem[] tab = Inventory.find("Bones To Peaches");
					if (tab.length > 0)
						tab[0].click("Break");
					else if (NormalSpell.BONES_TO_PEACHES.canCast())
						NormalSpell.BONES_TO_PEACHES.select();
				}
			}
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
