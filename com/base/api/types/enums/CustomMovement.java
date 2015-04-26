package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum CustomMovement {

	BASIC_ONE(null, null) {

		@Override
		protected boolean leave_alone() {
			// TODO Auto-generated method stub
			return false;
		}

	};

	private RSArea activation_location;
	private RSTile next_webwalking_location;

	CustomMovement(RSArea activation_location, RSTile next_webwalking_location) {
		this.activation_location = activation_location;
		this.next_webwalking_location = next_webwalking_location;

	}

	protected abstract boolean leave_alone();

	public Positionable execute() {
		this.leave_alone();
		return this.next_webwalking_location;
	}

	public boolean shouldExecute() {
		return this.activation_location.contains(Player.getRSPlayer());
	}

}
