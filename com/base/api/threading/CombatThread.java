package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.Getter;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;

public class CombatThread extends Getter implements Runnable, Pauseable {

	private String[] npc_names;

	public CombatThread(String... npc_names) {
		this.npc_names = npc_names;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		this.pause();
	}

}
