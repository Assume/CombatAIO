package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSArea;

public enum CustomPaths {

	TEST(null, null, null, null, null);

	private RSArea[] activation_area_to_bank;
	private RSArea[] activation_area_to_monster;
	private String name;
	private CustomMovement[] to_bank;
	private CustomMovement[] to_monster;
	private int current_index;

	CustomPaths(String monster_names, RSArea[] activation_area_to_bank,
			RSArea[] activation_area_to_monster, CustomMovement[] to_bank,
			CustomMovement[] to_monster) {
		this.activation_area_to_bank = activation_area_to_bank;
		this.activation_area_to_monster = activation_area_to_monster;
		this.to_bank = to_bank;
		this.to_monster = to_monster;
		this.current_index = 0;

	}

	public RSArea getWebWalkingDeactivationArea(MovementType type) {
		RSArea[] area = type.equals(MovementType.TO_BANK) ? this.activation_area_to_bank
				: this.activation_area_to_monster;
		if (this.current_index >= area.length)
			return null;
		else
			return area[this.current_index];
	}

	public void execute(MovementType type) {
		CustomMovement[] movement = type.equals(MovementType.TO_BANK) ? this.to_bank
				: this.to_monster;
		if (this.current_index >= movement.length)
			return;
		else {
			movement[current_index].execute();
			if (this.current_index + 1 >= movement.length)
				this.current_index = 0;
			else
				this.current_index++;
		}
	}

	public static CustomPaths getCustomPath(String monster_name) {
		for (CustomPaths x : CustomPaths.values()) {
			if (x.name == null)
				return null;
			if (x.name.equalsIgnoreCase(monster_name))
				return x;
		}
		return null;
	}

	public boolean hasIndicesLeft(MovementType type) {
		CustomMovement[] movement = type.equals(MovementType.TO_BANK) ? this.to_bank
				: this.to_monster;
		return this.current_index < movement.length;
	}

	public Positionable getDestination(MovementType type) {
		RSArea[] area = type.equals(MovementType.TO_BANK) ? this.activation_area_to_bank
				: this.activation_area_to_monster;
		return area[this.current_index].getRandomTile();
	}
}
