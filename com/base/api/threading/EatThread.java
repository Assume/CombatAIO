package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.Dispatchable;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;

public class EatThread implements Runnable, Pauseable, Dispatchable {

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		/*
		 * while(true)
		 * if need to eat
		 * pause banking, combat, looting
		 * after finished reenable combat, banking, and looting
		 * 
		 */

	}

}
