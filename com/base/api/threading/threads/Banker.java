package scripts.CombatAIO.com.base.api.threading.threads;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;

import scripts.CombatAIO.com.base.api.general.walking.CWalking;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.CustomPaths;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class Banker {

	/*
	 * @Override public void run() { while (Dispatcher.get().isRunning()) { if
	 * (this.shouldBank()) {
	 * Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
	 * 
	 * String[] monster_names = (String[]) Dispatcher.get()
	 * .get(ValueType.MONSTER_NAMES).getValue(); CustomPaths modified_path =
	 * getModifiedPath(monster_names); if (modified_path != null) ;
	 * modified_path.getWebWalkingDeactivationArea (MovementType.TO_BANK);
	 * 
	 * TODO make it so it grabs the first modified area to pass to webwalking
	 * bank(); Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING); }
	 * General.sleep(2000); } }
	 */

	public static void bank() {
		Camera.setCameraRotation(General.random(Camera.getCameraAngle() - 15,
				Camera.getCameraAngle() + 15));
		CWalking.walk(MovementType.TO_BANK);
		openBank();
		handleBankWindow();
		CWalking.walk(MovementType.TO_MONSTER);
	}

	// TODO DEPOSIT ALL EXCEPT WHAT?
	private static void handleBankWindow() {
		Banking.depositAll();
		Banking.withdraw(10, (String) Dispatcher.get().get(ValueType.FOOD_NAME)
				.getValue());
		Banking.close();
	}

	private static void openBank() {
		Banking.openBank();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Banking.isBankScreenOpen();
			}
		}, 3000);
		if (!Banking.isBankScreenOpen()) {
			bank();
			return;
		}
		General.sleep(250, 800);
	}

	private static CustomPaths getModifiedPath(String[] names) {
		if (names == null || names.length == 0)
			return null;
		else
			return CustomPaths.getCustomPath(names[0]);
	}

	public static boolean shouldBank() {
		String name = (String) Dispatcher.get().get(ValueType.FOOD_NAME)
				.getValue();
		return Inventory.isFull()
				|| (name != null && Inventory.find(name).length == 0);
	}

}
