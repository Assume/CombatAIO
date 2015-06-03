package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Point;

public interface PaintHandler {

	public void update();

	public void draw(Graphics g, long run_time);

	public void onClick(Point p);
}
