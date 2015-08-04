package scripts.CombatAIO.com.base.api.presets;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.presets.made.WaterfallFireGiantsPreset;
import scripts.CombatAIO.com.base.api.presets.made.WyrvensPreset;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.types.constants.HomeTiles;
import scripts.CombatAIO.com.base.api.types.constants.MonsterArea;
import scripts.CombatAIO.com.base.api.types.constants.ScriptIDs;
import scripts.CombatAIO.com.base.main.Dispatcher;

public enum PresetFactory {

	Automatic("Automatic", null, null, null),

	WYRVENS_HOUSE_TELEPORT("Wyrvens", new WyrvensPreset(
			WyrvensPreset.HOUSE_TELEPORT), null, null),

	RELLEKKA_WEST_ROCK_CRABS("Rock Crabs West", null,
			HomeTiles.ROCK_CRABS_WEST_HOME_TILE,
			MonsterArea.RELLEKKA_ROCK_CRABS_WEST),

	RELLEKKA_EAST_ROCK_CRABS("Rock Crabs East", null,
			HomeTiles.ROCK_CRABS_EAST_HOME_TILE,
			MonsterArea.RELLEKKA_ROCK_CRABS_EAST),

	FIRE_GIANTS_WATERFALL_W("Waterfall FGiants W",
			new WaterfallFireGiantsPreset(
					WaterfallFireGiantsPreset.REQUIREMENTS,
					WaterfallFireGiantsPreset.GAMES_NECKLACE,
					WaterfallFireGiantsPreset.ROPE),
			HomeTiles.FIRE_GIANTS_WEST_HOME_TILE, null),

	FIRE_GIANTS_WATERFALL_C("Waterfall FGiants C",
			new WaterfallFireGiantsPreset(
					WaterfallFireGiantsPreset.REQUIREMENTS,
					WaterfallFireGiantsPreset.GAMES_NECKLACE,
					WaterfallFireGiantsPreset.ROPE),
			HomeTiles.FIRE_GIANTS_CENTER_HOME_TILE, null);

	private Preset preset;

	private RSTile home_tile;
	private RSArea home_area;
	private String name;

	private PresetFactory(String name, Preset preset, RSTile home_tile,
			RSArea home_area) {
		this.preset = preset;
		this.name = name;
		this.home_tile = home_tile;
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
			return new PresetFactory[] { PresetFactory.Automatic };
		case ScriptIDs.ASSUMES_GOT_CRABS:
			return new PresetFactory[] { RELLEKKA_WEST_ROCK_CRABS,
					RELLEKKA_EAST_ROCK_CRABS };
		default:
			return new PresetFactory[] { Automatic, RELLEKKA_WEST_ROCK_CRABS,
					RELLEKKA_EAST_ROCK_CRABS, FIRE_GIANTS_WATERFALL_C,
					FIRE_GIANTS_WATERFALL_W };
		}
	}

	public static PresetFactory getPresetForName(String property) {
		for (PresetFactory x : values())
			if (x.toString().equalsIgnoreCase(property))
				return x;
		return null;
	}

	public void addBankItems() {
		if (this.get() == null)
			return;
		else
			for (BankItem x : this.get().getRequiredItems())
				Dispatcher.get().getBanker().addBankItem(x);

	}

}
