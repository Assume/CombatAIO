package scripts.CombatAIO.com.base.api.general.walking.types;

import org.tribot.api2007.types.RSArea;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class CustomMovement {

	private RSArea activation_area;
	private DFullHolder holder;
	private String name;
	private MovementType type;

	public CustomMovement(RSArea activation_area, DFullHolder holder,
			String name, MovementType type) {
		this.activation_area = activation_area;
		this.holder = holder;
		this.name = name;
		this.type = type;
	}

	public MovementType getMovementType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public RSArea getActivationArea() {
		return this.activation_area;
	}

}
