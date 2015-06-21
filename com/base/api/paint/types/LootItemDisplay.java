package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSModel;

public class LootItemDisplay extends Paintable<RSGroundItem> {

	private int x;
	private int y;

	public LootItemDisplay(RSGroundItem t, int x, int y) {
		super(t);
	}

	@Override
	public void draw(Graphics g) {
		if (this.isOpen()) {
			g.setFont(ARIAL_SIZE_ELEVEN);

		}
	}

	@Override
	protected void onClick() {
		// TODO open display if closed, close display if open

	}

	@Override
	protected boolean isInClick(Point p) {
		// TODO check if click is in click location
		return false;
	}

	@Override
	public void update(RSGroundItem t) {
		RSModel model = t.getModel();
		if (model != null) {
			Point center = model.getCentrePoint();
			this.x = (int) center.getX();
			this.y = (int) center.getY();
		}

	}

}
