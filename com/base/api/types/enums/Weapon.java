package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.CEquipment;

public enum Weapon {

	NONE(-1, -1, 10000), ABYSSAL_WHIP(1800, 1658, 50), DRAGON_SCIMITAR(2400,
			-1, 55), DRAGON_BATTLEAXE(-1, -1, 100), DRAGON_DAGGER(-1, -1, 25), MAGIC_SHORT_BOW(
			-1, -1, 55), DRAGON_LONG_SWORD(-1, -1, 25), ARMADYL_GODSWORD(-1,
			-1, 50), BANDSOS_GODSWORD(-1, -1, 100), SARADOMIN_GODSWORD(-1, -1,
			50), ZAMORAK_GODSWORD(-1, -1, 60), DARK_BOW(-1, -1, 55), SARADOMIN_SWORD(
			-1, -1, 100), EXCALIBUR(-1, -1, 100);

	private long attack_speed_ms;
	private int animation_id;
	private int special_attack_usage;

	Weapon(long attack_speed_ms, int animation_id, int special_attack_usage) {
		this.attack_speed_ms = attack_speed_ms;
		this.animation_id = animation_id;
		this.special_attack_usage = special_attack_usage;
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

	public void useSpecial() {
		String name = this.toString();
		if (!Equipment.isEquipped(name)) {
			String original_weapon_name = null;
			String shield_name = null;
			RSItem temp = Equipment.getItem(SLOTS.WEAPON);
			RSItemDefinition defintion = temp.getDefinition();
			if (defintion != null)
				original_weapon_name = defintion.getName();
			RSItem temp2 = Equipment.getItem(SLOTS.SHIELD);
			if (temp2 != null) {
				RSItemDefinition defintion2 = temp2.getDefinition();
				if (defintion2 != null)
					shield_name = defintion.getName();
			}
			CEquipment.equip(name);
			this.turnSpecialOn();
			CEquipment.equip(original_weapon_name, shield_name);
		} else
			this.turnSpecialOn();

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		char[] chars = this.name().toCharArray();
		builder.append(chars[0]);
		for (int i = 1; i < chars.length; i++)
			builder.append(Character.toLowerCase(chars[i]));
		return builder.toString();

	}

}
