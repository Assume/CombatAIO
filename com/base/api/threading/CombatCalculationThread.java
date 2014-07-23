package scripts.CombatAIO.com.base.api.threading;

public class CombatCalculationThread implements Runnable {

	private CombatThread combat_thread;

	public CombatCalculationThread(CombatThread combat_thread) {
		this.combat_thread = combat_thread;
	}

	@Override
	public void run() {
		/*
		 * only runs once this.combat_thread.setMonsters(calculated_monsters);
		 */
	}

}
