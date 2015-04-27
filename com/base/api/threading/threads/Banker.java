package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.CustomPaths;

public class Banker extends Threadable {

	public Banker() {
		this(null);
	}

	private Banker(List<PauseType> pause_types) {
		super(pause_types);
	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning()) {
			if (this.shouldBank()) {
				Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
				/*
				 * String[] monster_names = (String[]) Dispatcher.get()
				 * .get(ValueType.MONSTER_NAMES).getValue(); CustomPaths
				 * modified_path = getModifiedPath(monster_names); if
				 * (modified_path != null) ;
				 * modified_path.getWebWalkingDeactivationArea
				 * (MovementType.TO_BANK);
				 */
				// TODO make it so it grabs the first modified area to pass to
				// webwalking
				bank();
				Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING);
			}
			General.sleep(2000);
		}
	}

	private void bank() {
		Camera.setCameraRotation(Camera.getCameraRotation());
		WebWalking.walkToBank();
		openBank();
		handleBankWindow();
		WebWalking.walkTo((Positionable) Dispatcher.get()
				.get(ValueType.HOME_TILE, null).getValue());
	}

	// TODO DEPOSIT ALL EXCEPT WHAT?
	private void handleBankWindow() {
		Banking.depositAll();
		Banking.withdraw(10,
				(String) Dispatcher.get().get(ValueType.FOOD_NAME, null)
						.getValue());
		Banking.close();
	}

	private void openBank() {
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

	private CustomPaths getModifiedPath(String[] names) {
		if (names == null || names.length == 0)
			return null;
		else
			return CustomPaths.getCustomPath(names[0]);
	}

	private boolean shouldBank() {
		return Inventory.find((String) Dispatcher.get()
				.get(ValueType.FOOD_NAME, null).getValue()).length == 0
				|| Inventory.isFull();
	}

}
