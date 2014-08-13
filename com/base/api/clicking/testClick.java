package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

public class testClick extends Script implements Painting {
	Point[] stdPoints;
	Point[] points;

	@Override
	public void onPaint(Graphics g) {
		/*if (points != null && points.length > 0) {
			int[] xPoints = new int[points.length];
			int[] yPoints = new int[points.length];

			for (int i = 0; i < points.length; i++) {
				xPoints[i] = (int) points[i].getX();
				yPoints[i] = (int) points[i].getY();
			}
			g.setColor(Color.BLUE);
			g.drawPolygon(new Polygon(xPoints, yPoints, points.length));
		}
		if (stdPoints != null && stdPoints.length > 0) {
			int[] xPoints = new int[stdPoints.length];
			int[] yPoints = new int[stdPoints.length];

			for (int i = 0; i < stdPoints.length; i++) {
				xPoints[i] = (int) stdPoints[i].getX();
				yPoints[i] = (int) stdPoints[i].getY();
			}
			g.setColor(Color.RED);
			g.drawPolygon(new Polygon(xPoints, yPoints, stdPoints.length));
		}*/
	}

	@Override
	public void run() {
		int attempts = 0;
		int success = 0;
		while (true) {
			RSNPC[] birds = NPCs.findNearest("Crimson swift");
			if (birds.length > 0) {
				/*if (birds[0].isOnScreen()) {
					attempts ++;
					if (DynamicClicking.clickRSNPC(birds[0], "Examine Crimson swift")) {
						success++;
					};
				} else {
					General.sleep(30,50);
				}*/
				attempts++;
				if (Clicking.ABCLClick(birds[0], "Examine Crimson swift", false, false, false)) {
					success++;
				}
			}
			System.out.println("Click Success Rate: (" + success + "/"
					+ attempts + ")");
		}
	}
}
