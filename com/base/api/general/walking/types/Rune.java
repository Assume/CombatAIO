package scripts.CombatAIO.com.base.api.general.walking.types;

public enum Rune {
	// TODO add rune ids
	FIRE(554), WATER(555), AIR(556), EARTH(557), LAW(563);

	private int id;

	Rune(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

}
