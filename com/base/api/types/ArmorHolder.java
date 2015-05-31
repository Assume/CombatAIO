package scripts.CombatAIO.com.base.api.types;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.CEquipment;

public class ArmorHolder {

	private int helmet_id = -1;
	private int weapon_id = -1;
	private int shield_id = -1;
	private int legs_id = -1;
	private int body_id = -1;

	public ArmorHolder(int helmet_id, int weapon_id, int shield_id,
			int legs_id, int body_id) {
		this.helmet_id = helmet_id;
		this.weapon_id = weapon_id;
		this.shield_id = shield_id;
		this.legs_id = legs_id;
		this.body_id = body_id;
	}

	public ArmorHolder() {
		this(Equipment.getItem(SLOTS.HELMET) == null ? -1 : Equipment.getItem(
				SLOTS.HELMET).getID(),
				Equipment.getItem(SLOTS.WEAPON) == null ? -1 : Equipment
						.getItem(SLOTS.WEAPON).getID(), Equipment
						.getItem(SLOTS.SHIELD) == null ? -1 : Equipment
						.getItem(SLOTS.SHIELD).getID(), Equipment
						.getItem(SLOTS.LEGS) == null ? -1 : Equipment.getItem(
						SLOTS.LEGS).getID(),
				Equipment.getItem(SLOTS.BODY) == null ? -1 : Equipment.getItem(
						SLOTS.BODY).getID());
	}

	public boolean isEquipped() {
		if (this.helmet_id != -1) {
			RSItem helmet = Equipment.getItem(SLOTS.HELMET);
			if (helmet == null || helmet.getID() != this.helmet_id)
				return false;
		}
		if (this.weapon_id != -1) {
			RSItem weapon = Equipment.getItem(SLOTS.WEAPON);
			if (weapon == null || weapon.getID() != this.shield_id)
				return false;
		}
		if (this.shield_id != -1) {
			RSItem shield = Equipment.getItem(SLOTS.SHIELD);
			if (shield == null || shield.getID() != this.shield_id)
				return false;
		}
		if (this.legs_id != -1) {
			RSItem legs = Equipment.getItem(SLOTS.LEGS);
			if (legs == null || legs.getID() != this.legs_id)
				return false;
		}
		if (this.body_id != -1) {
			RSItem body = Equipment.getItem(SLOTS.BODY);
			if (body == null || body.getID() != this.body_id)
				return false;
		}
		return true;
	}

	public void equip() {
		CEquipment.equipAll(this.helmet_id, this.weapon_id, this.shield_id,
				this.legs_id, this.body_id);
	}

	@Override
	public String toString() {
		return "" + this.helmet_id + ", " + this.body_id + ", " + this.legs_id
				+ ", " + this.shield_id + ", " + this.weapon_id;
	}

}
