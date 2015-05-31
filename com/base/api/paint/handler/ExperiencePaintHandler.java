package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.threading.types.enums.SkillData;

public class ExperiencePaintHandler implements PaintHandler {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	private final static Font font1 = new Font("Arial", 0, 9);

	@Override
	public void draw(Graphics g) {
		try {
			if (!Dispatcher.hasBeenInitialized())
				return;
			g.setFont(font1);
			int i = 0;
			for (final SkillData skill : SkillData.values()) {
				// Bar
				g.setColor(getCapeColor(100, skill));
				g.fillRect(8, 320 - 15 * i, 242, 13);

				// Progress
				g.setColor(getCapeColor(255, skill));
				g.fillRect(8, 320 - 15 * i,
						skill.getPercentToNextLevel() * 242 / 100, 13);

				// Trim
				g.setColor(getTrimColor(skill));
				g.drawRect(8, 320 - 15 * i, 242, 13);

				// Text
				g.drawString(
						toString((Long) Dispatcher.get()
								.get(ValueType.RUN_TIME).getValue(), skill),
						11, 331 - 15 * i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String toString(Long runtime, SkillData skill) {
		return (skill.toString() + " | "
				+ (skill.getLevelsGained() + skill.getStartingLevel()) + "("
				+ skill.getLevelsGained() + ") | "
				+ formatNumber(skill.getExperienceGained()) + " XP | "
				+ formatNumber(getExperiencePerHour(runtime, skill))
				+ " XP/HR | TTL: " + getFormattedTime(getTimeToLevel(runtime,
					skill)));
	}

	private int getExperiencePerHour(long runtime, SkillData skill) {
		return (int) ((3600000.0 / runtime) * skill.getExperienceGained());
	}

	private long getTimeToLevel(long runtime, SkillData skill) {
		long ttl = (long) ((skill.getExperienceToNextLevel() * 3600000.0) / getExperiencePerHour(
				runtime, skill));
		return ttl > 3000000 ? 0 : ttl;
	}

	private String formatNumber(int num) {
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

	private String getFormattedTime(long time) {
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		seconds = time / 1000;
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= (minutes * 60);
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= (hours * 60);
		}
		return (hours + ":" + minutes + ":" + seconds);
	}

	@Override
	public void onClick(Point p) {
		return;
	}

	private static Color getCapeColor(int alpha, SkillData skill) {
		switch (skill) {
		case ATTACK:
			return new Color(138, 17, 17, alpha);
		case STRENGTH:
			return new Color(29, 150, 67, alpha);
		case DEFENCE:
			return new Color(70, 74, 210, alpha);
		case RANGED:
			return new Color(87, 224, 74, alpha);
			// / MAGIC:
			// return new Color(200, 200, 200, alpha);
		case HITPOINTS:
			return new Color(200, 200, 200, alpha);
			// case SLAYER:
			// return new Color(69, 69, 69, alpha);
		}

		return null;
	}

	private static Color getTrimColor(SkillData skill) {
		switch (skill) {
		case ATTACK:
			return new Color(217, 195, 56);
		case STRENGTH:
			return new Color(138, 17, 17);
		case DEFENCE:
			return new Color(232, 232, 232);
		case RANGED:
			return new Color(0, 0, 0);
			// case MAGIC:
			// return new Color(92, 94, 214);
		case HITPOINTS:
			return new Color(138, 17, 17);
			// case SLAYER:
			// return new Color(110, 0, 0);
		}
		return null;
	}

}
