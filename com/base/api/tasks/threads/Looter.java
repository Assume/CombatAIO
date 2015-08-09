package scripts.CombatAIO.com.base.api.tasks.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.magic.books.NormalSpell;
import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Pauseable;
import scripts.CombatAIO.com.base.api.tasks.types.Threadable;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.CombatAIO.com.base.main.utils.Logger;

public class Looter extends Threadable implements Pauseable {

	private Map<String, LootItem> items_known;
	private boolean eat_for_space = true;
	private boolean wait_for_loot;
	private boolean loot_in_combat;
	private boolean use_tele_grab;

	private RSNPC nil = null;
	private int minimum_price = Integer.MAX_VALUE;

	public Looter() {
		this(Arrays.asList(new PauseType[] {
				PauseType.NON_ESSENTIAL_TO_BANKING,
				PauseType.COULD_INTERFERE_WITH_EATING }));
		this.items_known = new ConcurrentHashMap<String, LootItem>();
		this.addPossibleLootItem(true, "Clue scroll (easy)");
		this.addPossibleLootItem(true, "Clue scroll (medium)");
		this.addPossibleLootItem(true, "Clue scroll (hard)");
		this.addPossibleLootItem(true, "Clue scroll (elite)");
		super.setName("LOOTING_THREAD");
	}

	private Looter(List<PauseType> pause_types) {
		super(pause_types);
	}

	public void addPossibleLootItem(boolean always_loot, boolean[] alch,
			String... name) {
		for (int i = 0; i < name.length; i++)
			if (name[i] != null && name[i].length() > 1
					&& !this.items_known.containsKey(name[i]))
				this.items_known.put(name[i], new LootItem(name[i],
						always_loot, alch[i]));
	}

	public void addPossibleLootItem(boolean always_loot, String... name) {
		for (String x : name)
			if (x != null && x.length() > 1 && !this.items_known.containsKey(x))
				this.items_known.put(x, new LootItem(x, always_loot, false));
	}

	public void addLootItem(LootItem value) {
		LootItem temp = this.items_known.get(value.getName());
		if (temp == null || !temp.shouldAlwaysLoot())
			this.items_known.put(value.getName(), value);
	}

