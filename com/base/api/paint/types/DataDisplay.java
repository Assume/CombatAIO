package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import scripts.CombatAIO.com.base.api.paint.handler.Paintable;

public class DataDisplay extends Paintable<String[]> {

	private int x;
	private int y;
	private int width;
	private int height;

	private DataDisplay(String[] t) {
		super(t);
	}

	public DataDisplay(String[] t, int x, int y, int width, int height) {
		this(t);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 175));
		g.fillRect(x, y, width, height);
		g.setFont(font2);
		int c = 0;
		for (String s : super.get()) {
			g.setColor(new Color(255, 255, 255, 150));
			int length = super.getStringLength(s, g);
			g.fillRect(x + 5, y + 5 + 17 * c, length + 20, 13);
			g.setColor(color2);
			g.drawRect(x + 5, y + 5 + 17 * c, length + 20, 13);
			g.drawString(s, 270, 365 + 17 * c);
			c++;
		}

	}

	@Override
	protected void onClick() {

	}

	@Override
	protected boolean isInClick(Point p) {
		return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
	}

}
