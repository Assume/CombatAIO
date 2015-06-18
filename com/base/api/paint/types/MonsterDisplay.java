package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSNPC;

public class MonsterDisplay extends Paintable<RSNPC> {

	private boolean target;

	public MonsterDisplay(RSNPC t, boolean target) {
		super(t);
		this.target = target;
	}

	@Override
	public void draw(Graphics g) {
		try {
			Color c = Color.BLACK;
			if (this.target)
				c = Color.RED;
			g.setColor(c);
			if (super.get() != null) {
				Polygon x = Projection.getTileBoundsPoly(super.get(), 0);
				if (x != null)
					g.drawPolygon(x);
				g.drawString("HP: " + super.get().getHealth(), super.get()
						.getLocalX(), super.get().getLocalY());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public void update(RSNPC t) {
		// TODO Auto-generated method stub

	}

}
