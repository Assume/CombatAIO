package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSTile;

import scripts.api.scriptapi.paint.LootedItemsDisplay;
import scripts.api.scriptapi.paint.PaintData;
import scripts.api.scriptapi.paint.PaintHandler;

final class LootPaintHandler extends PaintHandler {

	private List<RSGroundItem> paintable_items;
	private LootedItemsDisplay loot_item_display;

	public LootPaintHandler() {
		this.loot_item_display = new LootedItemsDisplay(null, 475, 12, true);
	}

	@Override
	public void update() {
		this.loot_item_display.update(PaintData.getLootItems());
	}

	@Override
	public void draw(Graphics g, long l) {
		/*
		 * g.setColor(Color.BLUE); Map<RSTile, List<RSGroundItem>> map = sort();
		 * for (RSTile tile : map.keySet()) { String[] unique_names =
		 * getUniqueNames(map.get(tile));
		 * g.drawPolygon(Projection.getTileBoundsPoly(tile, 0)); int i = 0; for
		 * (String y : unique_names) g.drawString(y, tile.getX(), tile.getY() +
		 * (i * 10)); }
		 */
		this.loot_item_display.draw(g, l);

	}

	private Map<RSTile, List<RSGroundItem>> sort() {
		Map<RSTile, List<RSGroundItem>> map = new HashMap<RSTile, List<RSGroundItem>>();
		for (RSGroundItem i : this.paintable_items) {
			RSTile pos = i.getPosition();
			if (map.containsKey(pos))
				map.get(pos).add(i);
			else {
				List<RSGroundItem> temp = new ArrayList<RSGroundItem>();
				temp.add(i);
				map.put(pos, temp);
			}
		}
		return map;
	}

	private String[] getUniqueNames(List<RSGroundItem> list) {
		List<String> tlist = new ArrayList<String>();
		for (RSGroundItem x : list) {
			RSItemDefinition def = x.getDefinition();
			if (def == null)
				continue;
			if (!tlist.contains(def.getName()))
				tlist.add(def.getName());
		}
		return tlist.toArray(new String[tlist.size()]);
	}

}
