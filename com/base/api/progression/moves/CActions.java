package scripts.CombatAIO.com.base.api.progression.moves;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.types.enums.Food;

public enum CActions {

	ATTACK_STYLE_CHANGE {
		@Override
		CProgressionAction make() {
			int index = parse(JOptionPane
					.showInputDialog("Enter the index of the style you wish to switch to. Indexes start at 1"));
			if (index > 0 && index < 5)
				return new CAttackStyleChangeMove(index);
			else
				return null;
		}
	},
	FOOD_CHANGE {
		@Override
		CProgressionAction make() {
			JComboBox<Food> jcb = new JComboBox<Food>(Food.values());
			JOptionPane.showMessageDialog(null, jcb,
					"Select a food to change to", JOptionPane.QUESTION_MESSAGE,
					null);
			Food food = (Food) jcb.getSelectedItem();
			return new CChangeFoodMove(food);
		}
	},
	STOP_SCRIPT {
		@Override
		CProgressionAction make() {
			return new CStopScriptMove();
		}
	};

	abstract CProgressionAction make();

	private static int parse(String parse) {
		if (parse == null)
			return -1;
		return Integer.parseInt(parse.replaceAll("[^0-9]", ""));
	}

}
