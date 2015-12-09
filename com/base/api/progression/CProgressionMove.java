package scripts.CombatAIO.com.base.api.progression;

import java.io.Serializable;

public class CProgressionMove implements Serializable {

	private static final long serialVersionUID = 3522615222676233364L;

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
		this.con.deactivate();
	}

	@Override
	public String toString() {
		return "if " + this.con.toString() + " then " + this.action.toString();
	}

}
