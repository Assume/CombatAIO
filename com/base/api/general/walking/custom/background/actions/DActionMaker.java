package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.magic.books.NormalSpell;

public enum DActionMaker {

	DTeleport {
		@Override
		public DAction make() {
			JComboBox<NormalSpell> box = new JComboBox<NormalSpell>(
					NormalSpell.values());
			JOptionPane.showMessageDialog(null, box, "Select a skill",
					JOptionPane.QUESTION_MESSAGE);
			NormalSpell k = (NormalSpell) box.getSelectedItem();
			return new DTeleport(k);
		}
	},
	DUseItem {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter item id"));
			String action = JOptionPane.showInputDialog("Enter action");
			return new DUseItem(action, x);
		}
	},
	DWalkToTile {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter x cord"));
			int y = getInt(JOptionPane.showInputDialog("Enter y cord"));
			return new DWalkToTile(x, y);
		}
	};

	public abstract DAction make();

	private static int getInt(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9-]", ""));
	}

	@Override
	public String toString() {
		return this.name().replaceFirst("D", "");
	}
}
