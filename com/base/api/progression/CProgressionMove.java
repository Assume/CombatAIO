package scripts.CombatAIO.com.base.api.progression;

import scripts.CombatAIO.com.base.api.progression.conditions.CCurrentLevel;

public class CProgressionMove {

	private CProgressionCondition con;

	private CProgressionAction action;

	public CProgressionMove(CProgressionCondition con, CProgressionAction action) {
		this.con = con;
		this.action = action;
	}

	public boolean shouldProgress() {
		return con.shouldProgress();
	}

	public void execute() {
		this.action.execute();
	}

	public boolean removable() {
		return con instanceof CCurrentLevel;
	}

}
