package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

public enum SkillData {

	STRENGTH(SKILLS.STRENGTH), ATTACK(SKILLS.ATTACK), DEFENCE(SKILLS.DEFENCE), HITPOINTS(
			SKILLS.HITPOINTS), RANGED(SKILLS.RANGED), MAGIC(SKILLS.MAGIC), SLAYER(
			SKILLS.SLAYER);

	private int start_exp;
	private int start_level;
	private SKILLS skill;
	private boolean should_show;

	SkillData(SKILLS skill) {
		this.skill = skill;
		this.start_exp = 0;
		this.start_level = 0;
	}

	private void init() {
		this.start_exp = Skills.getXP(skill);
		this.start_level = skill.getActualLevel();

	}

	public boolean shouldShow() {
		return should_show;
	}

	public int getExperienceGained() {
		return skill.getXP() - this.start_exp;
	}

	public int getLevelsGained() {
		return skill.getActualLevel() - this.start_level;
	}

	public int getExperienceToNextLevel() {
		return Skills.getXPToNextLevel(skill);
	}

	public int getPercentToNextLevel() {
		return Skills.getPercentToNextLevel(skill);
	}

	public static void initiate() {
		for (SkillData x : SkillData.values())
			x.init();
	}

	public int getStartingLevel() {
		return this.start_level;
	}

	public void setShouldShow() {
		this.should_show = true;
	}

}
