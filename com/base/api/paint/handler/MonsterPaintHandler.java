package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

final class MonsterPaintHandler implements PaintHandler {

	private String[] monster_names;
	private Graphics2D graphics;
	private List<RSNPC> paintable_monsters;

	public MonsterPaintHandler(String[] monster_names, Graphics2D graphics) {
		this.monster_names = monster_names;
		this.graphics = graphics;
	}

	@Override
	public void update() {
		this.paintable_monsters = new ArrayList<RSNPC>();
		for (RSNPC p : NPCs.find(monster_names))
			if (p.isValid() && p.isOnScreen())
				paintable_monsters.add(p);
	}

	@Override
	public void draw() {
		/*
		 * @TODO split into 4 categories: current monster, next monster,
		 * possible monsters, not possible monster - 4 different colors paint
		 * the tiles they are on (outline)
		 */
	}

	private RSNPC getCurrentMonster() {
		return (RSNPC) Dispatcher.get().get(ValueType.CURRENT_TARGET).get();
	}

	private RSNPC[] getPossibleMonsters() {
		// TODO grab it from this.paintable_monsters
		return null;
	}

	private RSNPC getNextMonster() {
		// TODO optional
		return null;
	}

	private RSNPC[] getInvalidMonsters() {
		// TODO get from this.paintable_monsters
		return null;
	}

	@Override
	public void onClick(Point p) {
		// TODO
		/*
		 * for each RSNPC in this.paintable_items check if isInClick, if
		 * so call onClick
		 */

	}

}
