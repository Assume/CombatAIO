package scripts.CombatAIO.com.base.api.progression;

public abstract class CProgressionMove {

	private CProgressionAction action;

	public CProgressionMove(CProgressionAction action) {
		this.action = action;
	}

	public abstract boolean shouldProgress();

	public void execute() {
		this.action.execute();
	}

}
