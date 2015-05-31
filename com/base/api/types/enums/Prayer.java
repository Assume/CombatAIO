package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api.General;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.Prayer.PRAYERS;
import org.tribot.api2007.Skills;

public enum Prayer {

	NONE(null, null, 10000), PIETY(PRAYERS.PIETY, "Piety", 70), CHIVALRY(
			PRAYERS.CHIVALRY, "Chivalry", 60), PROTECT_FROM_MELEE(
			PRAYERS.PROTECT_FROM_MELEE, "Protect from Melee", 43), PROTECT_FROM_MISSILES(
			PRAYERS.PROTECT_FROM_MISSILES, "Protect from Missiles", 40), PROTECT_FROM_MAGIC(
			PRAYERS.PROTECT_FROM_MAGIC, "Protect from Magic", 37);

	private int levelRequired;
	private String name;
	private PRAYERS p;

	Prayer(final PRAYERS p, final String name, final int levelRequired) {
		this.p = p;
		this.name = name;
		this.levelRequired = levelRequired;
	}

	public PRAYERS getPrayer() {
		return p;
	}

	public String getName() {
		return name;
	}

	public int getReqLevel() {
		return levelRequired;
	}

	public boolean isActivated() {
		return this == NONE ? true : p.isEnabled();
	}

	public void disable() {
		Options.setQuickPrayersOn(false);
	}

	public void activate() {
		Options.setQuickPrayersOn(true);
	}

	public void flicker() {
		if (this == PIETY || this == CHIVALRY)
			if (!isActivated()) {
				int xp = Skills.getXP(Skills.SKILLS.HITPOINTS);
				activate();
				for (int fsafe = 0; Skills.getXP(Skills.SKILLS.HITPOINTS) == xp
						&& !(Player.getRSPlayer().getInteractingCharacter() == null)
						&& fsafe < 40; fsafe++)
					General.sleep(15);
				disable();
			} else {
				disable();
			}
	}

	public static Prayer parse(String eval) {
		for (Prayer p : values())
			if (eval.equalsIgnoreCase(p.toString()))
				return p;
		return null;
	}
}
