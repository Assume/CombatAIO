package scripts.CombatAIO.com.base.api.threading.types.subtype;

import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class LootItemValue extends Value<LootItem> {

	private LootItem item;

	public LootItemValue(LootItem item) {
		this.item = item;
	}

	@Override
	public LootItem get() {
		return item;
	}
}
