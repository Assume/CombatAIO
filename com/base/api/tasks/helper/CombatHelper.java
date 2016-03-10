package scripts.CombatAIO.com.base.api.tasks.helper;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.tasks.threads.CombatTask;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.ArmorHolder;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.api.walking.custom.types.CEquipment;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.CombatAIO.com.base.main.utils.AntiBan;
import scripts.CombatAIO.com.base.main.utils.Logger;

public class CombatHelper {

	private static final int CANNON_BALL_AMOUNT_INDEX = 3;

	private static final int CANNON_IS_FIRING_INDEX = 1;

	public static final int[] GUTHANS_HELM_IDS = { 4724, 4904, 4905, 4906, 4907 };
	public static final int[] GUTHANS_BODY_IDS = { 4728, 4916, 4917, 4918, 4919 };
	public static final int[] GUTHANS_LEGS_IDS = { 4730, 4922, 4923, 4924, 4925 };
	public static final int[] GUTHANS_WARSPEAR_IDS = { 4726, 4910, 4911, 4912,
			4913 };

	public static final int TRASH_ITEM_IDS[] = { 9978, 10115, 10125, 10127,
			229, 592 };

	public static final int[] CANNON_IDS = { 6, 7, 8, 10, 12 };

	public static final int CANNON_BALL_ID = 2;

	private static final int[] BONES_IDS = { 526, 536, 532 };

	private CombatTask combat_task;

	private ArmorHolder armor_holder;
	private RSTile safe_spot_tile;

	private int ammo_id = -4;
	private int knife_id = -4;

	private boolean is_ranging = false;
	private boolean flicker = false;
	private boolean use_guthans = false;
	private boolean use_cannon = false;

	private int fill_cannon_at;

	private Prayer prayer = Prayer.NONE;
	private Weapon weapon = Weapon.NONE;
	private Weapon special_attack_weapon = Weapon.NONE;
	private RSTile cannon_location;

	private boolean pick_up_cannon = false;

	private boolean bury_bones;

	public CombatHelper(CombatTask task) {
		this.combat_task = task;
		this.armor_holder = null;
		this.fill_cannon_at = General.random(0, 10);
		this.cannon_location = null;
	}

	public void checkRun() {
		AntiBan.activateRun();
	}

	public void buryBones() {
		if (!this.bury_bones)
			return;
		RSItem[] bones = Inventory.find(BONES_IDS);
		if (bones.length == 0)
			return;
		for (RSItem x : bones) {
			x.click("Bury");
			General.sleep(800, 1500);
		}
	}

	public void safeSpotCheck() {
		if (this.safe_spot_tile != null)
			if (!Player.getPosition().equals(safe_spot_tile))
				Walking.walkScreenPath(Walking
						.generateStraightScreenPath(safe_spot_tile));
	}

