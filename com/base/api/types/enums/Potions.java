package scripts.CombatAIO.com.base.api.types.enums;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Game;
import org.tribot.api2007.Skills.SKILLS;

public enum Potions {

	// STRENGTH POTION WOULD CONTAIN ID OF STRENGTH POTION AND SUPER STRENGTH
	// ETC ETC

	STRENGTH(SKILLS.STRENGTH, 2440, 157, 159, 161, 113, 115, 117, 119), DEFENCE(
			SKILLS.DEFENCE, 2442, 163, 165, 167, 2432, 133, 135, 137), ATTACK(
			SKILLS.ATTACK, 2436, 145, 147, 149, 2428, 121, 123, 125), COMBAT(
			SKILLS.ATTACK, 12695, 12697, 12699, 12701, 9739, 9741, 9743, 9745), RANGING(
			SKILLS.RANGED, 2444, 169, 171, 173), OVERLOAD(SKILLS.ATTACK), PRAYER(
			null, 2434, 139, 141, 143), ANTI_POISON(null, 2446, 175, 177, 179,
			2448, 181, 183, 185), ;

	private SKILLS skill;
	private int[] ids;

	Potions(SKILLS skill, int... ids) {
		this.skill = skill;
		this.ids = ids;
	}

	public static boolean isPotionId(int id) {
		int[] ids = combineIDs(Potions.values());
		for (int x : ids)
			if (x == id)
				return true;
		return false;
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

	public static int[] getAllIds(int id) {
		for (Potions x : values())
			for (int y : x.ids)
				if (y == id)
					return x.getPotionsIDs();
		return new int[0];
	}
}
