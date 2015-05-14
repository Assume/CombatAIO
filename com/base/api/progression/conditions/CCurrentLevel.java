package scripts.CombatAIO.com.base.api.progression.conditions;

import org.tribot.api2007.Skills.SKILLS;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.progression.CProgressionMove;

public class CCurrentLevel extends CProgressionMove {

	private SKILLS skill;
	private int level;

	public CCurrentLevel(CProgressionAction action, SKILLS skill, int level) {
		super(action);
		this.skill = skill;
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}

	@Override
	public boolean shouldProgress() {
		return skill.getActualLevel() >= this.level;
	}

	public SKILLS getSkill() {
		return this.skill;
	}

}
