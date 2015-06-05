package scripts.CombatAIO.com.base.api.progression;

public abstract class CProgressionCondition {

	private boolean deactivated;

	public CProgressionCondition() {
		deactivated = false;
	}

	protected abstract boolean should_progress();

	public boolean shouldProgress() {
		if (this.deactivated)
			return false;
		return should_progress();
	}

	public void deactivate() {
		deactivated = true;

	}

}
