package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.magic.books.NormalSpell;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Potions;
import scripts.CombatAIO.com.base.main.GenericMethods;

public class ConsumptionTask extends Threadable implements Runnable {

	private int vial_id = 229;

	public ConsumptionTask() {
		this(null);
		super.setName("CONSUMPTION_THREAD");
	}

	private ConsumptionTask(List<PauseType> pause_types) {
		super(pause_types);
	}

	private Food food = Food.None;

	@Override
	public void run() {
		while (true) {
			if (Combat.getHPRatio() < Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_EAT_AT
					.next() && food != Food.None) {
				GenericMethods
						.println("CONSUMPTION_THREAD IS CALLING PAUSE ON EAT");
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_EATING);
				eat();
				executeBonesToPeaches();
				Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_EAT_AT.reset();
				GenericMethods
						.println("CONSUMPTION_THREAD IS CALLING UNPAUSE ON EAT");
				Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_EATING);
			}
			if (!Dispatcher.get().isLiteMode()
					&& Potions.getPotionsRequired().length > 0) {
				GenericMethods
						.println("CONSUMPTION_THREAD IS CALLING PAUSE ON POTIONS");
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_EATING);
				drink();
				GenericMethods
						.println("CONSUMPTION_THREAD IS CALLING UNPAUSE ON POTION");
				Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_EATING);
			}
			General.sleep(500);
		}
	}

	private void eat() {
		RSItem[] food = Inventory.find(((Food) Dispatcher.get()
				.get(ValueType.FOOD).getValue()).getId());
		if (food.length > 0) {
			food[0].click("Eat");
			Dispatcher.get().attackTarget();
		}
	}

	private void drink() {
		Potions[] potions = Potions.getPotionsRequired();
		for (Potions x : potions) {
			RSItem[] pot = Inventory.find(x.getPotionsIDs());
			if (pot.length > 0) {
				pot[0].click("Drink");
				General.sleep(300, 500);
			}
		}
	}

	public boolean isUsingBonesToPeaches() {
		return this.food == Food.BonesToPeaches;
	}

	private void executeBonesToPeaches() {
		if (food == Food.BonesToPeaches) {
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

	public Value<Food> getFood() {
		return new Value<Food>(food);
	}

	public void setFood(Food food) {
		this.food = food;
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		return false;
	}

}
