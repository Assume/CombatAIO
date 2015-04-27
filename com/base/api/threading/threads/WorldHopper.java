package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.List;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;

public class WorldHopper extends Threadable {

	public WorldHopper() {
		this(null);
	}

	private WorldHopper(List<PauseType> pause_types) {
		super(pause_types);
	}

}
