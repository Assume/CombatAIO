package scripts.CombatAIO.com.base.api.threading.types;

import java.util.List;

public abstract class Threadable implements Pauseable, Runnable {

	private List<PauseType> pause_types;

	public Threadable(List<PauseType> pause_types) {
		this.pause_types = pause_types;
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		if (this.pause_types == null)
			return false;
		return this.pause_types.contains(type);
	}

}
