package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api2007.types.RSCharacter;

import scripts.CombatAIO.com.base.api.paint.handler.custom.LootedItemsDisplay;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.CombatAIO.com.base.main.utils.Logger;
import scripts.api.scriptapi.paint.Calculations;
import scripts.api.scriptapi.paint.PaintHandler;
import scripts.api.scriptapi.paint.SkillData;
import scripts.api.scriptapi.paint.paintables.DataDisplay;
import scripts.api.scriptapi.paint.paintables.ExperienceDisplay;
import scripts.api.scriptapi.paint.paintables.RSCharacterHealthDisplay;
import scripts.api.scriptapi.paint.paintables.generic.HidePaintButton;
import scripts.api.scriptapi.paint.paintables.generic.ShowGUIButton;
import scripts.api.scriptapi.paint.paintables.tabs.PaintPanel;
import scripts.api.scriptapi.paint.paintables.tabs.PaintTab;

public class TotalPaintHandler extends PaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootedItemsDisplay looted_items_display;

	private DataDisplay generic_data_display;

	private RSCharacterHealthDisplay target_health_display;

	private DataDisplay logs_data_display;

	private DataDisplay abc_logs_data_display;

	private String version;

	public TotalPaintHandler(String version) {
		this.version = version;

		this.looted_items_display = new LootedItemsDisplay();
		this.generic_data_display = new DataDisplay();
		this.target_health_display = new RSCharacterHealthDisplay();
		this.logs_data_display = new DataDisplay();
		this.abc_logs_data_display = new DataDisplay();

		new HidePaintButton().register(this);
		new ShowGUIButton(Dispatcher.get().getGUI()).register(this);

		this.looted_items_display.register(this);
		this.target_health_display.register(this);

		PaintPanel main_panel = new PaintPanel(230, 325, 265, 118);
		main_panel.register(this);

		PaintTab tab_general = new PaintTab("General", main_panel);
		tab_general.add(this.generic_data_display);
		tab_general.setDrawBackground(false);

		PaintTab tab_experience = new PaintTab("Experience", main_panel);
		tab_experience.add(new ExperienceDisplay(SkillData.COMBAT_TYPE, false));

		PaintTab tab_ABCV2_logs = new PaintTab("ABCv2 Logs", main_panel);
		tab_ABCV2_logs.add(this.abc_logs_data_display);
		tab_ABCV2_logs.setDrawBackground(false);

		main_panel.addTab(tab_general);
		main_panel.addTab(tab_experience);
		main_panel.addTab(tab_ABCV2_logs);

		PaintPanel scripter_panel = new PaintPanel(5, 50, 180, 350);
		if (Logger.isScripter())
			scripter_panel.register(this);
		PaintTab tab_logger = new PaintTab("Logger", scripter_panel);
		tab_logger.add(this.logs_data_display);
		tab_logger.setDrawBackground(false);

		scripter_panel.addTab(tab_logger);

		this.monster_paint_handler = new MonsterPaintHandler();

	}

	private String[] getABCLogs() {
		return Logger.getLogger().getAllLogsAsString(Logger.ABC_LOGS, 5);
	}

	private String[] getLoggerMessages() {
		return Logger.getLogger().getAllLogsAsString(Logger.SCRIPTER_ONLY, 15);
	}

	private String[] getGenericDataDisplay(long run_time) {
		int kill_count = PaintData.getMonsterKills();
		int total_profit = PaintData.getProfit();
		int total_experience = SkillData.getTotalExperienceGained();

		String[] info_array = {
				"Runtime: " + getFormattedTime(run_time),
				"Kills: " + kill_count + " ("
						+ (int) ((3600000.0 / run_time) * kill_count) + "/HR)",
				"Profit: "
						+ Calculations.formatNumber(total_profit)
						+ " ("
						+ Calculations
								.formatNumber((int) ((3600000.0 / run_time) * total_profit))
						+ "/HR)",
				"Version" + (PaintData.isLite() ? "(Lite)" : "") + ": "
						+ version,
				"Experience: "
						+ Calculations.formatNumber(total_experience)
						+ " ("
						+ Calculations.formatNumber(Calculations.getPerHour(
								total_experience, run_time)) + "/HR)" };
		return info_array;

	}

	@Override
	public void draw(Graphics g, long l) {
		super.drawAll(g, l);
		this.monster_paint_handler.draw(g, l);
	}

	@Override
	public void update(long run_time) {
		this.generic_data_display.update(getGenericDataDisplay(run_time));
		this.looted_items_display.update(PaintData.getLootItems());
		this.target_health_display.update((RSCharacter) Dispatcher.get()
				.get(ValueType.CURRENT_TARGET).getValue());
		this.logs_data_display.update(getLoggerMessages());
		this.abc_logs_data_display.update(getABCLogs());
	}

	public boolean isInClick(Point p) {
		return super.isAnyInClick(p);
	}

}
