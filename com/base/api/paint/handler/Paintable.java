package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Paintable<T> {

	private T t;
	private boolean isOpen;

	public Paintable(T t) {
		this.t = t;
		this.isOpen = false;
	}

	protected abstract void draw(Graphics2D g);

	protected abstract void onClick();

	protected abstract boolean isInClick(Point p);

	protected T get() {
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

}
