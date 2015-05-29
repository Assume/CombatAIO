package scripts.CombatAIO.com.base.api.types.enums;

public enum Food {

	None(-1, -1), Lobster(379, 12), BonesToPeaches(6883, 8), Trout(333, 8), Salmon(
			329, 8), Monkfish(7946, 16), Shark(385, 20), Swordfish(373, 14), Tuna(
			361, 8), Kebab(1971, 8), Karambwan(3144, 20), Bass(365, 8);

	private int id;
	private int heal_value;

	Food(int id, int heal_value) {
		this.id = id;
		this.heal_value = heal_value;
	}

	public int getId() {
		return this.id;
	}

	public static Food getFoodFromName(String name) {
		for (Food x : Food.values())
			if (x.toString().equalsIgnoreCase(name))
				return x;
		return null;
	}

}
