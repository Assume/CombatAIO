package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.General;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DSleep implements DAction {

	private long time;

	public DSleep(long time) {
		this.time = time;
	}

	private static final long serialVersionUID = 6575519298039578691L;

	@Override
	public void execute() {
		MonitorMain.pause();
		General.sleep(time);
		MonitorMain.unpause();
	}

	@Override
	public String toString() {
		return "sleep for " + time;
	}

}
