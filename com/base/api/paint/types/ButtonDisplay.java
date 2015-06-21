package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class ButtonDisplay extends Paintable<String> {

	private int x;
	private int y;
	private int height;

	private Graphics g;

	public ButtonDisplay(String t, int x, int y, int height) {
		super(t);
		this.x = x;
		this.y = y;
		this.height = height;
	}

	@Override
	public void draw(Graphics g) {
		this.g = g;
		int length = super.getStringLength(super.get(), g);
		length += getTotalLengthAddition(length);
		g.setColor(VERY_LIGHT_GREY);
		g.setFont(ARIAL_SIZE_ELEVEN);
		g.fillRect(x, y, length, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, length, height);
		g.drawString(super.get(), x + getAddition(length), y + 10);
	}

	private int getTotalLengthAddition(int length) {
		if (length < 20)
			return length / 14;
		if (length < 40)
			return length / 11;
		if (length < 60)
			return length / 7;
		return length / 5;
	}

	private int getAddition(int total_length) {
		if (total_length < 20)
			return 3;
		if (total_length < 60)
			return 4;
		if (total_length < 100)
			return 5;
		return 6;
	}

	@Override
	protected boolean isInClick(Point p) {
		return p.x >= x && p.x <= super.getStringLength(super.get(), g) + x
				&& p.y >= y && p.y <= y + height;
	}

}
