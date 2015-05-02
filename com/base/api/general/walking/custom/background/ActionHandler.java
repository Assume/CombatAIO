package scripts.CombatAIO.com.base.api.general.walking.custom.background;

public class ActionHandler {

	private DFullHolder full_holder;

	public ActionHandler(DFullHolder full_holder) {
		this.full_holder = full_holder;
	}

	public void go() {
		full_holder.checkAndExecute();
	}

}
