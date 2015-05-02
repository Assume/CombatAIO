package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api2007.Login;
import org.tribot.api2007.WorldHopper;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;

public class DWorldHop implements DAction {


	public static int initial_world;

	private int world;

	public DWorldHop(int world) {
		this.world = world;
	}

	private static final long serialVersionUID = 6441411382459508476L;

	@Override
	public void execute() {
		MonitorMain.pause();
		WorldHopper.changeWorld(world == -1 ? initial_world : world);
		Login.login();
		MonitorMain.unpause();
	}

	@Override
	public String toString() {
		return "hop to world " + world;
	}

}
