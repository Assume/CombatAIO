package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.paint.types.MonsterDisplay;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

final class MonsterPaintHandler implements PaintHandler {

	private RSNPC[] paintable_monsters;
	private RSNPC current_target;
	private MonsterDisplay current_target_display;
	private Map<RSNPC, MonsterDisplay> map;

	public MonsterPaintHandler() {
		this.map = new ConcurrentHashMap<RSNPC, MonsterDisplay>();
	}

	@Override
	public void update() {
		this.current_target = (RSNPC) Dispatcher.get()
				.get(ValueType.CURRENT_TARGET).getValue();
		if (this.current_target_display == null)
			this.current_target_display = new MonsterDisplay(
					this.current_target, true);
		else if (this.current_target_display.get() != this.current_target)
			this.current_target_display = new MonsterDisplay(
					this.current_target, true);
		/*
		 * try { this.paintable_monsters = StaticTargetCalculator
		 * .getPaintableMonsters();
		 * 
		 * 
		 * this.updateList(); if (this.current_target_display == null)
		 * this.current_target_display = new MonsterDisplay(
		 * this.current_target, true); else if
		 * (this.current_target_display.get() != this.current_target)
		 * this.current_target_display = new MonsterDisplay(
		 * this.current_target, true); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

	}

	private void updateList() {
		List<RSNPC> temp = Arrays.asList(this.paintable_monsters);
		for (RSNPC x : this.paintable_monsters)
			if (!map.containsKey(x))
				map.put(x, new MonsterDisplay(x, false));
		for (RSNPC y : this.map.keySet())
			if (!temp.contains(y))
				map.remove(y);
	}

	@Override
	public void draw(Graphics g, long l) {
		/*
		 * for (RSNPC x : this.map.keySet()) { MonsterDisplay temp = map.get(x);
		 * if (temp != null) temp.draw(g); }
		 */
		if (this.current_target_display != null)
			this.current_target_display.draw(g);

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
