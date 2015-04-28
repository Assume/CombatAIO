package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.types.RSGroundItem;

final class LootPaintHandler implements PaintHandler {

	private String[] loot_names;
	private Graphics2D graphics;
	private List<RSGroundItem> paintable_items;

	public LootPaintHandler(String[] loot_names, Graphics2D graphics) {
		this.loot_names = loot_names;
		this.graphics = graphics;
	}

	@Override
	public void update() {
		this.paintable_items = new ArrayList<RSGroundItem>();
		for (RSGroundItem i : GroundItems.find(loot_names))
			if (i.isOnScreen())
				paintable_items.add(i);
	}

	@Override
	public void draw(Graphics g) {
		// TODO draw items + amount - grab from this.paintable_items

	}

	@Override
	public void onClick(Point p) {
		// TODO
		/*
		 * for each RSGroundItem in this.paintable_items check if isInClick, if
		 * so call onClick
		 */
	}

}
