package scripts.CombatAIO.com.base.api.walking.actions;

import javax.swing.JOptionPane;

import scripts.CombatAIO.com.base.api.walking.types.DAction;

public enum DActionMaker {
	/*
	 * Teleport {
	 * 
	 * @Override public DAction make() { JComboBox<NormalSpell> box = new
	 * JComboBox<NormalSpell>( NormalSpell.values());
	 * JOptionPane.showMessageDialog(null, box, "Select a skill",
	 * JOptionPane.QUESTION_MESSAGE); NormalSpell k = (NormalSpell)
	 * box.getSelectedItem(); return new DTeleport(k); } }
	 */
	Click_item {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter item id"));
			String action = JOptionPane.showInputDialog("Enter action");
			return new DUseItem(action, x);
		}
	},
	Click_object {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter object id"));
			String action = JOptionPane.showInputDialog("Enter action");
			return new DClickObject(x, action);
		}
	},
	Walk_to_tile {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter x cord"));
			int y = getInt(JOptionPane.showInputDialog("Enter y cord"));
			return new DWalkToTile(x, y);
		}
	},
	Walk_to_object {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter object id"));
			return new DWalkToObject(x);
		}
	},
	Walk_to_npc {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter npc id"));
			return new DWalkToNPC(x);
		}
	},
	Sleep {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane
					.showInputDialog("Enter time to sleep (milliseconds)"));
			return new DSleep(x);
		}
	},
	Wait_until_stopped {
		@Override
		public DAction make() {
			return new DWaitUntilStoppedAction();
		}
	},
	Wait_until_not_animating {

		@Override
		public DAction make() {
			return new DWaitUntilNotAnimating();
		}
	},
	Wait_until_object_on_screen {
		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter object id"));
			return new DWaitUntilObjectOnScreen(x);
		}
	},
	Wait_until_npc_on_screen {

		@Override
		public DAction make() {
			int x = getInt(JOptionPane.showInputDialog("Enter npc id"));
			return new DWaitUntilNPCOnScreen(x);
		}
	};

	public abstract DAction make();

	private static int getInt(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9-]", ""));
	}

	@Override
	public String toString() {
		return this.name().replaceFirst("_", " ");
	}
}
