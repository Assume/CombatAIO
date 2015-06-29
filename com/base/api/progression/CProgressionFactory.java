package scripts.CombatAIO.com.base.api.progression;

public class CProgressionFactory {

	CProgressionAction action;

	CProgressionCondition condition;

	public CProgressionFactory() {
		this.action = null;
		this.condition = null;
	}

	public void setAction(CProgressionAction action) {
		this.action = action;
	}

	public void setCondition(CProgressionCondition condition) {
		this.condition = condition;
	}

	public CProgressionMove make() {
		if (this.action == null || this.condition == null)
			return null;
		return new CProgressionMove(condition, action);
	}

}
