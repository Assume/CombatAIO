package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.Arrays;
import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Options;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.CEquipment;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.helper.Banker;
import scripts.CombatAIO.com.base.api.threading.helper.IngameWorldSwitcher;
import scripts.CombatAIO.com.base.api.threading.helper.StaticTargetCalculator;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.ArmorHolder;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.SkillData;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.main.GenericMethods;

public class CombatTask extends Threadable implements Runnable, Pauseable {

	private static int[] guthans_helm_ids = { 4724, 4904, 4905, 4906, 4907 };
	private static int[] guthans_legs_ids = { 4730, 4922, 4923, 4924, 4925 };
	private static int[] guthans_body_ids = { 4728, 4916, 4917, 4918, 4919 };
	private static int[] guthans_warspear_ids = { 4726, 4910, 4911, 4912, 4913 };

	public static int trash_ids[] = { 117, 9978, 10115, 10125, 10127, 229, 592 };

	private RSNPC current_target;
	private RSTile home_tile;
	private KillTracker kill_tracker;
	private int combat_distance;
	private RSNPC[] possible_monsters;
	private int[] monster_ids;
	private boolean is_ranging = false;
	private Prayer prayer = Prayer.NONE;
	private boolean flicker;
	private Weapon weapon = Weapon.NONE;
	private Weapon special_attack_weapon = Weapon.NONE;
	private int ammo_id = -1;
	private int knife_id = -1;
	private int world_hop_tolerance = -1;
	private RSTile safe_spot_tile;

	private boolean use_guthans = false;
	private ArmorHolder armor_holder;

	private long last_attack_time = System.currentTimeMillis();

