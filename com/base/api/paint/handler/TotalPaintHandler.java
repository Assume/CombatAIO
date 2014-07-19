package scripts.CombatAIO.com.base.api.paint.handler;

public class TotalPaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	public TotalPaintHandler(final String[] monster_ids,
			final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler(monster_ids);
		this.loot_paint_handler = new LootPaintHandler(loot_ids);
	}

	public void updateAll() {

	}

	public void updateMonsters() {

	}

	public void updateLoot() {

	}

}
