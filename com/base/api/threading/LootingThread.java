package scripts.CombatAIO.com.base.api.threading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.LootItemValue;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class LootingThread extends Threadable implements Pauseable {

	private Map<String, LootItem> items_known;

	public LootingThread() {
		this(Arrays.asList(new PauseType[] {
				PauseType.NON_ESSENTIAL_TO_BANKING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.items_known = new HashMap<String, LootItem>();
		items_known.put("Coins", new LootItem("Coins"));
		items_known.put("Cowhide", new LootItem("Cowhide"));
	}

	private LootingThread(List<PauseType> pause_types) {
		super(pause_types);
	}

	public IntegerValue getTotalLootValue() {
		int tot = 0;
		for (LootItem x : this.items_known.values())
			tot += (x.getAmountLooted() * x.getPrice());
		return new IntegerValue(tot);
	}

	@Override
	public void run() {
		while (true) {
			RSNPC target = (RSNPC) Dispatcher.get()
					.get(ValueType.CURRENT_TARGET, null).getValue();
			if (target == null) {
				General.sleep(800);
				continue;
			}
			if (target.getHealth() == 0 && target.isInCombat())
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_LOOTING);
			else {
				General.sleep(500);
				continue;
			}

			loot(target);
			Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_LOOTING);
			General.sleep(400);
		}
	}

	private void waitForLoot(RSNPC target) {
		while (target.isValid() && target.getHealth() == 0
				&& target.isInCombat())
			General.sleep(50);
		General.sleep(150);
	}

	private void loot(RSNPC target) {
		waitForLoot(target);
		RSGroundItem[] items = GroundItems.find(getAllItemsName());
		if (items.length == 0)
			return;
		items = GroundItems.sortByDistance(Player.getPosition(), items);
		if (!(Boolean) Dispatcher.get().get(ValueType.IS_RANGING, null)
				.getValue())
			items = removeLongRangeItems(items);
		if (items.length == 0)
			return;
		for (RSGroundItem x : items) {
			final int total_items_in_inventory = getTotalInventoryCount();
			if (!x.isOnScreen())
				Camera.turnToTile(x.getPosition());
			RSItemDefinition def = x.getDefinition();
			if (def != null)
				Clicking.click("Take " + def.getName(), x);
			else
				continue;
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return getTotalInventoryCount() != total_items_in_inventory;
				}
			}, General.random(2000, 3000));

			LootItem update = items_known.get(def.getName());
			update.incrementAmountLooted(getTotalInventoryCount()
					- total_items_in_inventory);
			System.out.println("looted: " + def.getName()
					+ " total amount of that looted = "
					+ update.getAmountLooted());
		}
	}

	private int getTotalInventoryCount() {
		int tot = 0;
		RSItem[] items = Inventory.getAll();
		for (RSItem x : items)
			tot += x.getStack();
		return tot;
	}

	private RSGroundItem[] removeLongRangeItems(RSGroundItem[] items) {
		List<RSGroundItem> short_distance = new ArrayList<RSGroundItem>();
		RSTile pos = Player.getPosition();
		for (RSGroundItem x : items) {
			if (pos.distanceTo(x.getPosition()) <= 3)
				short_distance.add(x);
		}
		return short_distance.toArray(new RSGroundItem[short_distance.size()]);

	}

	private LootItem get(String name) {
		return this.items_known.get(name);
	}

	private String[] getAllItemsName() {
		Set<String> key_set = this.items_known.keySet();
		return key_set.toArray(new String[key_set.size()]);
	}

	/*
	 * @param extra_paramaters pass the name of the item whose object is wanted
	 * 
	 * @return the requested item in LootItemValue form, contains a null object
	 * if none exists
	 */
	public Value<LootItem> getLootItem(String[] extra_paramaters) {
		if (extra_paramaters.length == 0)
			return new LootItemValue(null);
		else
			return new LootItemValue(get(extra_paramaters[0]));
	}

	/*
	 * @param extra_paramaters pass the name of the item whose value is wanted
	 * 
	 * @return the value of the of the item in in IntegerValue form, contains 0
	 * if none exists
	 */
	public Value<Integer> getItemPrice(String... extra_paramaters) {
		if (extra_paramaters.length == 0)
			return new IntegerValue(0);
		else {
			LootItem got = get(extra_paramaters[0]);
			if (got == null)
				return new IntegerValue(0);
			else
				return new IntegerValue(got.getPrice());
		}
	}

	/*
	 * @param extra_paramaters pass the name of the item whose value is wanted
	 * 
	 * @return the number looted of the item requested in IntegerValue form,
	 * contains 0 if none exists
	 */
	public Value<Integer> getAmountLooted(String[] extra_paramaters) {
		if (extra_paramaters.length == 0)
			return new IntegerValue(0);
		else {
			LootItem got = get(extra_paramaters[0]);
			if (got == null)
				return new IntegerValue(0);
			else
				return new IntegerValue(got.getAmountLooted());
		}
	}

}
