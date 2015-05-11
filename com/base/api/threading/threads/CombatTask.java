package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.Arrays;
import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.helper.Banker;
import scripts.CombatAIO.com.base.api.threading.helper.StaticTargetCalculator;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.enums.SkillData;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;

public class CombatTask extends Threadable implements Runnable, Pauseable {

	private RSNPC current_target;
	private RSTile home_tile;
	private KillTracker kill_tracker;
	private int combat_distance;
	private RSNPC[] possible_monsters;
	private String[] monster_names;
	private boolean isRanging = false;
	private Prayer prayer;
	private boolean flicker;
	private Prayer flicker_prayer;
	private Weapon weapon = Weapon.ABYSSAL_WHIP;

	public CombatTask() {
		this(Arrays.asList(new PauseType[] {
				PauseType.NON_ESSENTIAL_TO_BANKING,
				PauseType.COULD_INTERFERE_WITH_LOOTING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.possible_monsters = new RSNPC[0];
		this.kill_tracker = new KillTracker(this);
		this.home_tile = Player.getPosition();
	}

	private CombatTask(List<PauseType> pause_types) {
		super(pause_types);
	}

	public void initiate() {
		this.kill_tracker.start();
		SkillData.initiate();
	}

	public void fight() {
		System.out.println("In start of method fight");
		if (Banker.shouldBank())
			Banker.bank(false);
		if (!Player.getRSPlayer().isInCombat()
				&& Player.getRSPlayer().getInteractingCharacter() == null)
			this.current_target = null;
		if (current_target == null) {
			General.sleep(Dispatcher.get().getABCUtil().DELAY_TRACKER.NEW_OBJECT_COMBAT
					.next());
			Dispatcher.get().getABCUtil().DELAY_TRACKER.NEW_OBJECT_COMBAT
					.reset();
			StaticTargetCalculator.set(this);
			fight(this.possible_monsters);
		} else {
			if (Combat.getAttackingEntities().length == 0
					&& this.current_target != null)
				StaticTargetCalculator.set(this);
			if (this.flicker)
				flicker(this.flicker_prayer);
			General.sleep(300);
		}
	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning()) {
			fight();
		}
	}

	private void fight(RSNPC[] monsters) {
		System.out.println("In method fight, total possible targets, "
				+ monsters.length);
		if (monsters.length == 0 && NPCs.find(this.monster_names).length >= 0)
			WebWalking.walkTo(this.home_tile);
		if (monsters.length == 0)
			return;
		if (getAverageDistance(monsters) <= 3)
			this.current_target = monsters[General.random(0,
					monsters.length - 1)];
		else
			this.current_target = monsters[0];
		System.out.println("In method fight, target has been found: "
				+ this.current_target);
		moveToTarget(this.current_target);
		attackTarget(this.current_target);

	}

	private void moveToTarget(RSNPC target) {
		if (!target.isOnScreen())
			Camera.turnToTile(target);
		if (Player.getPosition().distanceTo(target) > 7 && !target.isOnScreen())
			new DPathNavigator().traverse(target);
		while (Player.isMoving())
			General.sleep(50);
	}

	public void attackCurrentTarget() {
		if (this.current_target != null) {
			Clicking.click("Attack " + this.current_target.getName(),
					this.current_target);
		}
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

	private void flicker(Prayer prayer) {
		if (Player.getAnimation() == this.weapon.getAnimationID()
				&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 0) {
			General.sleep(this.weapon.getAttackSpeed());
			prayer.flicker();
		}
	}

	public void setMonsters(RSNPC[] possible_monsters) {
		this.possible_monsters = possible_monsters;
	}

	public Value<RSNPC> getCurrentTarget() {
		return new Value<RSNPC>(current_target);
	}

	public Value<RSTile> getHomeTile() {
		return new Value<RSTile>(home_tile);
	}

	public Value<Integer> getCombatDistance() {
		return new Value<Integer>(this.combat_distance);
	}

	public Value<Integer> getTotalKills() {
		return kill_tracker.getTotalKills();
	}

	public Value<?> getNextTarget() {
		return null;
	}

	public void setMonsterNames(String... names) {
		this.monster_names = names;
	}

	public Value<String[]> getMonsterNames() {
		return new Value<String[]>(this.monster_names);
	}

	public Value<Boolean> isRanging() {
		return new Value<Boolean>(this.isRanging);
	}

	public Value<String> getFirstMonsterName() {
		if (this.monster_names.length == 0)
			return null;
		return new Value<String>(this.monster_names[0]);
	}

	public void resetTarget() {
		this.current_target = null;

	}

	public Value<Prayer> getPrayer() {
		return new Value<Prayer>(this.prayer);
	}

	public void setHomeTile(RSTile value) {
		this.home_tile = value;

	}

	public void setRanging(Boolean value) {
		this.isRanging = value.booleanValue();
	}

	public void setPrayer(Prayer value) {
		this.prayer = value;

	}
}
