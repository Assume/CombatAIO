package scripts.CombatAIO.com.base.main.utils;

import org.tribot.api.General;

public class Logger {

	public static final int GENERIC = 0;
	public static final int WARNING = 1;
	public static final int CRITICAL = 2;
	public static final int SCRIPTER_ONLY = 3;

	private int warning_level;

	public static Logger logger;

	public static Logger getLogger() {
		return logger == null ? logger = new Logger() : logger;
	}

	private Logger() {
		this.warning_level = 0;
	}

	public void print(Object message) {
		print(GENERIC, message);
	}

	public void print(int level, Object message) {
		if (level < warning_level)
			return;
		int id = General.getTRiBotUserID();
		if (level == SCRIPTER_ONLY && (id != 10385 || id != 5359))
			return;
		System.out.println(message == null ? "null" : message.toString());
	}

	public void setWarningLevel(int level) {
		if (level < 0)
			warning_level = GENERIC;
		else if (level > 3)
			warning_level = SCRIPTER_ONLY;
		else
			warning_level = level;
	}
}
