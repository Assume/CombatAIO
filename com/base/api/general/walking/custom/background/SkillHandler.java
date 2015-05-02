package scripts.CombatAIO.com.base.api.general.walking.custom.background;

import java.util.HashMap;
import java.util.Map;

import org.tribot.api2007.Skills.SKILLS;

public class SkillHandler {

	private static SkillHandler handler = new SkillHandler();

	public static SkillHandler get() {
		return handler;
	}

	private Map<SKILLS, SkillDataHolder> map = new HashMap<SKILLS, SkillDataHolder>();

	private SkillHandler() {
		for (SKILLS skill : SKILLS.values()) {
			map.put(skill, new SkillDataHolder(skill));
		}
	}

	public void init() {
		for (SkillDataHolder hold : map.values())
			hold.init();
	}

	public int getExperienceGained(SKILLS skill) {
		return map.get(skill).getExperienceGained();
	}

	public int getLevelsGained(SKILLS skill) {
		return map.get(skill).getLevelsGained();
	}

}
