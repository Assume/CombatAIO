package scripts.CombatAIO.com.base.api.progression.moves;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class CStopScriptAction extends CProgressionAction {

	private static final long serialVersionUID = -4277377185464201053L;

	@Override
	public void execute() {
		Dispatcher.get().stop(
				"Script requested to stop through Progression Manager");
	}

	@Override
	public String toString() {
		return "stop the script";
	}

}
