package scripts.CombatAIO.com.base.api.threading.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSPlayer;

import scripts.CombatAIO.com.base.api.paint.handler.PaintData;
import scripts.CombatAIO.com.base.api.types.enums.SkillData;
import scripts.CombatAIO.com.base.main.BaseCombat;

public class TrackingUpdater implements Runnable {

	private BaseCombat combat;

	public TrackingUpdater(BaseCombat combat) {
		this.combat = combat;
	}

	@Override
	public void run() {
		while (true) {
			update();
			General.sleep(300000);
		}
	}

	private void update() {
		RSPlayer p = Player.getRSPlayer();
		String name = (p == null ? "of" : p.getName().replaceAll(" ", "%20"));
		String data = " Tbot: " + General.getTRiBotUsername() + "| time: "
				+ Timing.msToString(combat.getRunningTime()) + " | exp"
				+ SkillData.getTotalExperienceGained() + " | profit: "
				+ PaintData.getProfit() + " | kills"
				+ PaintData.getMonsterKills() + "".replace(" ", "%20");
		this.updateTracker("update", name, "online", data.replace(" ", "%20"));

	}

	private String updateTracker(final String mode, final String botname,
			final String status, final String data) {

		final String unformattedURL = "http://polycoding.com/sigs/assume/tracker/index.php?"
				+ "mode=%s&" + "botname=%s&" + "status=%s&" + "data=%s";
		final String formattedURL = String.format(unformattedURL, mode,
				botname, status, data);
		URL url = null;
		String returnString = "";
		try {
			url = new URL(formattedURL);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String current;
			while ((current = in.readLine()) != null) {
				returnString += current.replace("<br/>", "\n").replace("<p>",
						"\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

}