	public void setupCannon() {
		if (this.use_cannon && Inventory.find(CANNON_IDS).length == 4
				&& Inventory.find(CANNON_BALL_ID).length > 0) {
			if (!Player.getPosition().equals(cannon_location)) {
				Walking.walkTo(cannon_location);
				while (Player.isMoving())
					General.sleep(10);
			}
			RSItem[] cannon_base = Inventory.find(CANNON_IDS[0]);
			if (cannon_base.length == 0)
				return;
			cannon_base[0].click("Set-up");
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return Inventory.find(CANNON_IDS).length == 0;
				}
			}, General.random(10000, 12000));
			General.sleep(800, 1500);
			if (Inventory.find(CANNON_IDS).length != 0)
				pickupCannon();
			else
				fireCannon();
		}
	}

	private void fireCannon() {
		if (!this.use_cannon)
			return;
		Logger.getLogger().print(Logger.SCRIPTER_ONLY, "calling  fireCannon");
		if (Inventory.find(CANNON_BALL_ID).length == 0) {
			this.pickupCannon();
			return;
		}
		if (Game.getSetting(CANNON_IS_FIRING_INDEX) == 0) {
			Logger.getLogger().print(Logger.SCRIPTER_ONLY,
					"calling clickCannon(Fire)");
			clickCannon("Fire ");
		} else if (Game.getSetting(CANNON_BALL_AMOUNT_INDEX) <= this.fill_cannon_at) {
			Logger.getLogger().print(Logger.SCRIPTER_ONLY,
					"calling clickCannon(Fire)");
			clickCannon("Fire ");
			this.fill_cannon_at = General.random(0, 10);
		}
	}

	private void clickCannon(String option) {
		RSObject[] obj = Objects.find(25, CANNON_IDS);
		if (obj.length == 0)
			return;
		obj = sortCannon(obj);
		if (!obj[0].isOnScreen())
			Camera.turnToTile(obj[0]);
		if (!obj[0].isOnScreen())
			new DPathNavigator().traverse(obj[0]);
		Clicking.click(option, obj[0]);
		while (Player.isMoving())
			General.sleep(25);
	}

	public RSObject[] sortCannon(RSObject[] obj) {
		int j;
		boolean flag = true;
		RSObject temp;
		while (flag) {
			for (j = 0; j < obj.length - 1; j++) {
				flag = false;
				if (obj[j].getPosition().distanceTo(cannon_location) < obj[j + 1]
						.getPosition().distanceTo(cannon_location)) {
					temp = obj[j];
					obj[j] = obj[j + 1];
					obj[j + 1] = temp;
					flag = true;
				}
			}
		}
		return obj;
	}

	public void pickupCannon() {
		int invin_length = Inventory.getAll().length;
		RSItem[] food = Inventory.find(((Food) Dispatcher.get()
				.get(ValueType.FOOD).getValue()).getId());
		if (Inventory.getAll().length > 24
				&& (invin_length - food.length) >= 24) {
			if (!this.eatForSpace(4, food)) {
				Logger.getLogger()
						.print("Unable to pickup cannon. You may have to retrieve it from the dwarf");
				return;
			}
		}
		clickCannon("Pick-up ");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Inventory.find(CANNON_IDS).length == 4;
			}
		}, 5000);
	}

	private boolean eatForSpace(int amount, RSItem[] food) {
		long st = System.currentTimeMillis();
		while (Timing.timeFromMark(st) < 12000 && amount > 0) {
			if (food.length > 0) {
				final int total_items_in_inventory = Dispatcher.get()
						.getLooter().getTotalInventoryCount();
				food[0].click("Eat");
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						return Dispatcher.get().getLooter()
								.getTotalInventoryCount() != total_items_in_inventory;
					}
				}, General.random(1200, 2000));
				amount--;
			}
		}
		return Inventory.getAll().length <= 24;

	}

	public void usePrayer(Prayer flicker_prayer) {
		if (!flicker_prayer.isActivated()
				&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 0)
			flicker_prayer.activate();
	}

	public void useGuthans() {
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

	public void equipGuthans() {
		CEquipment.equip(new int[][] { GUTHANS_HELM_IDS, GUTHANS_BODY_IDS,
				GUTHANS_LEGS_IDS, GUTHANS_WARSPEAR_IDS });
	}

	public boolean isGuthanDegraded() {
		return !CEquipment.isEquiped(GUTHANS_BODY_IDS[4] + 1)
				&& !CEquipment.isEquiped(GUTHANS_HELM_IDS[4] + 1)
				&& !CEquipment.isEquiped(GUTHANS_LEGS_IDS[4] + 1)
				&& !CEquipment.isEquiped(GUTHANS_WARSPEAR_IDS[4] + 1);
	}

	public boolean isDegradedGuthansInInventory() {
		return Inventory.find(GUTHANS_BODY_IDS[4] + 1, GUTHANS_HELM_IDS[4] + 1,
				GUTHANS_LEGS_IDS[4] + 1, GUTHANS_WARSPEAR_IDS[4] + 1).length > 0;
	}

	public void checkUse() {
		if (Game.getUptext().contains("->"))
			Mouse.clickBox(10, 446, 480, 480, 1);
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
				&& this.getNPCHPPercent(this.combat_task.getCurrentTarget()
						.getValue()) >= 30) {
			int wep_id = -1;
			int shield_id = -1;
			RSItem temp = Equipment.getItem(SLOTS.WEAPON);
			if (temp != null)
				wep_id = temp.getID();
			RSItem temp2 = Equipment.getItem(SLOTS.SHIELD);
			if (temp2 != null)
				shield_id = temp2.getID();
			this.special_attack_weapon.useSpecial(this.combat_task, wep_id,
					shield_id);
		}

	}

	private int getNPCHPPercent(RSNPC current_tar) {
		try {
			if (current_tar == null)
				return -1;
			int health = current_tar.getMaxHealth();
			if (health == 0)
				return 0;
			return ((current_tar.getHealth() / health) * 100);
		} catch (Exception e) {
			return 0;
		}
	}

	private void equipAmmo() {
		RSItem[] ammo = Inventory.find(ammo_id, knife_id);
		if (ammo.length > 0)
			if (ammo[0].getStack() >= AntiBan.eat_at) {
				AntiBan.eat_at = AntiBan.getABCUtil().generateEatAtHP();
				ammo[0].click("Wield");
			}
	}

	public void setAmmo() {
		if (this.is_ranging
				&& !((Boolean) Dispatcher.get()
						.get(ValueType.USE_TELEKINETIC_GRAB).getValue())) {
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
				RSItemDefinition knife_def = knife.getDefinition();
				if (knife_def != null)
					Dispatcher.get().set(
							ValueType.LOOT_ITEM_NAMES,
							new Value<String[]>(new String[] { knife_def
									.getName() }));
			}

		}

	}

	private int getSpecialPercent() {
		return Game.getSetting(300) / 10;
	}

	private void dropTrash() {
		RSItem[] items = Inventory.find(TRASH_ITEM_IDS);
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

	public void runHasTargetChecks() {
		if (this.flicker)
			flicker(this.prayer);
		if (this.armor_holder == null)
			this.useSpecialAttack();
		if (this.use_guthans)
			this.useGuthans();
	}

	public void runDefaultChecks() {
		checkRun();
		checkUse();
		checkDecayCannon();
		setupCannon();
		fireCannon();
		buryBones();
		Dispatcher.get().getLooter().alch();
		usePrayer(this.prayer);
	}

	private void checkDecayCannon() {
		if (this.pick_up_cannon) {
			this.pickupCannon();
			this.pick_up_cannon = false;
		}
	}

	public RSTile getSafeSpotTile() {
		return this.safe_spot_tile;
	}

	public boolean isRanging() {
		return this.is_ranging;
	}

	public void runCheckHasNoTarget() {
		dropTrash();
		equipAmmo();
	}

	public Prayer getPrayer() {
		return this.prayer;
	}

	public void setSpecialAttackWeapon(Weapon weapon2) {
		this.special_attack_weapon = weapon2;
	}

	public void setIsRanging(boolean booleanValue) {
		this.is_ranging = booleanValue;
	}

	public void setPrayer(Prayer value) {
		this.prayer = value;
	}

	public Weapon getSpecialAttackWeapon() {
		return this.special_attack_weapon;
	}

	public void setUseGuthans(boolean use_guthans2) {
		this.use_guthans = use_guthans2;
	}

	public boolean getUseGuthans() {
		return this.use_guthans;
	}

	public Boolean getUseFlicker() {
		return this.flicker;
	}

	public void setUseFlicker(boolean b) {
		this.flicker = b;
	}

	public void setSafeSpotTile(RSTile value) {
		this.safe_spot_tile = value;

	}

	public ArmorHolder getArmorHolder() {
		return this.armor_holder;
	}

	public int getAmmoId() {
		return ammo_id == -1 ? this.knife_id : this.ammo_id;
	}

	public void setUseCannon(boolean value) {
		this.use_cannon = value;
	}

	public boolean getUseCannon() {
		return this.use_cannon;
	}

	public void setCannonTile(RSTile tile) {
		this.cannon_location = tile;
	}

	public RSTile getCannonTile() {
		return this.cannon_location;
	}

	public void setPickupCannon() {
		this.pick_up_cannon = true;
	}

	public Value<Boolean> getBuryBones() {
		return new Value<Boolean>(this.bury_bones);
	}

	public void setBuryBones(boolean value) {
		this.bury_bones = value;

	}

}
