package scripts.CombatAIO.com.base.api.progression;


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
		this.con.deactivate();
	}


}
