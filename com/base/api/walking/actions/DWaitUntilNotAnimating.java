package scripts.CombatAIO.com.base.api.walking.actions;

import org.tribot.api.General;
import org.tribot.api2007.Player;

import scripts.CombatAIO.com.base.api.walking.types.DAction;

public class DWaitUntilNotAnimating implements DAction {

	private static final long serialVersionUID = -6858454871035409037L;

	@Override
	public void execute() {
		while (Player.getAnimation() != -1)
			General.sleep(50);

	}

	@Override
	public String toString() {
		return "wait until player isn't animating";
	}
	
}
