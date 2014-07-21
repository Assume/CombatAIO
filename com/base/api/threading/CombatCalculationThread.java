package scripts.CombatAIO.com.base.api.threading;

import scripts.CombatAIO.com.base.api.threading.types.Pauseable;

public class CombatCalculationThread implements Runnable, Pauseable {

	private CombatThread combat_thread;

	public CombatCalculationThread(CombatThread combat_thread) {
		this.combat_thread = combat_thread;
	}

	@Override
	public void run() {
		/*
		 * while(true) calculate
		 * this.combat_thread.setMonsters(calculated_monsters);
		 */
	}

	@Override
	public void pause() {
		this.pause();

	}

}
