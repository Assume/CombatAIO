package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class Dispatcher implements Runnable {

	private static Dispatcher dispatcher;

	public static Dispatcher get() {
		return dispatcher == null ? new Dispatcher() : dispatcher;
	}

	private CombatThread combat_thread;
	private LootingThread looting_thread;

	private Dispatcher() {
		this.combat_thread = new CombatThread();
		this.looting_thread = new LootingThread();
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
		}
		return null;

	}

	@Override
	public void run() {
		// dispatch initital needed threads
		// while(MainScriptClassHere.isRunning())
		/*
		 * check threads for issues and redispatch if needed
		 */
	}
}
