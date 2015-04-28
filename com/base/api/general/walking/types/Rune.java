package scripts.CombatAIO.com.base.api.general.walking.types;

public enum Rune {
	// TODO add rune ids
	LAW(0), WATER(0), EARTH(0), FIRE(0), AIR(0);

	private int id;

	Rune(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

}
