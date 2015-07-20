package scripts.CombatAIO.com.base.api.walking.custom.conditions;

import javax.swing.JOptionPane;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.walking.custom.types.DCondition;

public enum DConditionMaker {

	Not_in_area {
		@Override
		public DCondition make() {
			int neX = Integer.parseInt(JOptionPane.showInputDialog(
					"Enter North East Tile x").replaceAll("[^0-9]", ""));
			int neY = Integer.parseInt(JOptionPane.showInputDialog(
					"Enter North east Tile y").replaceAll("[^0-9]", ""));
			int swX = Integer.parseInt(JOptionPane.showInputDialog(
					"Enter South West Tile x").replaceAll("[^0-9]", ""));
			int swY = Integer.parseInt(JOptionPane.showInputDialog(
					"Enter South West Tile y").replaceAll("[^0-9]", ""));
			return new DNotInArea(new RSArea(new RSTile(swX, swY), new RSTile(
					neX, neY)));
		}
	},
	Npc_is_on_screen {
		@Override
		public DCondition make() {
			int id = Integer.parseInt(JOptionPane.showInputDialog("NPC id")
					.replaceAll("[^0-9]", ""));
			return new DNPCIsOnScreenCondition(id);
		}
	},
	Object_is_on_screen {

		@Override
		public DCondition make() {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Object id")
					.replaceAll("[^0-9]", ""));
			return new DNPCIsOnScreenCondition(id);
		}

	};

	public abstract DCondition make();

	@Override
	public String toString() {
		return this.name().replaceFirst("_", " ");
	}

}
