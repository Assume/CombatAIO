package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSModel;

public class MouseMovementThread extends Thread {

	private RSModel model;
	private boolean clicked = false;
	private String action;

	public MouseMovementThread(RSModel model, String action) {
		this.model = model;
		this.action = action;
	}

	public boolean execute() {
		int initial_speed = Mouse.getSpeed();
		Mouse.setSpeed(initial_speed + 2);
		start();
		while (isAlive())
			General.sleep(100);
		Mouse.setSpeed(initial_speed);
		return clicked;
	}

	@Override
	public void run() {
		focus(0);
		return;
	}

	private boolean focus(int attempt) {
		Point[] visible = this.model.getVisiblePoints();
		if (visible.length > 0) {
			Point[] points = Clicking.standardDeviation(visible);
			if (points == null || points.length == 0)
				return false;
			Point click = points[General.random(0, points.length - 1)];
			if (canClick()) {
				click();
			} else {
				Mouse.move(click);
				for (int fSafe = 0; fSafe < 20
						&& !Game.getUptext().toLowerCase()
								.contains(action.toLowerCase()); fSafe++)
					General.sleep(10, 15);
				if (canClick())
					click();
				else {
					if (attempt > 10)
						return false;
					Mouse.setSpeed(Mouse.getSpeed() + 2);
					focus(++attempt);
				}
			}
			return clicked;
		} else {
			General.println("no visible points");
			return false;
		}
	}

	private void click() {
		if (Game.getUptext().toLowerCase().contains(action.toLowerCase())) {
			General.sleep(50, 150);
			if (Game.getUptext().toLowerCase().contains(action.toLowerCase())) {
				Mouse.click(1);
				if (Timing.waitCrosshair(600)==2) {
					this.clicked = true;
				}
				return;
			}
		}
		if (model.getEnclosedArea().contains(Mouse.getPos())) {
			Mouse.click(3);
			if (ChooseOption.isOptionValid(action)) {
				this.clicked = ChooseOption.select(action);
			} else {
				this.clicked = false;
				ChooseOption.close();
			}
		}
	}

	private boolean canClick() {
		if (Game.getUptext().toLowerCase().contains(action.toLowerCase()))
			return true;
		else
			return model.getEnclosedArea().contains(Mouse.getPos());

	}
}
