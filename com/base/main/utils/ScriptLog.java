package scripts.CombatAIO.com.base.main.utils;

import java.util.Date;

public class ScriptLog {

	private int level;
	private Object message;
	private Date date;

	public ScriptLog(int level, Object message, Date date) {
		this.level = level;
		this.message = message;
		this.date = date;
	}

	public int getLevel() {
		return this.level;
	}

	public Object getMesage() {
		return this.message;
	}

	public Date getDate() {
		return this.date;
	}

	@Override
	public String toString() {
		return "[" + this.date.toString() + "] (" + this.level + ") "
				+ this.message == null ? "null" : this.message.toString();
	}

}
