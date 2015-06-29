package scripts.CombatAIO.com.base.api.walking.actions;

import org.tribot.api2007.Combat;

import scripts.CombatAIO.com.base.api.walking.types.DAction;

public class DChangeAttackStyle implements DAction {

	private String style;

	public DChangeAttackStyle(String style) {
		this.style = style;
	}

	private static final long serialVersionUID = 8829035779123689633L;

	@Override
	public void execute() {
		Combat.selectAttackAction(style);
	}

	@Override
	public String toString() {
		return "change attack style to " + style;
	}

}
