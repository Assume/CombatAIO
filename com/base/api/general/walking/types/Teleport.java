package scripts.CombatAIO.com.base.api.general.walking.types;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;

public enum Teleport {
	// TODO add tab ids
	VARROCK_TELEPORT(0, "Varrock Teleport", 25, null, Rune.FIRE), LUMBRIDGE_TELEPORT(
			0, "Lumbridge Teleport", 31, null, Rune.EARTH), FALADOR_TELEPORT(0,
			"Falador Teleport", 37, null, Rune.WATER), CAMELOT_TELEPORT(0,
			"Camelot Teleport", 45, null, Rune.AIR);

	private int tab_id;
	private String spell_name;
	private int level_requirement;
	private RSTile spell_location_result;
	private Rune secondary_rune;

	Teleport(int tab_id, String spell_name, int level_requirement,
			RSTile spell_location_result, Rune secondary_rune) {
		this.tab_id = tab_id;
		this.spell_name = spell_name;
		this.level_requirement = level_requirement;
		this.spell_location_result = spell_location_result;
		this.secondary_rune = secondary_rune;

	}

	// TODO ADD SLEEPS
	public boolean teleport() {
		RSItem[] teleport_tab = Inventory.find(tab_id);
		if (teleport_tab.length > 0)
			teleport_tab[0].click("Break");
		else {
			Magic.selectSpell(this.getSpellName());
		}
		Timing.waitCondition(new Condition() {

			@Override
			public boolean active() {
				return Player.getPosition()
						.distanceTo(getSpellLocationResult()) < 25;
			}
		}, General.random(4000, 5000));
		return Player.getPosition().distanceTo(getSpellLocationResult()) < 25;
	}

	public boolean canTeleport() {
		return Inventory.find(tab_id).length > 0
				|| (SKILLS.MAGIC.getActualLevel() >= this.getLevelRequirement() && hasAllRequiredRunes());
	}

	private boolean hasAllRequiredRunes() {
		RSItem[] air = Inventory.find(Rune.AIR.getID());
		RSItem[] law = Inventory.find(Rune.LAW.getID());
		RSItem[] secondary = Inventory.find(getSecondaryRune().getID());
		int airs_required = this == CAMELOT_TELEPORT ? 5 : 3;
		if (air.length == 0 || air[0].getStack() < airs_required)
			return false;
		if (law.length == 0)
			return false;
		if (this == CAMELOT_TELEPORT)
			return true;
		if (secondary.length == 0)
			return false;
		return true;

	}

	public String getSpellName() {
		return this.spell_name;
	}

	public int getLevelRequirement() {
		return this.level_requirement;
	}

	public RSTile getSpellLocationResult() {
		return this.spell_location_result;
	}

	public Rune getSecondaryRune() {
		return this.secondary_rune;
	}

	public static Teleport getTeleportNearestTo(Positionable pos) {
		Teleport nearest = null;
		int distance = Integer.MAX_VALUE;
		for (Teleport x : Teleport.values()) {
			int test_distance = x.getSpellLocationResult().distanceTo(pos);
			if (test_distance < distance) {
				nearest = x;
				distance = test_distance;
			}
		}
		return nearest;
	}
}
