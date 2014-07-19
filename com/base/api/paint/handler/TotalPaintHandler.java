package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics2D;
import java.awt.Point;

public class TotalPaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private Graphics2D graphics;

	public TotalPaintHandler(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public void setValues(final String[] monster_ids, final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler(monster_ids,
				this.graphics);
		this.loot_paint_handler = new LootPaintHandler(loot_ids, this.graphics);
	}

	public void onClick(Point p) {

	}

	public void updateAll() {

	}

	public void updateMonsters() {

	}

	public void updateLoot() {

	}

}
