package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import scripts.priv.drennon.background.DAction;

public class DCloseClientAction implements DAction {

	private static final long serialVersionUID = -2126240782960801730L;

	@Override
	public void execute() {
		System.exit(0);

	}

	@Override
	public String toString() {
		return "close client";
	}
	
}
