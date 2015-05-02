package scripts.CombatAIO.com.base.api.general.walking.custom.background.conditions;

import javax.swing.JOptionPane;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DCondition;

public enum DConditionMaker {

	DNotInArea {
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
			return new DNotInArea(new RSArea(swX, swY, neX, neY));
		}
	};

	public abstract DCondition make();

	private static int getInt(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9-]", ""));
	}

	@Override
	public String toString() {
		return this.name().replaceFirst("D", "");
	}

}
