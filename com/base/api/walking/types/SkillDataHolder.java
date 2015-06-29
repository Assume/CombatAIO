package scripts.CombatAIO.com.base.api.walking.types;

import org.tribot.api2007.Skills.SKILLS;

public class SkillDataHolder {

	private int start_level;
	private int start_exp;
	private SKILLS skill;

	public SkillDataHolder(SKILLS skill) {
		this.skill = skill;
	}

	public void init() {
		this.start_exp = skill.getXP();
		this.start_level = skill.getActualLevel();
	}

	public int getExperienceGained() {
		return skill.getXP() - start_exp;
	}

	public int getLevelsGained() {
		return skill.getActualLevel() - start_level;
	}

}
