package scripts.CombatAIO.com.base.api.threading;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Pauseable;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
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

	}

	private LootItem get(String name) {
		return this.items_known.get(name);
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
