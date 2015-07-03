package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.api.scriptapi.paint.ButtonDisplay;
import scripts.api.scriptapi.paint.DataDisplay;
import scripts.api.scriptapi.paint.ExperienceDisplay;
import scripts.api.scriptapi.paint.PaintData;
import scripts.api.scriptapi.paint.PaintHandler;
import scripts.api.scriptapi.paint.Paintable;
import scripts.api.scriptapi.paint.SkillData;

public class TotalPaintHandler extends PaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootPaintHandler loot_paint_handler;

	private ButtonDisplay show_gui_button;

	private ButtonDisplay hide_paint_button;

	private DataDisplay generic_data_display;

	private ExperienceDisplay experience_display;

	private String version;

	public TotalPaintHandler(String version) {
		this.version = version;
		this.experience_display = new ExperienceDisplay(SkillData.COMBAT_TYPE);
		this.monster_paint_handler = new MonsterPaintHandler();
		this.loot_paint_handler = new LootPaintHandler();
		this.show_gui_button = (new ButtonDisplay(true, "Show GUI", 255, 428,
				57, 12) {
			@Override
			protected void onClick() {
				Dispatcher.get().getGUI().setVisible(true);
			}
		});

		this.hide_paint_button = (new ButtonDisplay(false, "Hide", 320, 428,
				28, 12) {
			@Override
			protected void onClick() {
				Paintable.setHide(!Paintable.getHide());
				hide_paint_button.update(Paintable.getHide() ? "Show" : "Hide");
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

	private String[] getGenericDataDisplay(long run_time) {
		int kill_count = PaintData.getMonsterKills();
		int total_profit = PaintData.getProfit();

		String[] info_array = {
				"Runtime: " + getFormattedTime(run_time),
				"Kills: " + kill_count + " ("
						+ (int) ((3600000.0 / run_time) * kill_count) + "/HR)",
				"Profit: "
						+ Paintable.formatNumber(total_profit)
						+ " ("
						+ Paintable
								.formatNumber((int) ((3600000.0 / run_time) * total_profit))
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
		Paintable.drawAll(g, l);
		this.monster_paint_handler.draw(g, l);
		this.loot_paint_handler.draw(g, l);
	}

	@Override
	public void update() {

	}

}
