package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api2007.types.RSArea;

public enum MonsterRequiringCustomPathing {

	TEST(null, null);

	private RSArea activation_area;
	private String name;

	MonsterRequiringCustomPathing(String monster_names,
			RSArea[] activation_areas) {

	}

	public static MonsterRequiringCustomPathing getCustomPath(
			String monster_name) {
		for (MonsterRequiringCustomPathing x : MonsterRequiringCustomPathing
				.values()) {
			if (x.name.equalsIgnoreCase(monster_name))
				return x;
		}
		return null;
	}

}
