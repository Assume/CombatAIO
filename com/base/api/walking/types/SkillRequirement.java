package scripts.CombatAIO.com.base.api.walking.types;

import org.tribot.api2007.Skills.SKILLS;

public class SkillRequirement {

	private int level;

	private SKILLS skill;

	public SkillRequirement(int level, SKILLS skill) {
		this.skill = skill;
	}

	public boolean hasRequirement() {
		return skill.getActualLevel() >= level;
	}

}