	@Override
	public void run() {
		while (true) {
			RSNPC target = (RSNPC) Dispatcher.get()
					.get(ValueType.CURRENT_TARGET).getValue();
			if (this.loot_in_combat && Combat.getAttackingEntities().length > 0
					&& this.lootIsOnGround()) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"LOOTING_THREAD IS CALLING PAUSE");
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_LOOTING);
				loot(nil);
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"LOOTING_THREAD IS CALLING UNPAUSE");
				Dispatcher.get()
						.unpause(PauseType.COULD_INTERFERE_WITH_LOOTING);
			}
			if (this.lootIsOnGround()
					&& Combat.getAttackingEntities().length == 0
					&& Player.getRSPlayer().getInteractingCharacter() == null) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"LOOTING_THREAD IS CALLING PAUSE");
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_LOOTING);
				loot(nil);
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"LOOTING_THREAD IS CALLING UNPAUSE");
				Dispatcher.get()
						.unpause(PauseType.COULD_INTERFERE_WITH_LOOTING);
			}
			if (target == null) {
				General.sleep(800);
				continue;
			}
			if (target.getHealth() == 0 && target.isInCombat()
					&& this.items_known.size() > 1 && this.wait_for_loot) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"LOOTING_THREAD IS CALLING PAUSE");
				Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_LOOTING);
				loot(target);
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"LOOTING_THREAD IS CALLING UNPAUSE");
				Dispatcher.get()
						.unpause(PauseType.COULD_INTERFERE_WITH_LOOTING);
				General.sleep(400);
			} else
				General.sleep(500);
		}
	}

	private void waitForLoot(RSNPC target) {
		while (target.isValid() && target.getHealth() == 0
				&& target.isInCombat())
			General.sleep(50);
		General.sleep(150);
	}

	private void loot(RSNPC target) {
		if (this.wait_for_loot && !this.loot_in_combat && target != null)
			waitForLoot(target);
		RSGroundItem[] items = getLootableItems();
		if (items.length == 0)
			return;
		items = GroundItems.sortByDistance(Player.getPosition(), items);
		if (items.length == 0)
			return;
		loot(items);
	}

	private void loot(RSGroundItem[] items) {
		for (RSGroundItem x : items) {
			if (Inventory.isFull())
				if (!eatForSpace(x))
					break;
			if (Inventory.isFull() && !itemIsStackableAndInInventory(x))
				continue;
			if (!x.isOnScreen())
				Camera.turnToTile(x.getPosition());
			String name = getRSGroundItemName(x);
			if (name == null)
				continue;
			final int total_item_in_inventory = getInventoryCountOfItem(name);
			final int total_items_in_inventory = getTotalInventoryCount();
			if (this.use_tele_grab)
				if (!NormalSpell.TELEKINETIC_GRAB.canCast())
					return;
				else
					NormalSpell.TELEKINETIC_GRAB.select();
			if (this.use_tele_grab)
				Clicking.click("Cast Telekinetic Grab -> " + name, x);
			else
				Clicking.click("Take " + name, x);
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return getTotalInventoryCount() != total_items_in_inventory;
				}
			}, General.random(2000, 3000));
			General.sleep(250, 450);
			LootItem update = items_known.get(name);
			if (update.getId() == -1)
				update.setId(x.getID());
			update.incrementAmountLooted(getInventoryCountOfItem(name)
					- total_item_in_inventory);
		}
	}

	private RSGroundItem[] getLootableItems() {
		RSGroundItem[] items = GroundItems.find(getAllItemNames());
		List<RSGroundItem> list = new ArrayList<RSGroundItem>();
		int ammo_id = (Integer) Dispatcher.get().get(ValueType.AMMO_ID)
				.getValue();
		for (RSGroundItem x : items) {
			LootItem r = this.items_known.get(getRSGroundItemName(x));
			if (!PathFinding.canReach(x, false) && !this.use_tele_grab)
				continue;
			if (Player.getPosition().distanceTo(x) > 8)
				continue;
			if (r != null) {
				if (r.getId() == ammo_id && this.use_tele_grab)
					continue;
				if (r.getId() == ammo_id && x.getStack() < 3)
					continue;
				if (r.shouldAlwaysLoot())
					list.add(x);
				else if (x.getStack() * r.getPrice() >= this.minimum_price)
					list.add(x);
			}
		}
		if (!(Boolean) Dispatcher.get().get(ValueType.IS_RANGING).getValue())
			return this.removeLongRangeItems(list.toArray(new RSGroundItem[list
					.size()]));
		else
			return list.toArray(list.toArray(new RSGroundItem[list.size()]));
	}

	private boolean itemIsStackableAndInInventory(RSGroundItem x) {
		RSItem[] inventory = Inventory.find(x.getID());
		if (inventory.length > 0) {
			RSItemDefinition def = inventory[0].getDefinition();
			if (def != null && def.isStackable())
				return true;
		}
		return false;
	}

	private boolean eatForSpace(RSGroundItem x) {
		if ((Boolean) Dispatcher.get().get(ValueType.IS_BONES_TO_PEACHES)
				.getValue()) {
			RSItemDefinition def = x.getDefinition();
			if (def != null && def.getName().equalsIgnoreCase("bones"))
				return true;
		}
		if (itemIsStackableAndInInventory(x))
			return true;
		RSItem[] food = Inventory.find(((Food) Dispatcher.get()
				.get(ValueType.FOOD).getValue()).getId());
		if (food.length > 0) {
			final int total_items_in_inventory = getTotalInventoryCount();
			food[0].click("Eat");
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return getTotalInventoryCount() != total_items_in_inventory;
				}
			}, General.random(1200, 2000));
			return true;
		}
		return false;
	}

	public void alch() {
		if (Dispatcher.get().isLiteMode())
			return;
		String names[] = getAllItemNames();
		if (names.length == 0)
			return;
		RSItem[] items = Inventory.find(names);
		for (RSItem x : items) {
			String name = getItemNameFromRSItem(x);
			if (name == null)
				continue;
			LootItem item = this.items_known.get(name);
			if (item == null)
				continue;
			if (item.shouldAlch())
				item.alch(x);
		}
	}

	private int getInventoryCountOfItem(String name) {
		int tot = 0;
		RSItem[] items = Inventory.find(name);
		for (RSItem x : items)
			tot += x.getStack();
		return tot;
	}

	public int getTotalInventoryCount() {
		int tot = 0;
		RSItem[] items = Inventory.getAll();
		for (RSItem x : items)
			tot += x.getStack();
		return tot;
	}

	private boolean lootIsOnGround() {
		return getLootableItems().length > 0;
	}

	private RSGroundItem[] removeLongRangeItems(RSGroundItem[] items) {
		List<RSGroundItem> short_distance = new ArrayList<RSGroundItem>();
		RSTile pos = Player.getPosition();
		for (RSGroundItem x : items) {
			if (pos.distanceTo(x.getPosition()) <= 9)
				short_distance.add(x);
		}
		return short_distance.toArray(new RSGroundItem[short_distance.size()]);

	}

	/*
	 * @param extra_paramaters pass the name of the item whose value is wanted
	 * 
	 * @return the value of the of the item in in IntegerValue form, contains 0
	 * if none exists
	 */
	public Value<Integer> getItemPrice(String... extra_paramaters) {
		if (extra_paramaters.length == 0)
			return new Value<Integer>(0);
		else {
			LootItem got = get(extra_paramaters[0]);
			if (got == null)
				return new Value<Integer>(0);
			else
				return new Value<Integer>(got.getPrice());
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
			return new Value<Integer>(0);
		else {
			LootItem got = get(extra_paramaters[0]);
			if (got == null)
				return new Value<Integer>(0);
			else
				return new Value<Integer>(got.getAmountLooted());
		}
	}

	private String getRSGroundItemName(RSGroundItem x) {
		RSItemDefinition def = x.getDefinition();
		if (def == null)
			return null;
		return def.getName();
	}

	private String getItemNameFromRSItem(RSItem x) {
		if (x == null)
			return null;
		RSItemDefinition def = x.getDefinition();
		if (def == null)
			return null;
		return def.getName();
	}

	public Value<Boolean> shouldUseTelegrab() {
		return new Value<Boolean>(this.use_tele_grab);
	}

	public void setUseTelegrab(boolean use) {
		this.use_tele_grab = use;
	}

	public Value<Boolean> shouldEatForSpace() {
		return new Value<Boolean>(this.eat_for_space);
	}

	public void setLootInCombat(boolean active) {
		this.loot_in_combat = active;
	}

	public Value<Boolean> lootInCombat() {
		return new Value<Boolean>(this.loot_in_combat);
	}

	public Value<Boolean> waitForLoot() {
		return new Value<Boolean>(this.wait_for_loot);
	}

	public void setWaitForLoot(boolean active) {
		this.wait_for_loot = active;
	}

	private String[] getAllItemNames() {
		Set<String> key_set = this.items_known.keySet();
		return key_set.toArray(new String[key_set.size()]);
	}

	public Value<String[]> getAllItemNamesValue() {
		return new Value<String[]>(getAllItemNames());
	}

	/*
	 * @param extra_paramaters pass the name of the item whose object is wanted
	 * 
	 * @return the requested item in LootItemValue form, contains a null object
	 * if none exists
	 */
	public Value<LootItem> getLootItem(String[] extra_paramaters) {
		if (extra_paramaters.length == 0)
			return new Value<LootItem>(null);
		else
			return new Value<LootItem>(get(extra_paramaters[0]));
	}

	private LootItem get(String name) {
		return this.items_known.get(name);
	}

	public void setMinimumLootValue(int value) {
		this.minimum_price = value;
	}

	public Value<Integer> getMinimumLootValue() {
		return new Value<Integer>(this.minimum_price);
	}

	public Value<LootItem[]> getLootItems() {
		Collection<LootItem> items = this.items_known.values();
		return new Value<LootItem[]>(items.toArray(new LootItem[items.size()]));
	}

	public Value<Integer> getTotalLootValue() {
		int tot = 0;
		for (LootItem x : this.items_known.values())
			tot += (x.getAmountLooted() * x.getPrice());
		return new Value<Integer>(tot);
	}

}
