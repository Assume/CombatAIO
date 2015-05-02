package scripts.CombatAIO.com.base.api.general.walking.custom.background;

import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;

public class Equipment {

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
		if (items.length > 0) {
			Rectangle rec = items[0].getArea();
			Point p = new Point((int) rec.getCenterX() + General.random(0, 4),
					(int) rec.getCenterY() + General.random(0, 4));
			Mouse.move(p);
			Mouse.click(1);
			items[0].click("W");
		}

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
}