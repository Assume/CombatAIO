package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.main.BaseCombat;

public class Dispatcher implements Runnable {

	private static Dispatcher dispatcher;

	public static void create(BaseCombat main_class) {
		dispatcher = new Dispatcher(main_class);
	}

	public static Dispatcher get() {
		return dispatcher;
	}

	private CombatThread combat_thread;
	private LootingThread looting_thread;
	private EatThread eat_thread;
	private BaseCombat main_class;

	private Dispatcher(BaseCombat main_class) {
		this.main_class = main_class;
		this.combat_thread = new CombatThread();
		this.looting_thread = new LootingThread(this.combat_thread);
	}

	public Value<?> get(ValueType type, String... extra_paramaters) {
		switch (type) {
		case CURRENT_TARGET:
			return combat_thread.getCurrentTarget();
		case MINIMUM_LOOT_VALUE:

		case TOTAL_KILLS:
			return combat_thread.getTotalKills();
		case TOTAL_LOOT_VALUE:
			return looting_thread.getTotalLootValue();
		case LOOT_ITEM:
			return looting_thread.getLootItem(extra_paramaters);
		case ITEM_PRICE:
			return looting_thread.getItemPrice(extra_paramaters);
		case AMOUNT_LOOTED_OF_ITEM:
			return looting_thread.getAmountLooted(extra_paramaters);
		case FOOD_NAME:
			return eat_thread.getFoodName();
		case MONSTER_NAMES:
			return this.combat_thread.getMonsterNames();
		}
		return null;

	}

	@SuppressWarnings("deprecation")
	public void pause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type))
				x.suspend();
	}

	@SuppressWarnings("deprecation")
	public void unpause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type))
				x.resume();
	}

	@Override
	public void run() {

		while (this.main_class.isRunning()) {

		}

		/*
		 * dispatch initital needed threads while(BaseCombat.isRunning())
		 * 
		 * check threads for issues and redispatch if needed check for
		 * conditions requiring the pausing of thread - enum system for this
		 */
	}

	public boolean isRunning() {
		return this.main_class.isRunning();
	}

}
