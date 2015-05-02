package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.General;

import scripts.priv.drennon.background.DAction;

public class DWriteToDebug implements DAction {

	private String what;

	public DWriteToDebug(String what) {
		this.what = what;
	}

	private static final long serialVersionUID = -2483090687384641630L;

	@Override
	public void execute() {
		General.println(what);

	}

	@Override
	public String toString() {
		return "write " + what + " to debug";
	}

}
