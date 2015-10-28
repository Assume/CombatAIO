package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import org.tribot.api2007.types.RSNPC;

import scripts.api.scriptapi.paint.PaintHandler;
import scripts.api.scriptapi.paint.paintables.MonsterDisplay;

final class MonsterPaintHandler extends PaintHandler {

	private RSNPC current_target;
	private MonsterDisplay current_target_display;

	@Override
	public void update(long run_time) {
		if (this.current_target_display == null)
			this.current_target_display = new MonsterDisplay(
					this.current_target, true);
		else if (this.current_target_display.get() != this.current_target)
			this.current_target_display = new MonsterDisplay(
					this.current_target, true);
	}

	@Override
	public void draw(Graphics g, long l) {
		g.setColor(Color.BLACK);
		Polygon pol = PaintData.getTargetPolygon();
		if (pol != null)
			g.drawPolygon(pol);
	}

}
