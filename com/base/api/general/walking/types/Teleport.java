package scripts.CombatAIO.com.base.api.general.walking.types;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.magic.books.NormalSpell;

public enum Teleport {

	VARROCK_TELEPORT(8007, new RSTile(3212, 3428), NormalSpell.VARROCK_TELEPORT), LUMBRIDGE_TELEPORT(
			8008, new RSTile(3221, 3219), NormalSpell.LUMBRIDGE_TELEPORT), FALADOR_TELEPORT(
			8009, new RSTile(2965, 3381), NormalSpell.FALADOR_TELEPORT), CAMELOT_TELEPORT(
			810, new RSTile(2757, 3477), NormalSpell.CAMELOT_TELEPORT);

	private int tab_id;
	private RSTile spell_location_result;
	private NormalSpell spell;

	Teleport(int tab_id, RSTile spell_location_result, NormalSpell spell) {
		this.tab_id = tab_id;
		this.spell_location_result = spell_location_result;
		this.spell = spell;
	}

	public boolean teleport() {
		RSItem[] teleport_tab = Inventory.find(tab_id);
		if (teleport_tab.length > 0)
			teleport_tab[0].click("Break");
		else {
			this.spell.select();
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
				|| (this.spell.hasRequiredLevel() && this.spell
						.hasRequiredRunes());
	}

	/*
	 * private boolean hasAllRequiredRunes() { RSItem[] air =
	 * Inventory.find(Rune.AIR.getId()); RSItem[] law =
	 * Inventory.find(Rune.LAW.getId()); RSItem[] secondary =
	 * Inventory.find(getSecondaryRune().getId()); int airs_required = this ==
	 * CAMELOT_TELEPORT ? 5 : 3; if (air.length == 0 || air[0].getStack() <
	 * airs_required) return false; if (law.length == 0) return false; if (this
	 * == CAMELOT_TELEPORT) return true; if (secondary.length == 0) return
	 * false; return true;
	 * 
	 * }
	 */

	public RSTile getSpellLocationResult() {
		return this.spell_location_result;
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
