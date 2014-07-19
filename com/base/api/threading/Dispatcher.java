package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class Dispatcher implements Runnable {

	private static Dispatcher dispatcher;

	public static Dispatcher get() {
		return dispatcher == null ? new Dispatcher() : dispatcher;
	}

	private CombatThread combat_thread;

	public Dispatcher() {
		this.combat_thread = new CombatThread();
	}

	public Object get(ValueType type) {
		switch (type) {
		case CURRENT_TARGET:
			return combat_thread.get();
		}
		return null;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
