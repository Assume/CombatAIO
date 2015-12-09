package scripts.CombatAIO.com.base.api.progression.conditions;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.tribot.api2007.Skills.SKILLS;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;

public enum CConditions {

	TIME_ELAPSED {
		@Override
		public CProgressionCondition make() {
			int time_in_minutes = parse(JOptionPane.showInputDialog(null,
					"Enter time in minutes that shall have passed"));
			if (time_in_minutes == -1)
				return null;
			return new CTimeElapsed(time_in_minutes);
		}
	},
	CURRENT_LEVEL {
		@Override
		public CProgressionCondition make() {
			JComboBox<SKILLS> jcb = new JComboBox<SKILLS>(new SKILLS[] {
					SKILLS.ATTACK, SKILLS.STRENGTH, SKILLS.HITPOINTS,
					SKILLS.RANGED, SKILLS.HITPOINTS });
			JOptionPane.showMessageDialog(null, jcb, "Select skill to track",
					JOptionPane.QUESTION_MESSAGE, null);
			SKILLS skill = (SKILLS) jcb.getSelectedItem();
			int level = parse(JOptionPane.showInputDialog("Enter level: "));
			return new CCurrentLevel(skill, level);
		}
	},
	OUT_OF_FOOD_IN_BANK {
		@Override
		public CProgressionCondition make() {
			return new COutOfFoodInBank();
		}

	};

	public abstract CProgressionCondition make();

	private static int parse(String parse) {
		if (parse == null)
			return -1;
		return Integer.parseInt(parse.replaceAll("[^0-9]", ""));
	}

}
