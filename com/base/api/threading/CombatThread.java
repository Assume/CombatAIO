package scripts.CombatAIO.com.base.api.threading;

import java.util.Arrays;
import java.util.List;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.BooleanValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.PositionableValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.RSNPCValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.StringArrayValue;

public class CombatThread extends Threadable implements Runnable, Pauseable {

	private String[] npc_names;
	private RSNPC current_target;
	private Positionable home_tile;
	private KillTracker kill_tracker;
	private int combat_distance;
	private RSNPC[] possible_monsters;
	private String[] monster_names;
	private boolean isRanging;

	public CombatThread(String... npc_names) {
		this(Arrays.asList(new PauseType[] {
				PauseType.NON_ESSENTIAL_TO_BANKING,
				PauseType.COULD_INTERFERE_WITH_LOOTING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.npc_names = npc_names;
		this.kill_tracker = null; // TODO fix later
	}

	private CombatThread(List<PauseType> pause_types) {
		super(pause_types);
	}

	@Override
	public void run() {
		/*
		 * while(true) check x,y,z variables dispatch calculation threads when
		 * (ie on near death) needed
		 */
	}

	protected void setMonsters(RSNPC[] possible_monsters) {
		this.possible_monsters = possible_monsters;
	}

	public boolean shouldLoot() {
		// TODO this returns true if the LootThread should execute it's loot
		// cycle and pause the combat cycle
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

	public Value<?> getMonsterNames() {
		return new StringArrayValue(this.monster_names);
	}

	public Value<?> isRanging() {
		return new BooleanValue(this.isRanging);
	}
}
