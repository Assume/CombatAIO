package scripts.CombatAIO.com.base.api.general.walking.custom.types;

public abstract class CAction {

	public abstract boolean execute();

	public boolean onFail() {
		if (this.on_fail != null)
			return this.on_fail.execute();
		return false;
	}

	private CCondition con;
	private CAction on_fail;

	private CAction(CCondition con, CAction on_fail) {
		this.con = con;
		this.on_fail = on_fail;
	}

}
