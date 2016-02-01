package scripts.CombatAIO.com.base.main;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.Walking;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.EventBlockingOverride;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.MousePainting;
import org.tribot.script.interfaces.Painting;

import scripts.CombatAIO.com.base.api.paint.handler.PaintData;
import scripts.CombatAIO.com.base.api.paint.handler.TotalPaintHandler;
import scripts.CombatAIO.com.base.api.tasks.threads.TrackingUpdater;
import scripts.api.scriptapi.paint.SkillData;

@ScriptManifest(authors = { "Assume" }, category = "CombatTesting", name = "BaseAIO")
public class BaseCombat extends Script implements Painting, MousePainting,
		Arguments, MessageListening07, Ending, EventBlockingOverride {

	public static final String VERSION_NUMBER = "2.1.0_1";
	private TotalPaintHandler paint_handler;
	private Thread updater;
	private boolean run = true;
	private String name;

	private Image cursor;

	public boolean isRunning() {
		return this.run;
	}

	@Override
	public void run() {
		this.cursor = getCursor();
		ThreadSettings.get().setClickingAPIUseDynamic(true);
		General.useAntiBanCompliance(true);
		Dispatcher.create(this);
		Dispatcher.get().start(name);
		this.paint_handler = new TotalPaintHandler(VERSION_NUMBER);
		SkillData.updateAll();
		PaintData.updateAll();
		this.updater = new Thread(new TrackingUpdater(this));
		this.updater.setName("TRACKING UPDATER");
		this.updater.start();
		Walking.setWalkingTimeout(5000);
		while (Dispatcher.get().shouldRun()) {
			General.sleep(300);
			Dispatcher.get().checkThreads();
			Dispatcher.get().getABCUtil().performTimedActions(SKILLS.STRENGTH);
			SkillData.updateAll();
			PaintData.updateAll();
			this.paint_handler.update(getRunningTime());
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
			Dispatcher.get().getCombatTask().setCannonDecayed();

	}

	@Override
	public void tradeRequestReceived(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd() {
		if (Dispatcher.get().getCombatTask().isUsingCannon())
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

	private static Image getCursor() {
		try {
			URL url = new URL(
					"http://telcontar.net/Misc/screeniecursors/Cursor%20arrow%20white.png");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent",
					"Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1");
			con.connect();
			return ImageIO.read(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void paintMouse(Graphics arg0, Point arg1, Point arg2) {
		arg0.drawImage(this.cursor, arg1.x, arg1.y, null, null);
	}

	@Override
	public EventBlockingOverride.OVERRIDE_RETURN overrideKeyEvent(KeyEvent arg0) {
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

	@Override
	public EventBlockingOverride.OVERRIDE_RETURN overrideMouseEvent(MouseEvent e) {
		if (this.paint_handler != null
				&& this.paint_handler.isAnyInClick(e.getPoint())) {
			if (e.getID() == 500) {
				e.consume();
				this.paint_handler.onClick(e.getPoint());
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			} else if (e.getID() == 501) {
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			}
		}
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

}
