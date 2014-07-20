package scripts.CombatAIO.com.base.api.threading;

public class Mouse implements Runnable {
	boolean run = true;
	
	public void stop() {
		run = false;
	}
	
	@Override
	public void run() {
		while (run) {
	
		}
	}

}
