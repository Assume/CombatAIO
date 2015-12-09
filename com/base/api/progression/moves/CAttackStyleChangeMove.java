package scripts.CombatAIO.com.base.api.progression.moves;

import org.tribot.api2007.Combat;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;

public class CAttackStyleChangeMove extends CProgressionAction {

	private static final long serialVersionUID = 5779749632892926419L;

	private int style;

	public CAttackStyleChangeMove(int style) {
		this.style = (style - 1);
	}

	@Override
	public void execute() {
		Combat.selectIndex(style);
	}

	@Override
	public String toString() {
		return "change attack style to " + (style + 1);
	}

}
