package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class TotalPaintHandler {

	private static final String VERSION_NUMBER = "0.0.3_0";

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

	private String getFormattedTime(long time) {
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		seconds = time / 1000;
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= (minutes * 60);
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= (hours * 60);
		}
		return (hours + ":" + minutes + ":" + seconds);
	}

	public void draw(Graphics arg0) {
		if (!Dispatcher.get().hasStarted())
			return;
		arg0.setColor(Color.BLACK);
		arg0.drawString(
				getFormattedTime((Long) Dispatcher.get()
						.get(ValueType.RUN_TIME).getValue()), 432, 473);
		arg0.drawString(VERSION_NUMBER, 410, 473);
		// updateAll();
		// this.monster_paint_handler.draw(arg0);
		// this.loot_paint_handler.draw(arg0);
		if (Dispatcher.get().hasStarted())
			this.experience_paint_handler.draw(arg0);
	}

}
