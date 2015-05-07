package scripts.CombatAIO.com.base.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

import org.tribot.api.General;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;

import scripts.CombatAIO.com.base.api.paint.handler.TotalPaintHandler;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;

@ScriptManifest(authors = { "Assume" }, category = "CombatTesting", name = "BaseAIO")
public class BaseCombat extends Script implements Painting, MouseActions,
		Arguments {

	private TotalPaintHandler paint_handler;
	private boolean run = true;
	private String temp_monster_name;
	private String temp_food_name;

	public boolean isRunning() {
		return this.run;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO set values for TotalPaintHandler (this.paint_handler) here after
		// GUI done and what not
		General.useAntiBanCompliance(true);
		Dispatcher.create(this, 0);
		Dispatcher.get().set(ValueType.FOOD_NAME,
				new Value<String>(this.temp_food_name));
		Dispatcher.get().set(ValueType.MONSTER_NAMES,
				new Value<String[]>(new String[] { this.temp_monster_name }));
		Dispatcher.get().start();
		while (true) {
			General.sleep(300);
			Dispatcher.get().checkThreads();
			Dispatcher.get().getABCUtil().performTimedActions(SKILLS.STRENGTH);
		}

	}

	@Override
	public void onPaint(Graphics arg0) {
		if (this.paint_handler == null)
			this.paint_handler = new TotalPaintHandler((Graphics2D) arg0);
		this.paint_handler.draw(arg0);
	}

	@Override
	public void mouseClicked(Point arg0, int arg1, boolean arg2) {
		// this.paint_handler.onClick(arg0);

	}

	@Override
	public void mouseDragged(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Point arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void passArguments(HashMap<String, String> arg0) {
		String stuff = arg0.get("custom_input");
		this.temp_monster_name = stuff.split(",")[0].replace(",", "");
		this.temp_food_name = stuff.split(",")[1].replace(",", "");

	}

}
