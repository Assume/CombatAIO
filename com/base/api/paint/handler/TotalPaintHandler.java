package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics2D;

public class TotalPaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private Graphics2D graphics;

	public TotalPaintHandler(Graphics2D graphics, final String[] monster_ids,
			final String... loot_ids) {
		this.graphics = graphics;
		this.monster_paint_handler = new MonsterPaintHandler(monster_ids,
				this.graphics);
		this.loot_paint_handler = new LootPaintHandler(loot_ids, this.graphics);
	}

	public void updateAll() {

	}

	public void updateMonsters() {

	}

	public void updateLoot() {

	}

}
