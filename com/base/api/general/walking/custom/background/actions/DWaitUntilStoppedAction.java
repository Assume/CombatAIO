package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.General;
import org.tribot.api.rs3.Player;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;

public class DWaitUntilStoppedAction implements DAction {

	private static final long serialVersionUID = -1776732526084765377L;

	@Override
	public void execute() {
		while (Player.isMoving())
			General.sleep(50);

	}

}
