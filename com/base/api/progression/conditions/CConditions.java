package scripts.CombatAIO.com.base.api.progression.conditions;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.tribot.api2007.Skills.SKILLS;

import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;

public enum CConditions {

	TIME_ELAPSED {
		@Override
		CProgressionCondition make() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	CURRENT_LEVEL {
		@Override
		CProgressionCondition make() {
			JComboBox<SKILLS> jcb = new JComboBox<SKILLS>(new SKILLS[] {
					SKILLS.ATTACK, SKILLS.STRENGTH, SKILLS.HITPOINTS,
					SKILLS.RANGED, SKILLS.HITPOINTS });
			JOptionPane.showMessageDialog(null, jcb, "Select skill to track",
					JOptionPane.QUESTION_MESSAGE, null);
			SKILLS skill = (SKILLS) jcb.getSelectedItem();
			int level = Integer.parseInt(JOptionPane.showInputDialog(
					"Enter level: ").replaceAll("[^0-9]", ""));
			return null;
		}
	};

	abstract CProgressionCondition make();

}
