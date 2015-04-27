package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.CustomPaths;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class BankingThread implements Runnable {

	public BankingThread() {

	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning()) {
			if (this.shouldBank()) {
				Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
				String[] monster_names = (String[]) Dispatcher.get()
						.get(ValueType.MONSTER_NAMES).getValue();
				CustomPaths modified_path = getModifiedPath(monster_names);
				if (modified_path != null)
					;
				modified_path.getWebWalkingDeactivationArea(MovementType.TO_BANK);
				// TODO make it so it grabs the first modified area to pass to
				// webwalking
			}
		}
	}

	private CustomPaths getModifiedPath(String[] names) {
		if (names == null || names.length == 0)
			return null;
		else
			return CustomPaths.getCustomPath(names[0]);
	}

	private boolean shouldBank() {
		// TODO
		return false;
	}

}
