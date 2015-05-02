package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.WebWalking;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DDepositAll implements DAction {

	private static final long serialVersionUID = -7279246484548782252L;

	@Override
	public void execute() {
		bank();

	}

	private void bank() {
		MonitorMain.pause();
		WebWalking.walkToBank();
		Banking.openBank();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Banking.isBankScreenOpen();
			}
		}, 10000);
		if (!Banking.isBankScreenOpen()) {
			bank();
			return;
		}
		Banking.depositAll();
		Banking.close();
		MonitorMain.unpause();
	}

}
