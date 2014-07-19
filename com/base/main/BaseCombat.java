package scripts.CombatAIO.com.base.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import org.tribot.script.Script;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;

import scripts.CombatAIO.com.base.api.paint.handler.TotalPaintHandler;

public class BaseCombat extends Script implements Painting, MouseActions {

	private TotalPaintHandler paint_handler;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO set values for TotalPaintHandler (this.paint_handler) here after GUI done and what not
	}

	@Override
	public void onPaint(Graphics arg0) {
		if (this.paint_handler == null)
			this.paint_handler = new TotalPaintHandler((Graphics2D) arg0);
	}

	@Override
	public void mouseClicked(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Point arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

}
