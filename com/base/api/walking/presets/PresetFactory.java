package scripts.CombatAIO.com.base.api.walking.presets;

import scripts.CombatAIO.com.base.api.walking.presets.made.WyrvensPreset;

public enum PresetFactory {

	NONE(null), WYRVENS_HOUSE_TELEPORT(new WyrvensPreset(WyrvensPreset.NAME,
			WyrvensPreset.HOUSE_TELEPORT));

	private Preset preset;

	private PresetFactory(Preset preset) {
		this.preset = preset;
	}

	public Preset getPreset() {
		return this.preset;
	}

}
