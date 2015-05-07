package scripts.CombatAIO.com.base.api.general.walking.types;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.rs3.NPCChat;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;

public enum Jewelery {

	RING_OF_DUELING(
			new int[] { 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566 },
			JEWELERY_TELEPORT_LOCATIONS.AL_KHARID_DUEL_AREA,
			JEWELERY_TELEPORT_LOCATIONS.CASTLE_WARS), GAMES_NECKLACE(new int[] {
			3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867 },
			JEWELERY_TELEPORT_LOCATIONS.BARBARIAN_OUTPOST), GLORY(new int[] {
			1706, 1708, 1710, 1712 }, JEWELERY_TELEPORT_LOCATIONS.EDGEVILLE,
			JEWELERY_TELEPORT_LOCATIONS.DRAYNOR_VILLAGE,
			JEWELERY_TELEPORT_LOCATIONS.KARAMJA);

	private int[] ids;
	private JEWELERY_TELEPORT_LOCATIONS[] locations;

	Jewelery(int[] ids, JEWELERY_TELEPORT_LOCATIONS... locations) {
		this.ids = ids;
		this.locations = locations;
	}

	public boolean hasJewelery() {
		return Equipment.find(ids).length > 0 || Inventory.find(ids).length > 0;
	}

	public boolean operate(final JeweleryTeleport teleport) {
		RSItem item = getJewelery();
		if (item == null)
			return false;
		item.click("Operate");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return NPCChat.isOpen();
			}
		}, General.random(1000, 1500));
		NPCChat.selectOption(teleport.getTeleportLocation().getTeleportCode(),
				false);
		Timing.waitCondition(new Condition() {

			@Override
			public boolean active() {
				return Player.getPosition().distanceTo(
						teleport.getTeleportLocation().getTeleportTile()) < 50;
			}
		}, General.random(7000, 9000));
		return Player.getPosition().distanceTo(
				teleport.getTeleportLocation().getTeleportTile()) < 50;
	}

	private RSItem getJewelery() {
		RSItem[] x;
		if (Equipment.isEquipped(ids))
			x = Equipment.find(ids);
		else
			x = Inventory.find(ids);
		if (x.length > 0)
			return x[0];
		return null;
	}

	public JEWELERY_TELEPORT_LOCATIONS[] getTeleportLocations() {
		return this.locations;
	}

	public static JeweleryTeleport getNearestJewleryTeleport(RSTile end_tile) {
		int nearest_distance = Integer.MAX_VALUE;
		Jewelery nearest_jewelery = null;
		JEWELERY_TELEPORT_LOCATIONS nearest_teleport = null;
		for (Jewelery x : values()) {
			for (JEWELERY_TELEPORT_LOCATIONS y : x.getTeleportLocations()) {
				int temp = y.getTeleportTile().distanceTo(end_tile);
				if (temp < nearest_distance) {
					nearest_distance = temp;
					nearest_jewelery = x;
					nearest_teleport = y;
				}
			}
		}
		return new JeweleryTeleport(nearest_jewelery, nearest_teleport);

	}
}
