package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class TotalPaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private ExperiencePaintHandler experience_paint_handler;

	private Graphics2D graphics;

	public TotalPaintHandler(Graphics2D graphics) {
		this.graphics = graphics;
		this.experience_paint_handler = new ExperiencePaintHandler();
	}

	public void setValues(final String[] monster_ids, final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler(monster_ids,
				this.graphics);
		this.loot_paint_handler = new LootPaintHandler(loot_ids, this.graphics);
	}

	public void onClick(Point p) {
		this.monster_paint_handler.onClick(p);
		this.loot_paint_handler.onClick(p);
	}

	public void updateAll() {
		this.monster_paint_handler.update();
		this.loot_paint_handler.update();
	}

	public void updateMonsters() {
		this.monster_paint_handler.update();
	}

	public void updateLoot() {
		this.loot_paint_handler.update();
	}

	public void draw(Graphics arg0) {
		// this.monster_paint_handler.draw(arg0);
		// this.loot_paint_handler.draw(arg0);
		this.experience_paint_handler.draw(arg0);
	}

}
