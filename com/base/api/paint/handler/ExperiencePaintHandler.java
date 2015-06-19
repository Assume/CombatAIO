package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import scripts.CombatAIO.com.base.api.paint.types.PaintHandler;
import scripts.CombatAIO.com.base.api.types.enums.SkillData;

public class ExperiencePaintHandler extends PaintHandler {

	@Override
	public void update() {

	}

	private final static Font font1 = new Font("Arial", 0, 9);
	private static Color inside = new Color(Color.BLACK.getRed(),
			Color.BLACK.getGreen(), Color.BLACK.getBlue(), 125);

	@Override
	public void draw(Graphics g, long l) {
		g.setFont(font1);
		int i = 0;
		for (final SkillData skill : SkillData.values()) {
			if (skill.getExperienceGained() > 0) {
				// Bar
				// g.setColor(getCapeColor(100, skill));
				g.setColor(Color.GRAY);
				g.fillRect(8, 320 - 16 * i, 242, 13);

				// Progress
				// g.setColor(getCapeColor(255, skill));
				g.setColor(inside);
				g.fillRect(8, 320 - 16 * i,
						skill.getPercentToNextLevel() * 242 / 100, 13);

				// Trim
				// g.setColor(getTrimColor(skill));
				g.setColor(Color.BLACK);
				g.drawRect(8, 320 - 16 * i, 242, 13);

				// Text
				g.setColor(Color.WHITE);
				g.drawString(toString(l, skill), 11, 331 - 16 * i);
				i++;
			}
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
		return ttl > 300000000 ? 0 : ttl;
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

}
