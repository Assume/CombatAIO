package scripts.CombatAIO.com.base.api.threading;

import java.util.Arrays;
import java.util.List;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Dispatchable;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
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
	private List<PauseType> pause_types;

	public CombatThread(String... npc_names) {
		this.npc_names = npc_names;
		this.kill_tracker = null; // TODO fix later
		this.pause_types = Arrays
				.asList(new PauseType[] { PauseType.NON_ESSENTIAL_TO_BANKING });
	}

	@Override
	public void run() {
		/*
		 * while(true) check x,y,z variables dispatch calculation threads when
		 * (ie on near death) needed
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
