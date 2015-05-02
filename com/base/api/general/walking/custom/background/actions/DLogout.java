package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api2007.Login;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DLogout implements DAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4157961003796605895L;

	@Override
	public void execute() {
		MonitorMain.pause();
		Login.logout();
		MonitorMain.unpause();

	}

	@Override
	public String toString() {
		return "logout";
	}
	
}
