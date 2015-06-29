package scripts.CombatAIO.com.base.api.walking.actions;

import org.tribot.api.General;

import scripts.CombatAIO.com.base.api.walking.types.DAction;


public class DSleep implements DAction {

	private long time;

	public DSleep(long time) {
		long ten_percent = time / 10;
		this.time = General.random((int) (time - ten_percent),
				(int) (time + ten_percent));
	}

	private static final long serialVersionUID = 6575519298039578691L;

	@Override
	public void execute() {
		General.sleep(time);
	}

	@Override
	public String toString() {
		return "sleep for " + time;
	}

}
