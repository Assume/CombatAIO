package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public class Clicking {
	private static long lastBusyTime = System.currentTimeMillis();
	private static ABCUtil abc_util = new ABCUtil();

	private static boolean focus(RSNPC n, String action,
			boolean checkReachable, boolean safeClick) {
		if (n == null || n.getModel() == null) {
			return false;
		}
		if (checkReachable && !PathFinding.canReach(n, true))
			return false;
		if (!n.isOnScreen() && Player.getPosition().distanceTo(n) > 1) {
			RSTile tile = n.getPosition();
			Walking.setControlClick(true);
			Walking.blindWalkTo(tile);
			for (int i = 0; !Player.isMoving() && i < 10; i++) {
				General.sleep(25, 35);
			}
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
			if (safeClick) {
				while (Player.isMoving())
					General.sleep(30, 50);
			}
			if (advancedClick(n.getModel(), action)) {
				for (int i = 0; !Player.isMoving()
						&& Player.getAnimation() == -1 && i < 10; i++) {
					General.sleep(25, 35);
				}
				while (Player.isMoving()) {
					General.sleep(30, 70);
				}
				return true;
			}
		}
		return false;
	}

	private static boolean focus(RSObject n, String action,
			boolean checkReachable, boolean safeClick) {
		if (n == null || n.getModel() == null) {
			return false;
		}
		if (checkReachable && !PathFinding.canReach(n, true))
			return false;
		if (!n.isOnScreen() && Player.getPosition().distanceTo(n) > 1) {
			RSTile tile = n.getPosition();
			Walking.setControlClick(true);
			Walking.blindWalkTo(tile);
			for (int i = 0; !Player.isMoving() && i < 10; i++) {
				General.sleep(25, 35);
			}
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
			if (safeClick) {
				while (Player.isMoving())
					General.sleep(30, 50);
			}
			if (advancedClick(n.getModel(), action)) {
				for (int i = 0; !Player.isMoving()
						&& Player.getAnimation() == -1 && i < 10; i++) {
					General.sleep(25, 35);
				}
				while (Player.isMoving()) {
					General.sleep(30, 70);
				}
				return true;
			}
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
		if (Math.abs(dir) <= 210 && Math.abs(dir) >= 150) {
			return;
		}
		Camera.setCameraRotation(Camera.getCameraRotation()
				+ ((dir > 0 ^ Math.abs(dir) > 180) ? General.random(5, 20)
						: General.random(-5, -20)));
	}

	public static boolean click(RSNPC npc, String action,
			boolean checkReachable, boolean safeClick) {
		return focus(npc, action, checkReachable, safeClick);
	}

	public static boolean click(RSObject obj, String action,
			boolean checkReachable, boolean safeClick) {
		return focus(obj, action, checkReachable, safeClick);
	}

	public static boolean ABCLClick(RSObject obj, String option,
			boolean checkReachable, boolean safeClick, boolean combat) {
		waitNewOrSwitchDelay(lastBusyTime, combat);
		lastBusyTime = System.currentTimeMillis();
		return click(obj, option, checkReachable, safeClick);
	}

	public static boolean ABCLClick(RSNPC npc, String option,
			boolean checkReachable, boolean safeClick, boolean combat) {
		waitNewOrSwitchDelay(lastBusyTime, combat);
		lastBusyTime = System.currentTimeMillis();
		return click(npc, option, checkReachable, safeClick);
	}

	public static void waitNewOrSwitchDelay(final long last_busy_time,
			final boolean combat) {
		if (Timing.timeFromMark(last_busy_time) >= General.random(8000, 12000)) {
			if (combat) {
				General.sleep(abc_util.DELAY_TRACKER.NEW_OBJECT_COMBAT.next());

				abc_util.DELAY_TRACKER.NEW_OBJECT_COMBAT.reset();
			} else {
				General.sleep(abc_util.DELAY_TRACKER.NEW_OBJECT.next());

				abc_util.DELAY_TRACKER.NEW_OBJECT.reset();
			}
		} else {
			if (combat) {
				General.sleep(abc_util.DELAY_TRACKER.SWITCH_OBJECT_COMBAT
						.next());

				abc_util.DELAY_TRACKER.SWITCH_OBJECT_COMBAT.reset();
			} else {
				General.sleep(abc_util.DELAY_TRACKER.SWITCH_OBJECT.next());

				abc_util.DELAY_TRACKER.SWITCH_OBJECT.reset();
			}
		}
	}

	private static boolean advancedClick(RSModel m, String action) {
		MouseMovementThread thread = new MouseMovementThread(m, action);
		return thread.execute();
	}

	public static Point[] standardDeviation(Point[] p) {
		int meanX = 0, meanY = 0;
		for (int i = 0; i < p.length; i++) {
			meanX += p[i].getX();
			meanY += p[i].getY();
		}
		if (p.length > 0) {
			meanX /= p.length;
			meanY /= p.length;
		}
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
		if (squaredXDifferences.size() > 0)
			stdX = (int) Math.sqrt(stdX / squaredXDifferences.size());
		if (squaredYDifferences.size() > 0)
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
