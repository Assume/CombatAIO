package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Point;

public class TotalPaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private ExperiencePaintHandler experience_paint_handler;

	public TotalPaintHandler() {
		this.experience_paint_handler = new ExperiencePaintHandler();
		this.monster_paint_handler = new MonsterPaintHandler();
		this.loot_paint_handler = new LootPaintHandler();
	}

	public void setValues(final String[] monster_ids, final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler();
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
		updateAll();
		this.monster_paint_handler.draw(arg0);
		// this.loot_paint_handler.draw(arg0);
		this.experience_paint_handler.draw(arg0);
	}

}
