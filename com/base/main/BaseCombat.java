package scripts.CombatAIO.com.base.main;

import java.awt.Graphics;
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

@ScriptManifest(authors = { "Assume" }, category = "CombatTesting", name = "BaseAIO")
public class BaseCombat extends Script implements Painting, MouseActions,
		Arguments {

	private TotalPaintHandler paint_handler;
	private boolean run = true;

	public boolean isRunning() {
		return this.run;
	}

	@Override
	public void run() {
		// GUI done and what not
		General.useAntiBanCompliance(true);
		Dispatcher.create(this, 0);
		Dispatcher.get().start();
		// XMLWriter writer = new XMLWriter(Dispatcher.get());
		// writer.save(new File(Util.getAppDataDirectory() + File.separator
		// + "base_aio" + File.separator + "test.dat"), false,
		// Dispatcher.get());
		while (true) {
			General.sleep(300);
			Dispatcher.get().checkThreads();
			Dispatcher.get().getABCUtil().performTimedActions(SKILLS.STRENGTH);
			updateAllPaint();
		}

	}

	private void updateAllPaint() {
		this.paint_handler.updateAll();
	}

	@Override
	public void onPaint(Graphics arg0) {
		if (this.paint_handler == null)
			this.paint_handler = new TotalPaintHandler();
		try {
			this.paint_handler.draw(arg0);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	}

}
