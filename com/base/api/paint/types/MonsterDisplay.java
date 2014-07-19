package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Graphics2D;
import java.awt.Point;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.paint.handler.Paintable;

public class MonsterDisplay extends Paintable<RSNPC> {

	public MonsterDisplay(RSNPC t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void draw(Graphics2D g) {
		// TODO check if display is open (if we use that on RSNPC
		// otherwise override isOpen to always return false)
		//

	}

	@Override
	protected void onClick() {
		// TODO open display if closed, close display if open

	}

	@Override
	protected boolean isInClick(Point p) {
		// TODO check if click is in click location
		return false;
	}

}
