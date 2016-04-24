package scripts.CombatAIO.com.base.api.tasks.types;

import java.util.ArrayList;
import java.util.List;

import scripts.api.scriptapi.logging.Logger;

public abstract class Threadable extends Thread implements Pauseable {

	private static List<Threadable> threadables = new ArrayList<Threadable>();

	public static List<Threadable> getThreadables() {
		return threadables;
	}

	private boolean pause = false;
	private long pause_time;

	public boolean isPaused() {
		return this.pause;
	}
	
	
	public abstract void run();

	public void setPaused(boolean set) {
		this.pause = set;
		if(pause)
			Logger.getLogger().print(getClass());
		if (set)
			this.pause_time = System.currentTimeMillis();
		else
			this.pause_time = -1;
	}

	public long getPauseTime() {
		return this.pause_time;
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
