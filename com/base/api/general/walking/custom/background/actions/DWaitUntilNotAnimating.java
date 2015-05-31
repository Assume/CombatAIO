package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.General;
import org.tribot.api.rs3.Player;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;

public class DWaitUntilNotAnimating implements DAction {

	private static final long serialVersionUID = -6858454871035409037L;

	@Override
	public void execute() {
		while (Player.getAnimation() != -1)
			General.sleep(50);

	}

}
