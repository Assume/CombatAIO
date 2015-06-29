package scripts.CombatAIO.com.base.api.walking.types;

import org.tribot.api2007.types.RSTile;

public enum JEWELERY_TELEPORT_LOCATIONS {

	EDGEVILLE(new RSTile(3087, 3494), "Edgeville"), AL_KHARID_DUEL_AREA(
			new RSTile(3319, 3236), "Duel Arena"), CASTLE_WARS(new RSTile(2441,
			3090), "Castle Wars"), BARBARIAN_OUTPOST(new RSTile(2522, 3570),
			"Barbarian Outpost"), KARAMJA(new RSTile(2906, 3178), "Karamja"), DRAYNOR_VILLAGE(
			new RSTile(3081, 3250), "Draynor Village");

	private RSTile tile;
	private String teleport_code;

	JEWELERY_TELEPORT_LOCATIONS(RSTile tile, String teleport_code) {
		this.tile = tile;
		this.teleport_code = teleport_code;
	}

	public String getTeleportCode() {
		return this.teleport_code;
	}

	public RSTile getTeleportTile() {
		return this.tile;
	}

}
