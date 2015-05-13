package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.paint.types.MonsterDisplay;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.helper.StaticTargetCalculator;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

final class MonsterPaintHandler implements PaintHandler {

	private RSNPC[] paintable_monsters;
	private RSNPC current_target;

	public MonsterPaintHandler() {
		this.paintable_monsters = new RSNPC[0];
		this.current_target = (RSNPC) Dispatcher.get()
				.get(ValueType.CURRENT_TARGET).getValue();
	}

	@Override
	public void update() {
		this.paintable_monsters = StaticTargetCalculator.getPaintableMonsters();
		this.current_target = (RSNPC) Dispatcher.get()
				.get(ValueType.CURRENT_TARGET).getValue();
	}

	@Override
	public void draw(Graphics g) {
		new MonsterDisplay(this.current_target, true).draw(g);
		for (RSNPC x : paintable_monsters)
			new MonsterDisplay(x, false).draw(g);
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
