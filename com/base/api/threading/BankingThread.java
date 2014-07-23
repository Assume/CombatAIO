package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.MonsterRequiringCustomPathing;

public class BankingThread implements Runnable {

	public BankingThread() {

	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning()) {
			if (this.shouldBank()) {
				Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
				String[] monster_names = (String[]) Dispatcher.get()
						.get(ValueType.MONSTER_NAMES).get();
				MonsterRequiringCustomPathing modified_path = getModifiedPath(monster_names);
				if (modified_path != null)
					;
				// TODO make it so it grabs the first modified area to pass to
				// webwalking
			}
		}
	}

	private MonsterRequiringCustomPathing getModifiedPath(String[] names) {
		if (names == null || names.length == 0)
			return null;
		else
			return MonsterRequiringCustomPathing.getCustomPath(names[0]);
	}

	private boolean shouldBank() {
		// TODO
		return false;
	}

}
