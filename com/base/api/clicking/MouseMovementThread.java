package scripts.CombatAIO.com.base.api.clicking;

import java.awt.Point;

import org.tribot.api.General;
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
			Mouse.setSpeed(Mouse.getSpeed() + 2);
			focus(++attempt);
		}
		return clicked;
	}

	private void click() {
		if (Game.getUptext().contains(action)) {
			General.sleep(50, 150);
			if (Game.getUptext().contains(action)) {
				Mouse.click(1);
				this.clicked = true;
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
		if (Game.getUptext().contains(action))
			return true;
		else
			return model.getEnclosedArea().contains(Mouse.getPos());

	}
}
