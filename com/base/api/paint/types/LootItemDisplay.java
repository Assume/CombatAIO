package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api2007.types.RSGroundItem;

import scripts.CombatAIO.com.base.api.paint.handler.Paintable;

public class LootItemDisplay extends Paintable<RSGroundItem> {

	public LootItemDisplay(RSGroundItem t) {
		super(t);
	}

	@Override
	public void draw(Graphics g) {
		// TODO check if display is open (if we use that on RSGroundItem
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

	@Override
	protected void update(RSGroundItem t) {
		// TODO Auto-generated method stub
		
	}

}
