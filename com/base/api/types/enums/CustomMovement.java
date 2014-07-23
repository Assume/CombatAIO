package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum CustomMovement {

	BASIC(null, null, null) {

		@Override
		protected boolean leave_alone() {
			// TODO Auto-generated method stub
			return false;
		}

	};

	private RSArea activation_location;
	private RSArea deactivation_location;
	private RSTile next_webwalking_location;

	CustomMovement(RSArea activation_location, RSArea deactivation_location,
			RSTile next_webwalking_location) {

	}

	protected abstract boolean leave_alone();

	public boolean execute() {
		if (this.leave_alone()) {

		}
		return false;
	}

	public boolean shouldExecute() {
		return this.activation_location.contains(Player.getRSPlayer());
	}

}
