package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Getter;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.RSNPCValue;

public class CombatThread extends Getter implements Runnable, Pauseable {

	private String[] npc_names;
	private RSNPC current_target;

	public CombatThread(String... npc_names) {
		this.npc_names = npc_names;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		this.pause();
	}

	public Object getCurrentTarget() {
		return new RSNPCValue(current_target);
	}

	public Object getNextTarget() {
		return null;
	}

}
