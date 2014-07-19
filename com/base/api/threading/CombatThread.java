package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.RSNPCValue;

public class CombatThread implements Runnable, Pauseable {

	private String[] npc_names;
	private RSNPC current_target;
	private KillTracker kill_tracker;

	public CombatThread(String... npc_names) {
		this.npc_names = npc_names;
		this.kill_tracker = null; // TODO fix later
	}

	@Override
	public void run() {
		// TODO
	}

	@Override
	public void pause() {
		this.pause();
	}

	public Object getCurrentTarget() {
		return new RSNPCValue(current_target);
	}

	public Object getTotalKills() {
		return kill_tracker.getTotalKills();
	}

	public Object getNextTarget() {
		return null;
	}

}
