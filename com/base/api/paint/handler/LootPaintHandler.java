package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.types.LootItem;

final class LootPaintHandler implements PaintHandler {

	private List<RSGroundItem> paintable_items;

	@Override
	public void update() {
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
		int space = 0;
		LootItem[] looted_items = PaintData.getLootItems();
		Arrays.sort(looted_items);
		int x = 475;
		int tot_drawn = 0;
		for (LootItem y : looted_items) {
			if (y.getAmountLooted() > 0) {
				if (tot_drawn != 00 && tot_drawn % 8 == 0) {
					x -= 41;
					space = 0;
				}
				g.setColor(new Color(0, 0, 0, 110));
				g.fillRect(x, 15 + 40 * space - 3, 38, 38);
				g.setColor(Color.RED);
				g.drawRect(x, 15 + 40 * space - 3, 38, 38);
				g.drawImage(y.getIcon(), x + 4, 15 + 40 * space, null, null);
				g.drawString(
						TotalPaintHandler.formatNumber(y.getAmountLooted()),
						x + 4, 24 + 40 * space);
				tot_drawn++;
				space++;
			}
		}
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

	@Override
	public void onClick(Point p) {
		// TODO
		/*
		 * for each RSGroundItem in this.paintable_items check if isInClick, if
		 * so call onClick
		 */
	}

}
