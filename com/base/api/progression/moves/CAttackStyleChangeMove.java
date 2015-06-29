package scripts.CombatAIO.com.base.api.progression.moves;

import org.tribot.api2007.Combat;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;

public class CAttackStyleChangeMove extends CProgressionAction {

	private int style;

	public CAttackStyleChangeMove(int style) {
		this.style = (style - 1);
	}

	@Override
	public void execute() {
		Combat.selectIndex(style);
	}

}
