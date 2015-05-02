package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

import scripts.priv.drennon.background.DAction;

public class DTrade implements DAction {

	private static final long serialVersionUID = 7529173846120252284L;

	private String rsn;
	private int[] ids, amount;

	public DTrade(String rsn, Integer[] ids, Integer[] amount) {
		this.rsn = rsn;
		this.ids = convert(ids);
		this.amount = convert(amount);
	}

	private int[] convert(Integer[] x) {
		int[] ne = new int[x.length];
		for (int i = 0; i < x.length; i++)
			ne[i] = x[i];
		return ne;
	}

	@Override
	public void execute() {
		waitForOtherPlayer();
	}

	private void waitForOtherPlayer() {
		RSPlayer[] players = null;
		long start = System.currentTimeMillis();
		while ((players = Players.find(rsn)).length == 0
				&& Timing.timeFromMark(start) < 1200000)
			General.sleep(500);
		trade(players[0]);
	}

	private void trade(RSPlayer player) {
		RSTile other_player_pos = player.getPosition();
		Walking.walkTo(other_player_pos);
		Camera.turnToTile(other_player_pos);
		while (!Clicking.click("Trade with " + player.getName(), player))
			General.sleep(1000, 2000);
		long t = System.currentTimeMillis();
		while (Trading.getWindowState() == null
				&& Timing.timeFromMark(t) < 30000)
			General.sleep(400, 1100);
		if (Trading.getWindowState() != Trading.WINDOW_STATE.FIRST_WINDOW)
			return;
		this.offerItems();
		long st = System.currentTimeMillis();
		long ran = General.random(20000, 35000);
		while (!Trading.hasAccepted(true) && Timing.timeFromMark(st) < ran)
			General.sleep(1000, 2000);
		General.sleep(1000, 5000);
		Trading.accept();
		General.sleep(1000, 2000);
		while (Trading.getWindowState() != Trading.WINDOW_STATE.SECOND_WINDOW
				&& Trading.getWindowState() != null)
			General.sleep(1000, 2000);
		General.sleep(1000, 3000);
		Trading.accept();
		General.sleep(1000, 3000);

	}

	private void offerItems() {
		for (int i = 0; i < this.ids.length; i++) {
			Trading.offer(amount[i], ids[i]);
		}
		Trading.accept();
	}

	@Override
	public String toString() {
		return "trade " + rsn;
	}

}
