package scripts.CombatAIO.com.base.api.threading.types;

import java.util.ArrayList;
import java.util.List;

public abstract class Threadable extends Thread implements Pauseable {

	private static List<Threadable> threadables = new ArrayList<Threadable>();

	public static List<Threadable> getThreadables() {
		return threadables;
	}

	private boolean pause = false;

	public boolean isPaused() {
		return this.pause;
	}

	public void setPaused(boolean set) {
		this.pause = set;
	}

	private List<PauseType> pause_types;

	public Threadable(List<PauseType> pause_types) {
		this.pause_types = pause_types;
		threadables.add(this);
	}

	@Override
	public boolean hasPauseType(PauseType type) {
		if (this.pause_types == null)
			return false;
		return this.pause_types.contains(type);
	}

}
