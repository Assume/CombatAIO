package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.vars;

public class DStopBackgroundScript implements DAction {

	private static final long serialVersionUID = -570256850442390559L;

	@Override
	public void execute() {
		vars.get().stop = true;
	}

	@Override
	public String toString() {
		return "stop background script";
	}

}
