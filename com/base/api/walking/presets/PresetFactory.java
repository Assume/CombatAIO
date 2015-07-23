package scripts.CombatAIO.com.base.api.walking.presets;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.types.constants.HomeTiles;
import scripts.CombatAIO.com.base.api.types.constants.MonsterArea;
import scripts.CombatAIO.com.base.api.walking.presets.made.WyrvensPreset;

public enum PresetFactory {

	NONE(null, null, null), WYRVENS_HOUSE_TELEPORT(new WyrvensPreset(
			WyrvensPreset.NAME, WyrvensPreset.HOUSE_TELEPORT), null, null), RELLEKKA_WEST_ROCK_CRABS(
			null, HomeTiles.ROCK_CRABS_WEST_HOME_TILE,
			MonsterArea.RELLEKKA_ROCK_CRABS_WEST);

	private Preset preset;

	private RSTile home_tile;
	private RSArea home_area;

	private PresetFactory(Preset preset, RSTile home_tile, RSArea home_area) {
		this.preset = preset;
	}

	public Preset getPreset() {
		return this.preset;
	}

	public RSTile getHomeTile() {
		return this.home_tile;
	}

	public RSArea getArea() {
		return this.home_area;
	}
}
