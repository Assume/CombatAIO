package scripts.CombatAIO.com.base.api.progression.moves;

import org.tribot.api2007.Combat;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;

public class AttackStyleChangeMove extends CProgressionAction {

	private String style;

	public AttackStyleChangeMove(String style) {
		this.style = style;
	}

	@Override
	public void execute() {
		Combat.selectAttackAction(style);
	}

}
