package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class PriceUpdater extends Threadable implements Runnable {

	private List<Integer> checked_items;

	public PriceUpdater() {
		this(null);
	}

	public PriceUpdater(List<PauseType> pause_types) {
		super(pause_types);
		this.checked_items = new ArrayList<Integer>();
	}

	@Override
	public void run() {
		while (true) {
			RSGroundItem[] items = GroundItems.getAll();
			int minimum_price = (Integer) Dispatcher.get()
					.get(ValueType.MINIMUM_LOOT_VALUE).getValue();
			for (RSGroundItem x : items) {
				int id = x.getID();
				if (checked_items.contains(id))
					continue;
				RSItemDefinition def = x.getDefinition();
				if (def == null)
					continue;
				String name = def.getName();
				int price = LootItem.getPrice(id);
				if (price >= minimum_price) {
					LootItem temp = new LootItem(name);
					temp.setPrice(price);
					temp.setId(id);
					Dispatcher.get().set(ValueType.LOOT_ITEM, new Value<LootItem>(temp));
				}
			}
		}
	}
}
