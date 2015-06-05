package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api2007.Skills.SKILLS;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;

public class CCurrentLevel extends CProgressionCondition {

	private SKILLS skill;
	private int level;

	public CCurrentLevel(SKILLS skill, int level) {
		this.skill = skill;
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}

	@Override
	protected boolean should_progress() {
		return skill.getActualLevel() >= this.level;
	}

	public SKILLS getSkill() {
		return this.skill;
	}

}
