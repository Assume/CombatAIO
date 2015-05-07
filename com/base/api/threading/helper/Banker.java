package scripts.CombatAIO.com.base.api.threading.helper;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WorldHopper;

import scripts.CombatAIO.com.base.api.general.walking.CWalking;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
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

	public static void bank(boolean world_hop) {
		Camera.setCameraRotation(General.random(Camera.getCameraAngle() - 15,
				Camera.getCameraAngle() + 15));
		CWalking.walk(MovementType.TO_BANK);
		openBank(world_hop);
		handleBankWindow(world_hop);
		if (world_hop)
			WorldHopper.changeWorld(WorldHopper.getRandomWorld(true));
		CWalking.walk(MovementType.TO_MONSTER);
	}

	// TODO DEPOSIT ALL EXCEPT WHAT?
	private static void handleBankWindow(boolean world_hop) {
		Banking.depositAll();
		Banking.withdraw(25, (String) Dispatcher.get().get(ValueType.FOOD_NAME)
				.getValue());
		Banking.close();
	}

	private static void openBank(boolean world_hop) {
		Banking.openBank();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Banking.isBankScreenOpen();
			}
		}, 3000);
		if (!Banking.isBankScreenOpen()) {
			bank(world_hop);
			return;
		}
		General.sleep(250, 800);
	}

	public static boolean shouldBank() {
		String name = (String) Dispatcher.get().get(ValueType.FOOD_NAME)
				.getValue();
		int food_length = Inventory.find(name).length;
		if ((Boolean) Dispatcher.get().get(ValueType.EAT_FOR_SPACE).getValue()
				&& food_length > 0)
			return false;
		return Inventory.isFull()
				|| (name != null && Inventory.find(name).length == 0);
	}
}
