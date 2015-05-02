package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DStopScriptAction implements DAction {

	private static final long serialVersionUID = -1586429432266262913L;

	@Override
	public void execute() {
		MonitorMain.main_thread.stop();
		
		
	}

	@Override
	public String toString() {
		return "stop script";
	}
	
}
