package scripts.CombatAIO.com.base.main;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;

import scripts.CombatAIO.com.base.api.paint.handler.TotalPaintHandler;
import scripts.CombatAIO.com.base.api.paint.types.PaintData;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.threads.TrackingUpdater;
import scripts.CombatAIO.com.base.api.types.enums.SkillData;

@ScriptManifest(authors = { "Assume" }, category = "CombatTesting", name = "BaseAIO")
public class BaseCombat extends Script implements Painting, MouseActions,
		Arguments, MessageListening07, Ending {

	public static final String VERSION_NUMBER = "2.0.7_1";

	private TotalPaintHandler paint_handler;
	private Thread updater;
	private boolean run = true;
	private String name;

	public boolean isRunning() {
		return this.run;
	}

	@Override
	public void run() {
		// GUI done and what not
		this.paint_handler = new TotalPaintHandler(VERSION_NUMBER);
		General.useAntiBanCompliance(true);
		Dispatcher.create(this, 0);
		Dispatcher.get().start(name);
		// XMLWriter writer = new XMLWriter(Dispatcher.get());
		// writer.save(new File(Util.getAppDataDirectory() + File.separator
		// + "base_aio" + File.separator + "test.dat"), false,
		// Dispatcher.get());
		SkillData.updateAll();
		PaintData.updateAll();
		this.updater = new Thread(new TrackingUpdater(this));
		this.updater.setName("TRACKING UPDATER");
		this.updater.start();
		while (Dispatcher.get().shouldRun()) {
			General.sleep(300);
			Dispatcher.get().checkThreads();
			Dispatcher.get().getABCUtil().performTimedActions(SKILLS.STRENGTH);
			SkillData.updateAll();
			PaintData.updateAll();
			this.paint_handler.updateAll(getRunningTime());
		}

	}

	@Override
	public void onPaint(Graphics arg0) {
		try {
			this.paint_handler.draw(arg0, super.getRunningTime());
		} catch (Exception e) {

		}
	}

	@Override
	public void mouseClicked(Point arg0, int arg1, boolean arg2) {
		if (this.paint_handler != null)
			this.paint_handler.onClick(arg0);
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
		if (this.getRepoID() == 64)
			return;
		String scriptSelect = arg0.get("custom_input");
		String clientStarter = arg0.get("client_starter");
		String autoStart = arg0.get("autostart");
		String input;
		if (scriptSelect != null) {
			input = scriptSelect;
		} else if (clientStarter != null) {
			input = clientStarter;
		} else {
			input = autoStart;
		}
		this.name = input;

	}

	@Override
	public void clanMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void duelRequestReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void personalMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverMessageReceived(String arg0) {
		if (arg0 != null)
			if (arg0.contains("advanced")) {
				Keyboard.pressKeys(Keyboard.getKeyCode(' '));
				General.sleep(600, 700);
				Keyboard.pressKeys(Keyboard.getKeyCode(' '));
			}
		if (arg0.contains("decay"))
			Dispatcher.get().getCombatTask().pickupCannon();

	}

	@Override
	public void tradeRequestReceived(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd() {
		Dispatcher.get().getCombatTask().pickupCannon();
		if (Dispatcher.get().isLiteMode()) {
			try {
				Desktop.getDesktop()
						.browse(new URI(
								"https://tribot.org/repository/script/id/193-combataio-premium/"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			JOptionPane
					.showMessageDialog(null,
							"If you like this script, please check out CombatAIO Premium");
		}

	}

}
