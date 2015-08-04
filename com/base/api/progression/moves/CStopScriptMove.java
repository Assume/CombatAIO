package scripts.CombatAIO.com.base.api.progression.moves;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class CStopScriptMove extends CProgressionAction {

	@Override
	public void execute() {
		Dispatcher.get().stop(
				"Script requested to stop through Progression Manager");
	}

}
