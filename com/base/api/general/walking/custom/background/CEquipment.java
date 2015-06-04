package scripts.CombatAIO.com.base.api.general.walking.custom.background;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.main.utils.ArrayUtil;

public class CEquipment {

	public enum Gear {
		HELM(0), CAPE(1), NECK(2), WEAPON(3), BODY(4), SHIELD(5), LEGS(7), GLOVES(
				9), BOOTS(10), RING(12), ARROW(13);
		private int value;

		private Gear(int value) {
			this.value = value;
		}
	}

	public static void equip(int... id) {
		GameTab.TABS.INVENTORY.open();
		RSItem[] items = Inventory.find(id);
		if (items.length > 0)
			items[0].click("W");
	}

	public static int[] getUnequippedItems(int... ar) {
		List<Integer> list = new ArrayList<Integer>();
		for (int x : ar)
			if (!Equipment.isEquipped(x))
				list.add(x);
		return ArrayUtil.toArrayInt(list);
	}

	public static void equip(String... name) {
		GameTab.TABS.INVENTORY.open();
		RSItem[] items = Inventory.find(name);
		if (items.length > 0)
			items[0].click("W");
	}

	public static int getEquipmentID(Gear spot) {
		RSInterface face = Interfaces.get(387, 28);
		if (face != null)
			for (RSItem i : face.getItems())
				if (i.getIndex() == spot.value)
					return i.getID();
		return -1;
	}

	public static boolean isEquiped(int id) {
		RSInterface face = Interfaces.get(387, 28);
		if (face != null)
			for (RSItem i : face.getItems())
				if (i.getID() == id)
					return true;
		return false;
	}

	public static boolean isEquiped(int[] id) {
		RSInterface face = Interfaces.get(387, 28);
		if (face != null) {
			for (RSItem i : face.getItems()) {
				for (int a : id) {
					if (i.getID() == a) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void equip(int[][] is) {
		for (int i = 0; i < is.length; i++) {
			equip(is[i]);
			General.sleep(450, 700);
		}
	}

	public static void equipAll(int... id) {
		for (int x : id) {
			equip(x);
			General.sleep(450, 700);
		}
	}
}