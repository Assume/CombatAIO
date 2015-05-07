package scripts.CombatAIO.com.base.api.general.walking.types;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.conditions.RSArea;

public class CustomMovement {
	
	private RSArea activation_area;
	private DFullHolder holder;
	private String name;

	public CustomMovement(RSArea activation_area, DFullHolder holder,
			String name) {
		this.activation_area = activation_area;
		this.holder = holder;
		this.name = name;
	}

}
