package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

public class TotalPaintHandler {

	private static final String VERSION_NUMBER = "0.0.4_4";

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private ExperiencePaintHandler experience_paint_handler;

	private final static Color color2 = new Color(0, 0, 0);
	private final static BasicStroke stroke1 = new BasicStroke(1);
	private final static Font font2 = new Font("Arial", 0, 11);

	public TotalPaintHandler() {
		this.experience_paint_handler = new ExperiencePaintHandler();
		this.monster_paint_handler = new MonsterPaintHandler();
		this.loot_paint_handler = new LootPaintHandler();
	}

	public void setValues(final String[] monster_ids, final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler();
	}

	public void onClick(Point p) {
		this.monster_paint_handler.onClick(p);
		this.loot_paint_handler.onClick(p);
	}

	public void updateAll() {
		this.monster_paint_handler.update();
		this.loot_paint_handler.update();
	}

	public void updateMonsters() {
		this.monster_paint_handler.update();
	}

	public void updateLoot() {
		this.loot_paint_handler.update();
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

	public void draw(Graphics arg0) {
		try {
			if (Dispatcher.get() == null)
				return;
			if (!Dispatcher.get().hasStarted())
				return;
			if (Dispatcher.get().hasStarted()) {
				this.drawGenericPaint(arg0);
				this.monster_paint_handler.draw(arg0);
				this.loot_paint_handler.draw(arg0);
				this.experience_paint_handler.draw(arg0);
			}
		} catch (Exception e) {

		}
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

	private void drawGenericPaint(Graphics g) {
		long run_time = (Long) Dispatcher.get().get(ValueType.RUN_TIME)
				.getValue();
		int kill_count = (Integer) Dispatcher.get().get(ValueType.TOTAL_KILLS)
				.getValue();
		int total_profit = (Integer) Dispatcher.get()
				.get(ValueType.TOTAL_LOOT_VALUE).getValue();

		g.setColor(new Color(0, 0, 0, 175));
		g.fillRect(255, 361, 240, 78);

		String[] infoArray = {
				"Runtime: " + getFormattedTime(run_time),
				"Kills: " + kill_count + " ("
						+ (int) ((3600000.0 / run_time) * kill_count) + "/HR)",
				"Profit: "
						+ formatNumber(total_profit)
						+ " ("
						+ formatNumber((int) ((3600000.0 / run_time) * total_profit))
						+ "/HR)", "Version: " + VERSION_NUMBER };

		g.setFont(font2);
		int c = 0;
		for (String s : infoArray) {
			g.setColor(new Color(255, 255, 255, 150));
			int length = stringLength(s, g);
			g.fillRect(260, 366 + 17 * c, length + 20, 12);
			g.setColor(color2);
			((Graphics2D) g).setStroke(stroke1);
			g.drawRect(260, 366 + 17 * c, length + 20, 12);
			g.drawString(s, 270, 376 + 17 * c);
			c++;
		}
	}

	private static int stringLength(String s, Graphics g) {
		int x = 0;
		for (int c1 = 0; c1 < s.length(); c1++) {
			char ch = s.charAt(c1);
			x += g.getFontMetrics().charWidth(ch);
		}
		return x;
	}

}
