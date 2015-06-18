package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Polygon;
import java.util.Arrays;

import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class PaintData {

	private static int monster_kills;
	private static int profit;
	private static LootItem[] loot_items;
	private static Polygon target;
	private static boolean is_lite;

	public static void updateAll() {
		monster_kills = (Integer) Dispatcher.get().get(ValueType.TOTAL_KILLS)
				.getValue();
		profit = (Integer) Dispatcher.get().get(ValueType.TOTAL_LOOT_VALUE)
				.getValue();
		loot_items = ((LootItem[]) Dispatcher.get()
				.get(ValueType.ALL_LOOT_ITEMS).getValue());
		if (loot_items != null)
			Arrays.sort(loot_items);
		RSNPC tar = (RSNPC) Dispatcher.get().get(ValueType.CURRENT_TARGET)
				.getValue();
		if (tar != null)
			target = Projection.getTileBoundsPoly(tar, 0);
		else
			target = null;
		is_lite = Dispatcher.get().isLiteMode();
	}

	public static int getMonsterKills() {
		return monster_kills;
	}

	public static int getProfit() {
		return profit;
	}

	public static boolean isLite() {
		return is_lite;
	}

	public static LootItem[] getLootItems() {
		if (loot_items != null)
			return Arrays.copyOf(loot_items, loot_items.length);
		return new LootItem[0];
	}

	public static Polygon getTargetPolygon() {
		return target;
	}

}
