package scripts.CombatAIO.com.base.api.threading;

import java.util.ArrayList;
import java.util.List;

import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.LootItemValue;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class LootingThread implements Runnable {

	private List<LootItem> items_known;

	public LootingThread() {
		this.items_known = new ArrayList<LootItem>();
	}

	public IntegerValue getTotalLootValue() {
		int tot = 0;
		for (LootItem x : this.items_known)
			tot += (x.getAmountLooted() * x.getPrice());
		return new IntegerValue(tot);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	private LootItem get(String name) {
		for (LootItem x : this.items_known)
			if (x.getName().equalsIgnoreCase(name))
				return x;
		return null;
	}

	public Value<LootItem> getLootItem(String[] extra_paramaters) {
		if (extra_paramaters.length == 0)
			return new LootItemValue(null);
		else
			return new LootItemValue(get(extra_paramaters[0]));
	}

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
