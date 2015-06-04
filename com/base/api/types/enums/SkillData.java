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
	private int exp;
	private int level;
	private int exp_to_next_level;
	private int percent_to_next_level;

	SkillData(SKILLS skill) {
		this.skill = skill;
		this.start_exp = 0;
		this.start_level = 0;
	}

	private void init() {
		this.start_exp = Skills.getXP(skill);
		this.start_level = skill.getActualLevel();

	}

	public int getExperienceGained() {
		return this.exp;
	}

	public int getLevelsGained() {
		return this.level;
	}

	public int getExperienceToNextLevel() {
		return this.exp_to_next_level;
	}

	public int getPercentToNextLevel() {
		return this.percent_to_next_level;
	}

	public static void initiate() {
		for (SkillData x : SkillData.values())
			x.init();
	}

	public void update() {
		this.exp = skill.getXP() - this.start_exp;
		this.level = skill.getActualLevel() - this.start_level;
		this.exp_to_next_level = Skills.getXPToNextLevel(skill);
		this.percent_to_next_level = Skills.getPercentToNextLevel(skill);
	}

	public static void updateAll() {
		for (SkillData x : values())
			x.update();
	}

	public int getStartingLevel() {
		return this.start_level;
	}

	public static int getTotalExperienceGained() {
		int tot = 0;
		for (SkillData x : values())
			tot += x.getExperienceGained();
		return tot;
	}

}
