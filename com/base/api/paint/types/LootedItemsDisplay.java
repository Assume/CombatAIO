package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;

import scripts.CombatAIO.com.base.api.paint.handler.TotalPaintHandler;
import scripts.CombatAIO.com.base.api.types.LootItem;

public class LootedItemsDisplay extends Paintable<LootItem[]> {

	private boolean draw_per_hour;

	private int x;
	private int y;
	private boolean move_left_on_overflow;

	public LootedItemsDisplay(LootItem[] t, int x, int y,
			boolean move_left_on_overflow) {
		super(t);
		this.draw_per_hour = false;
		this.x = x;
		this.y = y;
		this.move_left_on_overflow = move_left_on_overflow;
	}

	@Override
	public void draw(Graphics g) {
		if (super.get() == null)
			return;
		int space = 0;
		Arrays.sort(super.get());
		int tot_drawn = 0;
		for (LootItem loot_item : super.get()) {
			if (loot_item.getAmountLooted() > 0) {
				if (tot_drawn != 0 && tot_drawn % 8 == 0) {
					if (this.move_left_on_overflow)
						x -= 41;
					else
						x += 41;
					space = 0;
				}
				g.setColor(new Color(0, 0, 0, 110));
				g.fillRect(x, y + (40 * space), 38, 38);
				g.setColor(Color.RED);
				g.drawRect(x, y + (40 * space), 38, 38);
				g.drawImage(loot_item.getIcon(), x + 4, y + 3 + (40 * space),
						null, null);
				g.drawString(TotalPaintHandler.formatNumber(loot_item
						.getAmountLooted()), x + 4, y + 12 + (40 * space));
				tot_drawn++;
				space++;
			}
		}

	}

	@Override
	protected void onClick() {
		this.draw_per_hour = !draw_per_hour;

	}

	@Override
	protected boolean isInClick(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

}