	public CombatTask() {
		this(Arrays.asList(new PauseType[] {
				PauseType.COULD_INTERFERE_WITH_LOOTING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.possible_monsters = new RSNPC[0];
		this.kill_tracker = new KillTracker(this);
		this.home_tile = Player.getPosition();
		this.armor_holder = null;
		super.setName("COMBAT_THREAD");
	}

	private CombatTask(List<PauseType> pause_types) {
		super(pause_types);
	}

	public void initiate() {
		this.kill_tracker.start();
		SkillData.initiate();
	}

	public void fight() {
		if (Banker.shouldBank(this))
			Dispatcher.get().bank(false);
		checkRun();
		checkUse();
		usePrayer(this.prayer);
		if (this.shouldChangeWorld() && !Player.getRSPlayer().isInCombat())
			IngameWorldSwitcher.switchToRandomWorld();
		this.safeSpotCheck();
		if (!Player.getRSPlayer().isInCombat()
				&& Player.getRSPlayer().getInteractingCharacter() == null)
			this.current_target = null;
		if (current_target == null) {
			dropTrash();
			equipAmmo();
			long time = Dispatcher.get().getABCUtil().DELAY_TRACKER.NEW_OBJECT_COMBAT
					.next();
			General.sleep(time);
			Dispatcher.get().getABCUtil().DELAY_TRACKER.NEW_OBJECT_COMBAT
					.reset();
			this.setMonsters(StaticTargetCalculator.calculate());
			fight(this.possible_monsters);
		} else {
			RSCharacter interacting_char = Player.getRSPlayer()
					.getInteractingCharacter();
			if (interacting_char instanceof RSNPC) {
				RSNPC inter_rsnpc = (RSNPC) interacting_char;
				if (inter_rsnpc != current_target && inter_rsnpc.isValid())
					current_target = inter_rsnpc;
			}
			if (!this.current_target.isValid()) {
				this.current_target = null;
				this.setMonsters(StaticTargetCalculator.calculate());
				fight(this.possible_monsters);
			}
			this.last_attack_time = System.currentTimeMillis();
			if (this.flicker)
				flicker(this.prayer);
			if (this.armor_holder == null)
				this.useSpecialAttack();
			if (this.use_guthans)
				useGuthans();
			General.sleep(300);

		}
	}

	private void checkRun() {
		if (Game.getRunEnergy() >= Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_RUN_AT
				.next() && !Game.isRunOn()) {
			Options.setRunOn(true);
			Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_RUN_AT.reset();
		}
	}

	private void safeSpotCheck() {
		if (this.safe_spot_tile != null)
			if (!Player.getPosition().equals(safe_spot_tile))
				Walking.walkScreenPath(Walking
						.generateStraightScreenPath(safe_spot_tile));
	}

	private void usePrayer(Prayer flicker_prayer) {
		if (!flicker_prayer.isActivated()
				&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 0)
			flicker_prayer.activate();
	}

	private void useGuthans() {
		if (this.armor_holder == null && Combat.getHPRatio() <= 50
				&& !isDegradedGuthansInInventory()) {
			this.armor_holder = new ArmorHolder();
			equipGuthans();
			return;
		}
		if (this.armor_holder != null && Combat.getHPRatio() >= 90) {
			this.armor_holder.equip();
			this.armor_holder = null;
		}
	}

	private void equipGuthans() {
		CEquipment.equip(new int[][] { guthans_helm_ids, guthans_body_ids,
				guthans_legs_ids, guthans_warspear_ids });
	}

	private boolean isGuthanDegraded() {
		return !CEquipment.isEquiped(guthans_body_ids[4] + 1)
				&& !CEquipment.isEquiped(guthans_helm_ids[4] + 1)
				&& !CEquipment.isEquiped(guthans_legs_ids[4] + 1)
				&& !CEquipment.isEquiped(guthans_warspear_ids[4] + 1);
	}

	private boolean isDegradedGuthansInInventory() {
		return Inventory.find(guthans_body_ids[4] + 1, guthans_helm_ids[4] + 1,
				guthans_legs_ids[4] + 1, guthans_warspear_ids[4] + 1).length > 0;
	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning())
			fight();
	}

	private void fight(RSNPC[] monsters) {
		if ((monsters.length == 0 && NPCs.find(this.monster_ids).length >= 0 && Player
				.getPosition().distanceTo(home_tile) >= combat_distance)
				|| Timing.timeFromMark(this.last_attack_time) >= 10000) {
			if (Player.getPosition().distanceTo(home_tile) >= 5) {
				new DPathNavigator().traverse(this.home_tile);
				this.last_attack_time = System.currentTimeMillis();
			}
		}
		if (monsters.length == 0)
			return;
		if (getAverageDistance(monsters) <= 4)
			this.current_target = monsters[General.random(0,
					monsters.length - 1)];
		else
			this.current_target = monsters[0];
		if (!StaticTargetCalculator.verifyTarget(this.current_target))
			return;
		if (this.safe_spot_tile == null)
			moveToTarget(this.current_target);
		attackTarget(this.current_target);

	}

	private void moveToTarget(RSNPC target) {
		if (!target.isOnScreen())
			Camera.turnToTile(target);
		if (Player.getPosition().distanceTo(target) >= 7
				|| !target.isOnScreen()
				|| (!this.is_ranging && !PathFinding.canReach(target, false))) {
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
		if (target == null
				|| (target.isInCombat() && !target.isInteractingWithMe()))
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

	private void flicker(Prayer prayer) {
		if (Player.getAnimation() == this.weapon.getAnimationID()
				&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 0) {
			General.sleep(this.weapon.getAttackSpeed());
			prayer.flicker();
		}
	}

	private void useSpecialAttack() {
		if (this.special_attack_weapon == Weapon.NONE)
			return;
		if (getSpecialPercent() >= this.special_attack_weapon.getSpecialUsage()
				&& getTargetHPPercent() >= 30) {
			int wep_id = -1;
			int shield_id = -1;
			RSItem temp = Equipment.getItem(SLOTS.WEAPON);
			if (temp != null)
				wep_id = temp.getID();
			RSItem temp2 = Equipment.getItem(SLOTS.SHIELD);
			if (temp2 != null)
				shield_id = temp2.getID();
			this.special_attack_weapon.useSpecial(this, wep_id, shield_id);
		}

	}

	private double getTargetHPPercent() {
		if (this.current_target == null)
			return -1;
		int health = this.current_target.getMaxHealth();
		if (health == 0)
			return 0;
		return ((this.current_target.getHealth() / health) * 100);
	}

	private int getSpecialPercent() {
		return Game.getSetting(300) / 10;
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

	public Value<Integer> getCombatRadius() {
		return new Value<Integer>(this.combat_distance);
	}

	public Value<Integer> getTotalKills() {
		return kill_tracker.getTotalKills();
	}

	public Value<?> getNextTarget() {
		return null;
	}

	public void setMonsterIDs(int... id) {
		this.monster_ids = id;
	}

	public Value<int[]> getMonsterIDs() {
		return new Value<int[]>(this.monster_ids);
	}

	public Value<Boolean> isRanging() {
		return new Value<Boolean>(this.is_ranging);
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

	public Value<RSNPC[]> getPossibleMonsters() {
		return new Value<RSNPC[]>(this.possible_monsters);
	}

	public void setSpecialAttackWeapon(Weapon weapon) {
		this.special_attack_weapon = weapon;
	}

	public void setRanging(Boolean value) {
		this.is_ranging = value.booleanValue();
	}

	public void setPrayer(Prayer value) {
		this.prayer = value;

	}

	public Value<Weapon> getSpecialAttackWeapon() {
		return new Value<Weapon>(this.special_attack_weapon);
	}

	public void setUseGuthans(boolean use_guthans) {
		this.use_guthans = use_guthans;
	}

	public boolean getUseGuthans() {
		return this.use_guthans;
	}

	public int[] getGuthansIDs() {
		return GenericMethods.combineArrays(guthans_body_ids, guthans_helm_ids,
				guthans_legs_ids, guthans_warspear_ids);
	}

	public Value<Boolean> shouldFlicker() {
		return new Value<Boolean>(this.flicker);
	}

	public void setUseFlicker(boolean b) {
		this.flicker = b;
	}

	private void equipAmmo() {
		RSItem[] ammo = Inventory.find(ammo_id, knife_id);
		if (ammo.length > 0)
			if (ammo[0].getStack() >= Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_EAT_AT
					.next()) {
				Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_EAT_AT.reset();
				ammo[0].click("Wield");
			}
	}

	public void setAmmo() {
		if (this.is_ranging) {
			RSItem ammo = Equipment.getItem(SLOTS.ARROW);
			RSItem knife = Equipment.getItem(SLOTS.WEAPON);
			if (ammo != null && ammo.getStack() > 1) {
				this.ammo_id = ammo.getID();
				RSItemDefinition ammo_def = ammo.getDefinition();
				if (ammo_def != null)
					Dispatcher.get().set(
							ValueType.LOOT_ITEM_NAMES,
							new Value<String[]>(new String[] { ammo_def
									.getName() }));
			}
			if (knife != null && knife.getStack() > 1) {
				this.knife_id = knife.getID();
				RSItemDefinition knife_def = ammo.getDefinition();
				if (knife_def != null)
					Dispatcher.get().set(
							ValueType.LOOT_ITEM_NAMES,
							new Value<String[]>(new String[] { knife_def
									.getName() }));
			}

		}

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
		this.safe_spot_tile = value;
	}

	public Value<RSTile> getSafeSpot() {
		return new Value<RSTile>(this.safe_spot_tile);
	}

	public Value<int[]> getArmorHolderIDs() {
		if (this.armor_holder == null)
			return new Value<int[]>(new int[0]);
		return new Value<int[]>(this.armor_holder.getIDs());
	}

	private void checkUse() {
		if (Game.getUptext().contains("->"))
			Mouse.clickBox(10, 446, 480, 480, 1);
	}

	private void dropTrash() {
		RSItem[] items = Inventory.find(trash_ids);
		if (items.length == 0)
			return;
		for (RSItem x : items) {
			x.click("Drop");
			General.sleep(200, 400);
		}
	}

	public boolean isUsingProtectionPrayer() {
		return prayer == Prayer.PROTECT_FROM_MAGIC
				|| prayer == Prayer.PROTECT_FROM_MELEE
				|| prayer == Prayer.PROTECT_FROM_MISSILES;
	}
}
