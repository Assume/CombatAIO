package scripts.CombatAIO.com.base.api.paint.handler.custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;

import scripts.CombatAIO.com.base.api.types.LootItem;
import scripts.api.scriptapi.paint.Paintable;

public class LootedItemsDisplay extends Paintable<LootItem[]> {

	private boolean draw_per_hour;

	private boolean move_left_on_overflow;

	public LootedItemsDisplay() {
		this(new LootItem[0], 475, 12, true);
	}

	public LootedItemsDisplay(LootItem[] t, int x, int y,
			boolean move_left_on_overflow) {
		super(t, x, y, true);
		this.draw_per_hour = false;
		this.x = x;
		this.y = y;
		this.move_left_on_overflow = move_left_on_overflow;
	}

	@Override
	public void draw(Graphics g, long time) {
		if (super.get() == null)
			return;
		int space = 0;
		Arrays.sort(super.get());
		int temp_x = x;
		int tot_drawn = 0;
		for (LootItem loot_item : super.get()) {
			if (loot_item.getAmountLooted() > 0) {
				if (tot_drawn != 0 && tot_drawn % 8 == 0) {
					if (this.move_left_on_overflow)
						temp_x -= 41;
					else
						temp_x += 41;
					space = 0;
				}
				g.setColor(new Color(0, 0, 0, 110));
				g.fillRect(temp_x, y + (40 * space), 38, 38);
				g.setColor(Color.RED);
				g.drawRect(temp_x, y + (40 * space), 38, 38);
				g.drawImage(loot_item.getIcon(), temp_x + 4, y + 3
						+ (40 * space), null, null);
				g.drawString(formatNumber(loot_item.getAmountLooted()),
						temp_x + 4, y + 12 + (40 * space));
				tot_drawn++;
				space++;
			}
		}
	}

	private int getNumberOfItemsThatHaveBeenLooted() {
		int count = 0;
		for (LootItem x : super.get())
			if (x.getAmountLooted() > 0)
				count++;
		return count;
	}

	@Override
	protected void onClick(Point p) {
		this.setOpen(false);
		this.open_button.setOpen(true);
	}

	@Override
	protected boolean isInClick(Point p) {
		int total_looted = getNumberOfItemsThatHaveBeenLooted();
		if (total_looted == 0)
			return false;
		int total_columns = (int) Math.ceil((double) total_looted / 8.0);
		for (int i = 0; i < total_columns; i++) {
			int num_items_in_column = i == 0 ? total_looted
					: (total_looted * (i * 8)) % 8;
			Rectangle rec = this.move_left_on_overflow ? new Rectangle(x
					- (41 * i), y, 41, (num_items_in_column * 38)
					+ (num_items_in_column * 2)) : new Rectangle(x + (41 * i),
					y, 41, (num_items_in_column * 38)
							+ (num_items_in_column * 2));
			if (rec.contains(p))
				return true;
			else
				continue;
		}
		return false;
	}

	/*
	 * if (p.x >= x - (41 * i) && p.x <= p.x - (41 * i) - 38 && p.y >= y && p.y
	 * <= y + (num_items_in_column * 38) + (num_items_in_column * 2)) return
	 * true; else continue;
	 */

	@Override
	public int getWidth() {
		return getNumberOfItemsThatHaveBeenLooted() * 41;
	}

	@Override
	public int getHeight() {
		int num = getNumberOfItemsThatHaveBeenLooted();
		if (num >= 8)
			return 8 * 40;
		else
			return num * 40;
	}
}
