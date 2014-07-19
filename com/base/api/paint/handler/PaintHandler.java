package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Point;

public interface PaintHandler {

	public void update();

	public void draw();

	public boolean isInClick(Point p);

	public void onClick(Point p);
}
