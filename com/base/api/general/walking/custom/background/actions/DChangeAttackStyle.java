package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api2007.Combat;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DChangeAttackStyle implements DAction {

	private String style;

	public DChangeAttackStyle(String style) {
		this.style = style;
	}

	private static final long serialVersionUID = 8829035779123689633L;

	@Override
	public void execute() {
		MonitorMain.pause();
		Combat.selectAttackAction(style);
		MonitorMain.unpause();
	}

	@Override
	public String toString() {
		return "change attack style to " + style;
	}

}
