package scripts.CombatAIO.com.base.api.walking.presets;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.tasks.Dispatcher;
import scripts.CombatAIO.com.base.api.types.constants.HomeTiles;
import scripts.CombatAIO.com.base.api.types.constants.MonsterArea;
import scripts.CombatAIO.com.base.api.types.constants.ScriptIDs;
import scripts.CombatAIO.com.base.api.walking.presets.made.WyrvensPreset;

public enum PresetFactory {

	NONE("None", null, null, null), WYRVENS_HOUSE_TELEPORT("Wyrvens",
			new WyrvensPreset(WyrvensPreset.HOUSE_TELEPORT), null, null), RELLEKKA_WEST_ROCK_CRABS(
			"Rock Crabs West", null, HomeTiles.ROCK_CRABS_WEST_HOME_TILE,
			MonsterArea.RELLEKKA_ROCK_CRABS_WEST);

	private Preset preset;

	private RSTile home_tile;
	private RSArea home_area;
	private String name;

	private PresetFactory(String name, Preset preset, RSTile home_tile,
			RSArea home_area) {
		this.preset = preset;
		this.name = name;
	}

	public Preset get() {
		return this.preset;
	}

	public RSTile getHomeTile() {
		return this.home_tile;
	}

	public RSArea getArea() {
		return this.home_area;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	// TODO WHEN ACTUALLY ADD PRESETS
	public static PresetFactory[] getPresetsForScript() {
		switch (Dispatcher.get().getRepoID()) {
		case ScriptIDs.COMBAT_AIO_LITE:
			return new PresetFactory[] { PresetFactory.NONE };
		case ScriptIDs.ASSUMES_GOT_CRABS:
			return new PresetFactory[] { RELLEKKA_WEST_ROCK_CRABS };
		default:
			return new PresetFactory[] { PresetFactory.NONE };
		}
	}

	public static PresetFactory getPresetForName(String property) {
		for (PresetFactory x : values())
			if (x.toString().equalsIgnoreCase(property))
				return x;
		return null;
	}

}
