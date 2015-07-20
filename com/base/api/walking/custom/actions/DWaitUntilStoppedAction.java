package scripts.CombatAIO.com.base.api.walking.custom.actions;

import org.tribot.api.General;
import org.tribot.api2007.Player;

import scripts.CombatAIO.com.base.api.walking.custom.types.DAction;

public class DWaitUntilStoppedAction implements DAction {

	private static final long serialVersionUID = -1776732526084765377L;

	@Override
	public void execute() {
		while (Player.isMoving())
			General.sleep(50);

	}
	
	
	@Override
	public String toString() {
		return "wait until player isn't moving";
	}

}
