package scripts.CombatAIO.com.base.api.tasks.threads;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;

import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Threadable;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;
import scripts.CombatAIO.com.base.main.Dispatcher;

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
			if (minimum_price == Integer.MAX_VALUE)
				break;
			for (RSGroundItem x : items) {
				int id = x.getID();
				if (checked_items.contains(id))
					continue;
				checked_items.add(id);
				String name = getItemNameFromRSItem(x);
				if (name == null)
					continue;
				if (Dispatcher.get().get(ValueType.LOOT_ITEM, name).getValue() != null)
					continue;
				int price = LootItem.getPrice(id);
				LootItem temp = new LootItem(name, false, false);
				temp.setPrice(price);
				temp.setId(id);
				Dispatcher.get().set(ValueType.LOOT_ITEM,
						new Value<LootItem>(temp));
			}
			General.sleep(1000);
		}
	}

	private String getItemNameFromRSItem(RSGroundItem x) {
		if (x == null)
			return null;
		RSItemDefinition def = x.getDefinition();
		return def == null ? null : def.getName();
	}
}
