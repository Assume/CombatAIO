package scripts.CombatAIO.com.base.api.progression;

import java.io.Serializable;

public abstract class CProgressionCondition implements Serializable {

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
