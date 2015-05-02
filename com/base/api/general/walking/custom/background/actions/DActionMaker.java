package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.magic.books.NormalSpell;

public enum DActionMaker {

	DChangeAttackStyle {
		@Override
		public DAction make() {
			String style = JOptionPane.showInputDialog("Enter style name");
			return new DChangeAttackStyle(style);
		}
	},
	DChangeWeapon {
		@Override
		public DAction make() {
			int id = getInt(JOptionPane.showInputDialog("Enter id: "));
			return new DChangeWeapon(id);
		}
	},
	DCloseClient {
		@Override
		public DAction make() {
			return new DCloseClientAction();
		}
	},
	DLogout {
		@Override
		public DAction make() {
			return new DLogout();
		}
	},
	DSleep {
		@Override
		public DAction make() {
			int time = getInt(JOptionPane
					.showInputDialog("Enter amount of time to sleep: "));
			return new DSleep(time);
		}
	},
	DStopScriptAction {
		@Override
		public DAction make() {
			return new DStopScriptAction();
		}
	},
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
	},
	DWorldHop {
		@Override
		public DAction make() {
			int world = getInt(JOptionPane
					.showInputDialog("Enter world. If you want to hop to previous world, enter -1"));
			return new DWorldHop(world);

		}
	},
	DWriteToDebug {
		@Override
		public DAction make() {
			String what = JOptionPane.showInputDialog("Enter what to write");
			return new DWriteToDebug(what);
		}
	},
	DWriteToNotepad {
		@Override
		public DAction make() {
			String path = new JFileChooser().getSelectedFile()
					.getAbsolutePath();
			String what = JOptionPane.showInputDialog("Enter text to write");
			return new DWriteToNotepad(path, what);
		}
	},
	DTrade {
		@Override
		public DAction make() {
			List<Integer> ids = new ArrayList<Integer>();
			List<Integer> amount = new ArrayList<Integer>();
			while (true) {
				int id;
				int am;
				String text = JOptionPane
						.showInputDialog("Enter id followed by a comma then the amount ex: 192,12 Enter -1 to stop");
				if (text.equalsIgnoreCase("-1"))
					break;
				id = getInt(text.split(",")[0]);
				am = getInt(text.split(",")[1]);
				ids.add(id);
				amount.add(am);
			}
			String name = JOptionPane
					.showInputDialog("Enter player name to trade");
			return new DTrade(name, ids.toArray(new Integer[ids.size()]),
					amount.toArray(new Integer[amount.size()]));
		}
	},
	DStopBackgroundScript {
		@Override
		public DAction make() {
			return new DStopBackgroundScript();
		}
	},
	DKillLine {
		@Override
		public DAction make() {
			return new DKillLine();
		}
	},
	DDepositAll() {

		@Override
		public DAction make() {
			return new DDepositAll();
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
