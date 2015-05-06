package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.helper.StaticTargetCalculator;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

final class MonsterPaintHandler implements PaintHandler {

	private String[] monster_names;
	private Graphics2D graphics;
	private RSNPC[] paintable_monsters;
	private RSNPC current_target;

	public MonsterPaintHandler(String[] monster_names, Graphics2D graphics) {
		this.monster_names = monster_names;
		this.graphics = graphics;
	}

	@Override
	public void update() {
		this.paintable_monsters = StaticTargetCalculator.getPaintableMonsters();
		this.current_target = (RSNPC) Dispatcher.get()
				.get(ValueType.CURRENT_TARGET).getValue();
	}

	@Override
	public void draw(Graphics g) {
	}

	private RSNPC getCurrentMonster() {
		return this.current_target;
	}

	@Override
	public void onClick(Point p) {
		// TODO
		/*
		 * for each RSNPC in this.paintable_items check if isInClick, if so call
		 * onClick
		 */

	}

}
