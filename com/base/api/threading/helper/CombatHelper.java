package scripts.CombatAIO.com.base.api.threading.helper;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.CEquipment;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.threads.CombatTask;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.ArmorHolder;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;

public class CombatHelper {

	public static final int[] GUTHANS_HELM_IDS = { 4724, 4904, 4905, 4906, 4907 };
	public static final int[] GUTHANS_BODY_IDS = { 4728, 4916, 4917, 4918, 4919 };
	public static final int[] GUTHANS_LEGS_IDS = { 4730, 4922, 4923, 4924, 4925 };
	public static final int[] GUTHANS_WARSPEAR_IDS = { 4726, 4910, 4911, 4912,
			4913 };

	public static final int TRASH_ITEM_IDS[] = { 117, 9978, 10115, 10125,
			10127, 229, 592 };

	private CombatTask combat_task;

	private ArmorHolder armor_holder;
	private RSTile safe_spot_tile;

	private int ammo_id = -1;
	private int knife_id = -1;

	private boolean is_ranging = false;
	private boolean flicker = false;
	private boolean use_guthans = false;

	private Prayer prayer = Prayer.NONE;
	private Weapon weapon = Weapon.NONE;
	private Weapon special_attack_weapon = Weapon.NONE;

	public CombatHelper(CombatTask task) {
		this.combat_task = task;
		this.armor_holder = null;
	}

	public void checkRun() {
		if (Game.getRunEnergy() >= Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_RUN_AT
				.next() && !Game.isRunOn()) {
			Options.setRunOn(true);
			Dispatcher.get().getABCUtil().INT_TRACKER.NEXT_RUN_AT.reset();
		}
	}

	public void safeSpotCheck() {
		if (this.safe_spot_tile != null)
			if (!Player.getPosition().equals(safe_spot_tile))
				Walking.walkScreenPath(Walking
						.generateStraightScreenPath(safe_spot_tile));
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
				&& this.getNPCHPPercent((RSNPC) this.combat_task
						.getCurrentTarget().getValue()) >= 30) {
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
		Dispatcher.get().getLooter().alch();
		usePrayer(this.prayer);
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

}
