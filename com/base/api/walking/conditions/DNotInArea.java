package scripts.CombatAIO.com.base.api.walking.conditions;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;

import scripts.CombatAIO.com.base.api.walking.types.DCondition;

public class DNotInArea implements DCondition {

	private RSArea area;

	public DNotInArea(RSArea rsArea) {
		this.area = rsArea;
	}

	private static final long serialVersionUID = -206863847275591963L;

	@Override
	public boolean validate() {
		return !this.area.contains(Player.getRSPlayer().getPosition());
	}

	@Override
	public String toString() {
		return "not in area";
	}
	
}
