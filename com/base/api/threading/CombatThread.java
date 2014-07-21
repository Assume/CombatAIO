package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Dispatchable;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.PositionableValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.RSNPCValue;

public class CombatThread implements Runnable, Pauseable, Dispatchable {

	private String[] npc_names;
	private RSNPC current_target;
	private Positionable home_tile;
	private KillTracker kill_tracker;
	private int combat_distance;
	private RSNPC[] possible_monsters;

	public CombatThread(String... npc_names) {
		this.npc_names = npc_names;
		this.kill_tracker = null; // TODO fix later
	}

	@Override
	public void run() {
		/*
		 * while(true) check x,y,z variables dispatch calculation threads when
		 * needed
		 */
	}

	@Override
	public void pause() {
		this.pause();
	}

	protected void setMonsters(RSNPC[] possible_monsters) {
		this.possible_monsters = possible_monsters;
	}

	public boolean shouldLoot() {
		// TODO
		return false;
	}

	public Value<?> getCurrentTarget() {
		return new RSNPCValue(current_target);
	}

	public Value<?> getHomeTile() {
		return new PositionableValue(home_tile);
	}

	public Value<?> getCombatDistance() {
		return new IntegerValue(this.combat_distance);
	}

	public Value<?> getTotalKills() {
		return kill_tracker.getTotalKills();
	}

	public Value<?> getNextTarget() {
		return null;
	}

}
