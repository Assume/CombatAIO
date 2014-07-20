package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSModel;

public class MouseMovementThread implements Runnable {

	private RSModel model;
	private boolean clicked = false;
	private String action;

	public MouseMovementThread(RSModel model, String action) {
		this.model = model;
		this.action = action;
	}

	@Override
	public void run() {
		focus(0);
		return;
	}

	private boolean focus(int attempt) {
		Point[] points = Clicking.standardDeviation(this.model
				.getVisiblePoints());
		if (points.length == 0)
			return false;
		Point click = points[General.random(0, points.length)];
		Mouse.move(click);
		if (canClick())
			click();
		else {
			if (attempt > 10)
				return false;
			focus(++attempt);
		}
		return clicked;
	}

	private void click() {
		if (Game.getUptext().contains(action)) {
			General.sleep(50, 150);
			if (Game.getUptext().contains(action)) {
				Mouse.click(1);
				return;
			}
		}
		if (model.getEnclosedArea().contains(Mouse.getPos())) {
			Mouse.click(3);
			if (ChooseOption.isOptionValid(action)) {
				ChooseOption.select(action);
			} else {
				ChooseOption.close();
			}
		}
	}

	private boolean canClick() {
		if (Game.getUptext().contains(action))
			return true;
		else
			return model.getEnclosedArea().contains(Mouse.getPos());

	}
}
