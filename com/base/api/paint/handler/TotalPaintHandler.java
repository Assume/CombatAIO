package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.text.DecimalFormat;

import scripts.CombatAIO.com.base.api.paint.types.ButtonDisplay;
import scripts.CombatAIO.com.base.api.paint.types.DataDisplay;
import scripts.CombatAIO.com.base.api.paint.types.PaintData;
import scripts.CombatAIO.com.base.api.paint.types.PaintHandler;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;

public class TotalPaintHandler extends PaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private ExperiencePaintHandler experience_paint_handler;

	private ButtonDisplay show_gui_button;

	private ButtonDisplay hide_paint_button;

	private DataDisplay generic_data_display;

	private boolean show_paint = true;

	private String version;

	public TotalPaintHandler(String version) {
		this.version = version;
		this.experience_paint_handler = new ExperiencePaintHandler();
		this.monster_paint_handler = new MonsterPaintHandler();
		this.loot_paint_handler = new LootPaintHandler();
		this.show_gui_button = (new ButtonDisplay("Show GUI", 255, 428, 12) {
			@Override
			protected void onClick() {
				Dispatcher.get().getGUI().setVisible(true);
			}
		});

		this.hide_paint_button = (new ButtonDisplay("Hide", 320, 428, 12) {
			@Override
			protected void onClick() {
				setShowPaint();
			}
		});

		this.generic_data_display = new DataDisplay(new String[0], 255, 349,
				240, 76);

	}

	public void setValues(final String[] monster_ids, final String... loot_ids) {
		this.monster_paint_handler = new MonsterPaintHandler();
	}

	public void updateAll(long run_time) {
		this.generic_data_display.update(getGenericDataDisplay(run_time));
		this.updateLoot();
	}

	public void updateMonsters() {
		this.monster_paint_handler.update();
	}

	public void updateLoot() {
		this.loot_paint_handler.update();
	}

	private void setShowPaint() {
		this.show_paint = !show_paint;
		this.hide_paint_button.update(this.show_paint ? "Hide" : "Show");
	}

	private String[] getGenericDataDisplay(long run_time) {
		int kill_count = PaintData.getMonsterKills();
		int total_profit = PaintData.getProfit();

		String[] info_array = {
				"Runtime: " + getFormattedTime(run_time),
				"Kills: " + kill_count + " ("
						+ (int) ((3600000.0 / run_time) * kill_count) + "/HR)",
				"Profit: "
						+ formatNumber(total_profit)
						+ " ("
						+ formatNumber((int) ((3600000.0 / run_time) * total_profit))
						+ "/HR)",
				"Version" + (PaintData.isLite() ? "(Lite)" : "") + ": "
						+ version };
		return info_array;

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
		this.hide_paint_button.draw(g);
		if (!this.show_paint)
			return;
		this.show_gui_button.draw(g);
		this.generic_data_display.draw(g);
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

	@Override
	public void update() {

	}

}
