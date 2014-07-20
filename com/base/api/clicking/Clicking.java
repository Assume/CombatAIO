package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

public class Clicking {
	public static boolean focus(RSNPC n, String action, boolean checkReachable) {
		if (n == null || n.getModel() == null) {
			return false;
		}
		if (checkReachable && !PathFinding.canReach(n, true))
			return false;
		if (!n.isOnScreen() && Player.getPosition().distanceTo(n) > 1) {
			RSTile tile = n.getPosition();
			Walking.setControlClick(true);
			Walking.blindWalkTo(tile);
			General.sleep(250, 350);
			while (Player.isMoving() && !n.isOnScreen()) {
				turnTo(tile);
			}
			if (!n.isOnScreen()) {
				Camera.turnToTile(n);
			}
		}
		if (!n.isOnScreen()) {
			return false;
		}
		if (n.getModel() != null) {
			return advancedClick(n.getModel(), action);
		}
		return false;
	}

	private static void turnTo(final Positionable loc) {
		if (loc == null) {
			return;
		}
		final int cAngle = Camera.getCameraRotation();
		final int angle = 180 + Camera.getTileAngle(loc);
		final int dir = cAngle - angle;
		if (Math.abs(dir) <= 190 && Math.abs(dir) >= 180) {
			return;
		}
		Camera.setCameraRotation(Camera.getCameraRotation()
				+ ((dir > 0 ^ Math.abs(dir) > 180) ? 10 : -10));
	}

	public static boolean click(RSNPC npc, String action) {
		return focus(npc, action, false);
	}

	private static boolean advancedClick(RSModel m, String action) {
		MouseMovementThread thread = new MouseMovementThread(m, action);
		return thread.execute();
	}

	public static Point[] standardDeviation(Point[] p) {
		long startTime = System.currentTimeMillis();
		int meanX = 0, meanY = 0;
		for (int i = 0; i < p.length; i++) {
			meanX += p[i].getX();
			meanY += p[i].getY();
		}
		meanX /= p.length;
		meanY /= p.length;
		List<Integer> squaredXDifferences = new ArrayList<Integer>();
		List<Integer> squaredYDifferences = new ArrayList<Integer>();
		for (int i = 0; i < p.length; i++) {
			squaredXDifferences.add((int) Math.pow(p[i].getX() - meanX, 2));
			squaredYDifferences.add((int) Math.pow(p[i].getY() - meanY, 2));
		}
		int stdX = 0, stdY = 0;
		for (int i = 0; i < squaredXDifferences.size(); i++) {
			stdX += squaredXDifferences.get(i);
			stdY += squaredYDifferences.get(i);
		}
		stdX = (int) Math.sqrt(stdX / squaredXDifferences.size());
		stdY = (int) Math.sqrt(stdY / squaredYDifferences.size());
		List<Point> finalPoints = new ArrayList<Point>();
		for (int i = 0; i < p.length; i++) {
			int xVal = (int) p[i].getX();
			int yVal = (int) p[i].getY();
			if (xVal >= (meanX - stdX) && xVal <= (meanX + stdX)
					&& yVal >= (meanY - stdY) && yVal <= (meanY + stdY)) {
				finalPoints.add(p[i]);
			}
		}
		Point[] pFinal = new Point[finalPoints.size()];
		pFinal = finalPoints.toArray(pFinal);
		return pFinal;
	}
}
