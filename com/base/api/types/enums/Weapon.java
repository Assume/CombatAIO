package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.CEquipment;
import scripts.CombatAIO.com.base.api.threading.threads.CombatTask;

public enum Weapon {

	NONE(-1, -1, 10000, -1), ABYSSAL_WHIP(1800, 1658, 50, 4151), DRAGON_SCIMITAR(
			2400, -1, 55, 4587), DRAGON_BATTLEAXE(-1, -1, 100, 1377), DRAGON_DAGGER(
			-1, -1, 25, 1215, 1231, 5680, 5698), MAGIC_SHORT_BOW(-1, -1, 55,
			861), DRAGON_LONGSWORD(-1, -1, 25, 1305), ARMADYL_GODSWORD(-1, -1,
			50, 11694), BANDOS_GODSWORD(-1, -1, 100, 11804), SARADOMIN_GODSWORD(
			-1, -1, 50, 11698), ZAMORAK_GODSWORD(-1, -1, 60, 11700), DARK_BOW(
			-1, -1, 55, 11235), SARADOMIN_SWORD(-1, -1, 100, 11739), EXCALIBUR(
			-1, -1, 100, 35);

	private long attack_speed_ms;
	private int animation_id;
	private int special_attack_usage;
	private int[] ids;

	Weapon(long attack_speed_ms, int animation_id, int special_attack_usage,
			int... id) {
		this.attack_speed_ms = attack_speed_ms;
		this.animation_id = animation_id;
		this.special_attack_usage = special_attack_usage;
		this.ids = id;
	}

	public int getAnimationID() {
		return this.animation_id;
	}

	public long getAttackSpeed() {
		return this.attack_speed_ms;
	}

	public int getSpecialUsage() {
		return this.special_attack_usage;
	}

	public void useSpecial(CombatTask task) {
		int original_weapon_id = -1;
		int shield_id = -1;
		RSItem temp = Equipment.getItem(SLOTS.WEAPON);
		if (temp != null)
			original_weapon_id = temp.getID();
		RSItem temp2 = Equipment.getItem(SLOTS.SHIELD);
		if (temp2 != null)
			shield_id = temp2.getID();
		CEquipment.equipAll(ids);
		General.sleep(500, 1100);
		final int start_special = getSpecialPercent();
		this.turnSpecialOn();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return getSpecialPercent() < start_special;
			}
		}, 3500);
		RSNPC curr_atr = (RSNPC) task.getCurrentTarget().getValue();
		if (this.getSpecialPercent() >= this.getSpecialUsage()
				&& curr_atr != null && curr_atr.isValid())
			this.useSpecial(task);
		else {
			CEquipment.equipAll(original_weapon_id, shield_id);
			if (!Equipment.isEquipped(shield_id))
				CEquipment.equipAll(shield_id);
			if (!Equipment.isEquipped(original_weapon_id))
				CEquipment.equip(original_weapon_id);
		}
	}

	private int getSpecialPercent() {
		return Game.getSetting(300) / 10;
	}

	private void turnSpecialOn() {
		if (isSpecialOn())
			return;
		TABS.COMBAT.open();
		Mouse.clickBox(574, 417, 710, 427, 1);
		General.sleep(190, 260);
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return isSpecialOn();
			}
		}, 800);
	}

	private boolean isSpecialOn() {
		return Game.getSetting(301) == 1;
	}

	public static Weapon getWeaponFromName(String name) {
		for (Weapon x : Weapon.values())
			if (x.toString().equalsIgnoreCase(name))
				return x;
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		char[] chars = this.name().toCharArray();
		builder.append(chars[0]);
		for (int i = 1; i < chars.length; i++)
			builder.append(Character.toLowerCase(chars[i]));
		return builder.toString();

	}

	public int[] getIDs() {
		return this.ids;
	}
}
