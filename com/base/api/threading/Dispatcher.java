package scripts.CombatAIO.com.base.api.threading;

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

	public Object get(ValueType type) {
		switch (type) {
		case CURRENT_TARGET:
			return combat_thread.getCurrentTarget();
		case MINIMUM_LOOT_VALUE:

		case TOTAL_KILLS:
			return combat_thread.getTotalKills();

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
