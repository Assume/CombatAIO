package scripts.CombatAIO.com.base.api.threading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import scripts.CombatAIO.com.base.api.threading.types.Dispatchable;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.subtype.IntegerValue;
import scripts.CombatAIO.com.base.api.threading.types.subtype.LootItemValue;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class LootingThread implements Runnable, Dispatchable {

	private List<LootItem> items_known;
	private CombatThread combat_thread;
	private List<PauseType> pause_types;

	public LootingThread(CombatThread combat_thread) {
		this.items_known = new ArrayList<LootItem>();
		this.combat_thread = combat_thread;
		this.pause_types = Arrays
				.asList(new PauseType[] { PauseType.NON_ESSENTIAL_TO_BANKING });
	}

	public IntegerValue getTotalLootValue() {
		int tot = 0;
		for (LootItem x : this.items_known)
			tot += (x.getAmountLooted() * x.getPrice());
		return new IntegerValue(tot);
	}

	@Override
	public void run() {
		/*
		 * while(true) if(this.combat_thread.canLoot()) loot();
		 */

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
