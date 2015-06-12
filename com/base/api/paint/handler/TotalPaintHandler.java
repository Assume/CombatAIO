package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;

public class TotalPaintHandler implements PaintHandler {

	private static final String VERSION_NUMBER = "2.0.6_0";

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private ExperiencePaintHandler experience_paint_handler;

	private final static Color color1 = new Color(155, 155, 155, 110);
	private final static Color color2 = new Color(0, 0, 0);
	private final static Font font2 = new Font("Arial", 0, 11);

	private boolean show_paint = true;

	public TotalPaintHandler() {
		this.experience_paint_handler = new ExperiencePaintHandler();
		this.monster_paint_handler = new MonsterPaintHandler();
		this.loot_paint_handler = new LootPaintHandler();
	}

	public void setValues(final String[] monster_ids, final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler();
	}

	@Override
	public void onClick(Point p) {
		if (p.x >= 255 && p.x <= 321 && p.y >= 430 && p.y <= 441)
			Dispatcher.get().getGUI().setVisible(true);
		if (p.x >= 326 && p.x <= 364 && p.y >= 430 && p.y <= 441)
			this.setShowPaint();
		this.monster_paint_handler.onClick(p);
		this.loot_paint_handler.onClick(p);
	}

	public void updateAll() {
		// this.monster_paint_handler.update();
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

	@Override
	public void draw(Graphics g, long l) {
		g.setFont(font2);
		g.setColor(color1);
		g.fillRect(326, 430, 38, 11);
		g.setColor(color2);
		g.drawRect(326, 430, 38, 11);
		g.drawString("Hide", 336, 440);
		if (!this.show_paint)
			return;
		this.drawGenericPaint(g, l);
		this.monster_paint_handler.draw(g, l);
		this.loot_paint_handler.draw(g, l);
		this.experience_paint_handler.draw(g, l);

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

	private void drawGenericPaint(Graphics g, long run_time) {
		int kill_count = PaintData.getMonsterKills();
		int total_profit = PaintData.getProfit();

		g.setColor(new Color(0, 0, 0, 175));
		g.fillRect(255, 349, 240, 78);

		String[] infoArray = {
				"Runtime: " + getFormattedTime(run_time),
				"Kills: " + kill_count + " ("
						+ (int) ((3600000.0 / run_time) * kill_count) + "/HR)",
				"Profit: "
						+ formatNumber(total_profit)
						+ " ("
						+ formatNumber((int) ((3600000.0 / run_time) * total_profit))
						+ "/HR)",
				"Version" + (PaintData.isLite() ? "(Lite)" : "") + ": "
						+ VERSION_NUMBER };

		g.setFont(font2);
		int c = 0;
		for (String s : infoArray) {
			g.setColor(new Color(255, 255, 255, 150));
			int length = stringLength(s, g);
			g.fillRect(260, 354 + 17 * c, length + 20, 13);
			g.setColor(color2);
			g.drawRect(260, 354 + 17 * c, length + 20, 13);
			g.drawString(s, 270, 365 + 17 * c);
			c++;
		}
		g.setColor(color1);
		g.fillRect(255, 430, 66, 11);
		g.setColor(color2);
		g.drawRect(255, 430, 66, 11);
		g.drawString("Show GUI", 265, 440);
	}

	private static int stringLength(String s, Graphics g) {
		int x = 0;
		for (int c1 = 0; c1 < s.length(); c1++) {
			char ch = s.charAt(c1);
			x += g.getFontMetrics().charWidth(ch);
		}
		return x;
	}

	private void setShowPaint() {
		this.show_paint = !show_paint;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
