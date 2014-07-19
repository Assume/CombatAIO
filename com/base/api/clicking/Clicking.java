package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Point;
import java.util.ArrayList;
import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSNPC;

public class Clicking {
	private void focus(RSNPC p) {
		//TODO
	}
	
	public boolean click(Positionable p) {
		//TODO
		return false;
	}
	
	private boolean advancedClick(Point[] p) {
		//TODO
		return false;
	}
	
	public static Point[] standardDeviation(Point[] p) {
		General.println("Unfiltered Length: " + p.length);
		int meanX = 0, meanY = 0;
		for (int i = 0; i < p.length; i++) {
			meanX += p[i].getX();
			meanY += p[i].getY();
		}
		meanX  /= p.length;
		meanY /= p.length;
		ArrayList<Integer> squaredXDifferences = new ArrayList<Integer>();
		ArrayList<Integer> squaredYDifferences = new ArrayList<Integer>();
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
		General.println("Mean X: " + meanX + " STD X: " + stdX + " Mean Y: " + meanY + " STD Y: " + stdY);
		ArrayList<Point> finalPoints = new ArrayList<Point>();
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
		General.println("Filtered Length: " + pFinal.length);
		return pFinal;
	}
}
