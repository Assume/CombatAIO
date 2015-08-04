package scripts.CombatAIO.com.base.api.tasks.threads;

import java.util.Arrays;
import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.tasks.helper.Banker;
import scripts.CombatAIO.com.base.api.tasks.helper.CombatHelper;
import scripts.CombatAIO.com.base.api.tasks.helper.IngameWorldSwitcher;
import scripts.CombatAIO.com.base.api.tasks.helper.StaticTargetCalculator;
import scripts.CombatAIO.com.base.api.tasks.helper.scriptspecific.RockCrabsHelper;
import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Pauseable;
import scripts.CombatAIO.com.base.api.tasks.types.Threadable;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.CombatAIO.com.base.main.utils.ArrayUtil;
import scripts.api.scriptapi.paint.SkillData;

public class CombatTask extends Threadable implements Pauseable {

	private RSNPC current_target;
	private RSTile home_tile;
	private KillTracker kill_tracker;
	private int combat_distance;
	private RSNPC[] possible_monsters;
	private int[] monster_ids;

	private int world_hop_tolerance = -1;

	private long last_attack_time = System.currentTimeMillis();

	private CombatHelper helper;

	private boolean attack_monsters_already_in_combat;

	public CombatTask() {
		this(Arrays.asList(new PauseType[] {
				PauseType.COULD_INTERFERE_WITH_LOOTING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.possible_monsters = new RSNPC[0];
		this.kill_tracker = new KillTracker(this);
		this.helper = new CombatHelper(this);
		super.setName("COMBAT_THREAD");
	}

	private CombatTask(List<PauseType> pause_types) {
		super(pause_types);
	}

	public void initiate() {
		this.kill_tracker.start();
		SkillData.initiate();
	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning())
			fight();

	}

	public void fight() {
		if (Banker.shouldBank(this))
			Dispatcher.get().getBanker().bank(false);
		this.helper.runDefaultChecks();
		if (this.shouldChangeWorld() && !Player.getRSPlayer().isInCombat()) {
			this.helper.pickupCannon();
			IngameWorldSwitcher.switchToRandomWorld();
		}
		this.helper.safeSpotCheck();
		if (!Player.getRSPlayer().isInCombat()
				&& Player.getRSPlayer().getInteractingCharacter() == null)
			this.current_target = null;
		if (current_target == null) {
			this.executeNoTarget();
		} else {
			executeHasTarget();
		}
	}

	private void executeNoTarget() {
		this.helper.runCheckHasNoTarget();
		long time = Dispatcher.get().getABCUtil().DELAY_TRACKER.NEW_OBJECT_COMBAT
				.next();
		General.sleep(time);
		Dispatcher.get().getABCUtil().DELAY_TRACKER.NEW_OBJECT_COMBAT.reset();
		this.setMonsters(StaticTargetCalculator.calculate());
		fight(this.possible_monsters);
	}

	private void executeHasTarget() {
		RSCharacter interacting_char = Player.getRSPlayer()
				.getInteractingCharacter();
		if (interacting_char != null && interacting_char instanceof RSNPC) {
			RSNPC inter_rsnpc = (RSNPC) interacting_char;
			if (inter_rsnpc != current_target && inter_rsnpc.isValid())
				current_target = inter_rsnpc;
		}
		if (this.current_target != null && !this.current_target.isValid()) {
			this.current_target = null;
			this.setMonsters(StaticTargetCalculator.calculate());
			fight(this.possible_monsters);
		}
		this.last_attack_time = System.currentTimeMillis();
		this.helper.runHasTargetChecks();

		General.sleep(300);
	}

	private void fight(RSNPC[] monsters) {
		if ((monsters.length == 0 && NPCs.find(this.monster_ids).length >= 0 && Player
				.getPosition().distanceTo(home_tile) >= combat_distance)
				|| Timing.timeFromMark(this.last_attack_time) >= 10000) {
			if (Player.getPosition().distanceTo(home_tile) >= 5
					&& !Dispatcher.get().isRockCrabsPreset()) {
				new DPathNavigator().traverse(this.home_tile);
				this.last_attack_time = System.currentTimeMillis();
			} else
				RockCrabsHelper.wakeUpCrabs();
		}
		setTarget(monsters);
		if (!verifyTarget(this.current_target))
			return;
		if (this.helper.getSafeSpotTile() == null)
			moveToTarget(this.current_target);
		attackTarget(this.current_target);

	}

	public boolean verifyTarget(RSNPC current_target) {
		if (current_target == null)
			return false;
		return this.attack_monsters_already_in_combat
				|| (!current_target.isInCombat() && !StaticTargetCalculator
						.isBeingSplashed(current_target));

	}

	private void setTarget(RSNPC[] monsters) {
		if (monsters.length == 0)
			return;
		if (getAverageDistance(monsters) < 3)
			this.current_target = monsters[General.random(0,
					monsters.length - 1)];
		else
			this.current_target = monsters[0];
	}

	private void moveToTarget(RSNPC target) {
		if (!target.isOnScreen())
			Camera.turnToTile(target);
		if (!target.isOnScreen()
				|| (!this.helper.isRanging() && !PathFinding.canReach(target,
						false))) {
			if (PathFinding.canReach(target, false))
				Walking.walkTo(target);
			else
				new DPathNavigator().traverse(target);
		}
	}

	public boolean attackCurrentTarget() {
		if (this.current_target != null)
			return Clicking.click("Attack " + this.current_target.getName(),
					this.current_target);
		return false;
	}

	private void attackTarget(RSNPC target) {
		if (target == null)
			return;
		if (Clicking.click("Attack " + target.getName(), target)) {
			int distance = Player.getPosition().distanceTo(target);
			int sleep_time = General.random((int) ((distance / 3.5) * 1000),
					(int) ((distance / 2.5) * 1000));
			General.sleep(sleep_time);
		}
	}

	private int getAverageDistance(RSNPC[] npcs) {
		if (npcs.length == 0)
			return 0;
		int total = 0;
		for (RSNPC x : npcs)
			total += new DPathNavigator().findPath(x).length;
		return total / npcs.length;
	}

	public boolean shouldChangeWorld() {
		return this.world_hop_tolerance > 0
				&& Players.find(new Filter<RSPlayer>() {
					@Override
					public boolean accept(RSPlayer arg0) {
						return arg0.getPosition().distanceTo(home_tile) <= combat_distance;
					}
				}).length > this.world_hop_tolerance;
	}

	public void setMonsters(RSNPC[] possible_monsters) {
		this.possible_monsters = possible_monsters;
	}

	public void resetTarget() {
		this.current_target = null;
	}

	public Value<RSNPC> getCurrentTarget() {
		return new Value<RSNPC>(current_target);
	}

	public Value<RSTile> getHomeTile() {
		return new Value<RSTile>(home_tile);
	}

	public Value<Integer> getCombatRadius() {
		return new Value<Integer>(this.combat_distance);
	}

	public Value<Integer> getTotalKills() {
		return kill_tracker.getTotalKills();
	}

	public void setMonsterIDs(int... id) {
		this.monster_ids = id;
	}

	public Value<int[]> getMonsterIDs() {
		return new Value<int[]>(this.monster_ids);
	}

	public Value<Boolean> isRanging() {
		return new Value<Boolean>(this.helper.isRanging());
	}

	public Value<Prayer> getPrayer() {
		return new Value<Prayer>(this.helper.getPrayer());
	}

	public void setHomeTile(RSTile value) {
		this.home_tile = value;
	}

	public Value<RSNPC[]> getPossibleMonsters() {
		return new Value<RSNPC[]>(this.possible_monsters);
	}

	public void setSpecialAttackWeapon(Weapon weapon) {
		this.helper.setSpecialAttackWeapon(weapon);
	}

	public void setRanging(Boolean value) {
		this.helper.setIsRanging(value.booleanValue());
	}

	public void setPrayer(Prayer value) {
		this.helper.setPrayer(value);
	}

	public Value<Weapon> getSpecialAttackWeapon() {
		return new Value<Weapon>(this.helper.getSpecialAttackWeapon());
	}

	public void setUseGuthans(boolean use_guthans) {
		this.helper.setUseGuthans(use_guthans);
	}

	public Value<Boolean> getUseGuthans() {
		return new Value<Boolean>(this.helper.getUseGuthans());
	}

	public Value<int[]> getGuthansIDs() {
		return new Value<int[]>(ArrayUtil.combineArrays(
				CombatHelper.GUTHANS_BODY_IDS, CombatHelper.GUTHANS_HELM_IDS,
				CombatHelper.GUTHANS_LEGS_IDS,
				CombatHelper.GUTHANS_WARSPEAR_IDS));
	}

	public Value<Boolean> shouldFlicker() {
		return new Value<Boolean>(this.helper.getUseFlicker());
	}

	public void setUseFlicker(boolean b) {
		this.helper.setUseFlicker(b);
	}

	public void setCombatRadius(int value) {
		this.combat_distance = value;
	}

	public void setWorldHopTolerance(int value) {
		this.world_hop_tolerance = value;
	}

	public Value<Integer> getWorldHopTolerance() {
		return new Value<Integer>(this.world_hop_tolerance);
	}

	public void setSafeSpot(RSTile value) {
		this.helper.setSafeSpotTile(value);
	}

	public Value<RSTile> getSafeSpot() {
		return new Value<RSTile>(this.helper.getSafeSpotTile());
	}

	public Value<int[]> getArmorHolderIds() {
		if (this.helper.getArmorHolder() == null)
			return new Value<int[]>(new int[0]);
		return new Value<int[]>(this.helper.getArmorHolder().getIDs());
	}

	public Value<Integer> getAmmoId() {
		return new Value<Integer>(this.helper.getAmmoId());
	}

	public boolean isUsingProtectionPrayer() {
		return this.helper.isUsingProtectionPrayer();
	}

	public void setAmmo() {
		this.helper.setAmmo();
	}

	public void pickupCannon() {
		this.helper.pickupCannon();
	}

	public boolean isUsingCannon() {
		return this.helper.getUseCannon();
	}

	public void setUseCannon(boolean value) {
		this.helper.setUseCannon(value);
	}

	public void setAttackMonstersInCombat(boolean value) {
		this.attack_monsters_already_in_combat = value;
	}

	public Value<Boolean> getAttackMonstersInCombat() {
		return new Value<Boolean>(this.attack_monsters_already_in_combat);
	}

	public void setCannonTile(RSTile value) {
		this.helper.setCannonTile(value);

	}

	public Value<Boolean> getUseCannon() {
		return new Value<Boolean>(this.helper.getUseCannon());
	}

	public Value<RSTile> getCannonTile() {
		return new Value<RSTile>(this.helper.getCannonTile());
	}

	public void setCannonDecayed() {
		this.helper.setPickupCannon();
	}

	public Value<Boolean> getBuryBones() {
		return this.helper.getBuryBones();
	}

	public void setBuryBones(boolean value) {
		this.helper.setBuryBones(value);

	}

}
