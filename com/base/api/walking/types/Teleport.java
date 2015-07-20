package scripts.CombatAIO.com.base.api.walking.types;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.magic.books.NormalSpell;
import scripts.CombatAIO.com.base.main.utils.Logger;

public enum Teleport {

	VARROCK_TELEPORT(8007, new RSTile(3212, 3428), NormalSpell.VARROCK_TELEPORT), LUMBRIDGE_TELEPORT(
			8008, new RSTile(3221, 3219), NormalSpell.LUMBRIDGE_TELEPORT), FALADOR_TELEPORT(
			8009, new RSTile(2965, 3381), NormalSpell.FALADOR_TELEPORT), CAMELOT_TELEPORT(
			8010, new RSTile(2757, 3477), NormalSpell.CAMELOT_TELEPORT), HOUSE_TELEPORT(
			8013, null, null);

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
		}, General.random(7000, 9000));
		return Player.getPosition().distanceTo(getSpellLocationResult()) < 25;
	}

	public boolean canTeleport() {
		return Inventory.find(tab_id).length > 0
				|| (this.spell.hasRequiredLevel() && this.spell
						.hasRequiredRunes());
	}

	public RSTile getSpellLocationResult() {
		return this.spell_location_result;
	}

	public static Teleport getTeleportNearestTo(RSTile pos) {
		Teleport nearest = null;
		int distance = Integer.MAX_VALUE;
		for (Teleport x : Teleport.values()) {
			int test_distance = pos.distanceTo(x.getSpellLocationResult());
			Logger.getLogger().print(Logger.SCRIPTER_ONLY,
					"Distance for: " + x + " is " + test_distance);
			if (test_distance < distance) {
				nearest = x;
				distance = test_distance;
			}
		}
		return nearest;
	}
}
