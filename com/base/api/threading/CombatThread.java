package scripts.CombatAIO.com.base.api.threading;

import java.util.Arrays;
import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
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
import scripts.CombatAIO.com.base.api.threading.types.subtype.StringValue;

public class CombatThread extends Threadable implements Runnable, Pauseable {

	private RSNPC current_target;
	private Positionable home_tile;
	private KillTracker kill_tracker;
	private int combat_distance;
	private RSNPC[] possible_monsters;
	private String[] monster_names;
	private boolean isRanging;
	private CombatCalculationThread calculation_thread;

	public CombatThread(CombatCalculationThread calculation_thread,
			String... monster_names) {
		this(Arrays.asList(new PauseType[] {
				PauseType.NON_ESSENTIAL_TO_BANKING,
				PauseType.COULD_INTERFERE_WITH_LOOTING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.monster_names = monster_names;
		this.possible_monsters = new RSNPC[0];
		this.kill_tracker = new KillTracker(this);
		this.calculation_thread = calculation_thread;
	}

	private CombatThread(List<PauseType> pause_types) {
		super(pause_types);
	}

	@Override
	public void run() {
		this.kill_tracker.start();
		while (Dispatcher.get().isRunning()) {
			if (!Player.getRSPlayer().isInCombat()
					&& Player.getRSPlayer().getInteractingCharacter() == null)
				this.current_target = null;
			if (current_target == null)
				fight(this.possible_monsters);
			else
				General.sleep(300);
		}
	}

	private void fight(RSNPC[] monsters) {
		if (monsters.length == 0)
			return;
		if (getAverageDistance(monsters) <= 3)
			this.current_target = monsters[General.random(0,
					monsters.length - 1)];
		else
			this.current_target = monsters[0];
		moveToTarget(this.current_target);
		attackTarget(this.current_target);

	}

	private void moveToTarget(RSNPC target) {
		if (!target.isOnScreen())
			Camera.turnToTile(target);
		if (Player.getPosition().distanceTo(target) > 7 && !target.isOnScreen())
			Walking.walkTo(target);
		while (Player.isMoving())
			General.sleep(50);
	}

	private void attackTarget(RSNPC target) {
		if (target == null
				|| (target.isInCombat() && !target.isInteractingWithMe()))
			return;
		Clicking.click("Attack " + target.getName(), target);
		int distance = Player.getPosition().distanceTo(target);
		int sleep_time = General.random((int) ((distance / 3.5) * 1000),
				(int) ((distance / 2.5) * 1000));
		General.sleep(sleep_time);
	}

	private int getAverageDistance(RSNPC[] npcs) {
		int total = 0;
		for (RSNPC x : npcs)
			total += Player.getPosition().distanceTo(x);
		return total / npcs.length;
	}

	protected void setMonsters(RSNPC[] possible_monsters) {
		this.possible_monsters = possible_monsters;
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

	public Value<?> getFirstMonsterName() {
		if (this.monster_names.length == 0)
			return null;
		return new StringValue(this.monster_names[0]);
	}

	public void resetTarget() {
		this.current_target = null;

	}
}
