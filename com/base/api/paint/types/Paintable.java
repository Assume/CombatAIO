package scripts.CombatAIO.com.base.api.paint.types;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Paintable<T> {

	protected final static Color DARK_GREY = new Color(0, 0, 0, 175);
	
	protected final static Color VERY_LIGHT_GREY = new Color(155, 155, 155, 110);
	protected final static Font ARIAL_SIZE_ELEVEN = new Font("Arial", 0, 11);

	private static final List<Paintable<?>> list_of_paintables = new ArrayList<Paintable<?>>();

	private T t;
	private boolean isOpen;

	public Paintable(T t) {
		this.t = t;
		this.isOpen = false;
		Paintable.list_of_paintables.add(this);
	}

	public abstract void draw(Graphics g);

	protected abstract void onClick();

	protected abstract boolean isInClick(Point p);

	public void update(T t) {
		this.t = t;
	}

	public T get() {
		return this.t;
	}

	protected boolean isOpen() {
		return this.isOpen;
	}

	protected void setOpen() {
		this.isOpen = true;
	}

	protected void setCollapsed() {
		this.isOpen = false;
	}

	public static void onClick(Point p) {
		for (Paintable<?> x : list_of_paintables)
			if (x.isInClick(p))
				x.onClick();
	}

	protected static int getStringLength(String s, Graphics g) {
		int x = 0;
		for (int c1 = 0; c1 < s.length(); c1++) {
			char ch = s.charAt(c1);
			x += g.getFontMetrics().charWidth(ch);
		}
		return x;
	}
	
	public static String formatNumber(int num) {
		DecimalFormat df = new DecimalFormat("0");
		double i = num;
		if (i >= 1000000)
			if (i % 1000000 == 0)
				return df.format(i / 1000000) + "M";
			else
				return (i / 1000000) + "M";
		if (i >= 1000)
			return df.format((i / 1000)) + "k";
		return "" + num;
	}

}
