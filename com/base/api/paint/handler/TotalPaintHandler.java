package scripts.CombatAIO.com.base.api.paint.handler;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api2007.types.RSCharacter;

import scripts.CombatAIO.com.base.api.paint.handler.custom.LootedItemsDisplay;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.api.scriptapi.paint.PaintHandler;
import scripts.api.scriptapi.paint.Paintable;
import scripts.api.scriptapi.paint.SkillData;
import scripts.api.scriptapi.paint.paintables.DataDisplay;
import scripts.api.scriptapi.paint.paintables.ExperienceDisplay;
import scripts.api.scriptapi.paint.paintables.PaintPanel;
import scripts.api.scriptapi.paint.paintables.PaintTab;
import scripts.api.scriptapi.paint.paintables.RSCharacterHealthDisplay;
import scripts.api.scriptapi.paint.paintables.generic.HidePaintButton;
import scripts.api.scriptapi.paint.paintables.generic.ShowGUIButton;

public class TotalPaintHandler extends PaintHandler {

	private MonsterPaintHandler monster_paint_handler;

	private LootedItemsDisplay looted_items_display;

	private DataDisplay generic_data_display;

	private RSCharacterHealthDisplay target_health_display;

	private String version;

	public TotalPaintHandler(String version) {
		this.version = version;

		this.looted_items_display = new LootedItemsDisplay();
		this.generic_data_display = new DataDisplay();
		this.target_health_display = new RSCharacterHealthDisplay(null);

		new HidePaintButton().register(this);
		// new ExperienceDisplay(SkillData.COMBAT_TYPE).register(this);
		new ShowGUIButton(Dispatcher.get().getGUI()).register(this);

		this.looted_items_display.register(this);
		// this.generic_data_display.register(this);
		this.target_health_display.register(this);

		PaintPanel main_panel = new PaintPanel(230, 325, 265, 118);
		main_panel.register(this);

		PaintTab generic = new PaintTab("Generic", main_panel);
		generic.add(this.generic_data_display);
		generic.setDrawBackground(false);

		PaintTab experience = new PaintTab("Experience", main_panel);
		experience.add(new ExperienceDisplay(SkillData.COMBAT_TYPE, false));

		main_panel.addTab(generic);
		main_panel.addTab(experience);

		this.monster_paint_handler = new MonsterPaintHandler();

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
	}

	public boolean isInClick(Point p) {
		return super.isAnyInClick(p);
	}

}
