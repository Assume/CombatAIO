package scripts.CombatAIO.com.base.api.types.enums;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Game;
import org.tribot.api2007.Skills.SKILLS;

public enum Potions {

	// STRENGTH POTION WOULD CONTAIN ID OF STRENGTH POTION AND SUPER STRENGTH
	// ETC ETC

	STRENGTH(SKILLS.STRENGTH), DEFENCE(SKILLS.DEFENCE), ATTACK(SKILLS.ATTACK), COMBAT(
			SKILLS.ATTACK), RANGING(SKILLS.RANGED), OVERLOAD(SKILLS.ATTACK), PRAYER(
			null), ANTI_POISON(null), ;

	private SKILLS skill;
	private int[] ids;

	Potions(SKILLS skill, int... ids) {
		this.skill = skill;
		this.ids = ids;
	}

	public SKILLS getSkill() {
		return skill;
	}

	public int[] getPotionsIDs() {
		return this.ids;
	}

	protected static int[] combineIDs(Potions... potions) {
		int total_index = 0;
		for (Potions x : potions)
			total_index += x.getPotionsIDs().length;
		int[] all = new int[total_index];
		int index = 0;
		for (int m = 0; m < potions.length; m++) {
			int[] ids = potions[m].getPotionsIDs();
			for (int i = 0; i < ids.length; i++)
				all[index++] = ids[i];
		}
		return all;
	}

	public static Potions[] getPotionsRequired() {
		List<Potions> potions = new ArrayList<Potions>();
		for (Potions x : Potions.values()) {
			SKILLS skill = x.getSkill();
			if (skill != null)
				if (skill.getCurrentLevel() < (skill.getActualLevel() + 3))
					potions.add(x);
		}
		if (Game.getSetting(102) > 0)
			potions.add(ANTI_POISON);
		if (SKILLS.PRAYER.getCurrentLevel() < 5)
			potions.add(PRAYER);
		return potions.toArray(new Potions[potions.size()]);
	}
}
