package scripts.CombatAIO.com.base.api.types;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.magic.books.NormalSpell;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class LootItem implements Comparable<LootItem> {

	private int price;
	private int amount_looted;
	private boolean alch;
	private String name;
	private Image icon;
	private int id;
	private boolean always_loot;

	public LootItem(String name, boolean always_loot, boolean alch) {
		this.price = 0;
		this.amount_looted = 0;
		char[] ar = name.toLowerCase().toCharArray();
		if (ar.length > 0) {
			ar[0] = Character.toUpperCase(ar[0]);
			this.name = new String(ar);
		}
		this.alch = alch;
		this.id = -1;
		this.icon = null;
		this.always_loot = always_loot;
	}

	public int getPrice() {
		return price;
	}

	public int getAmountLooted() {
		return this.amount_looted;
	}

	public int incrementAmountLooted(int by) {
		if (this.id == (Integer) Dispatcher.get().get(ValueType.AMMO_ID).getValue())
			return (this.amount_looted = 0);
		else
			return this.amount_looted += by;
	}

	public boolean shouldAlwaysLoot() {
		return this.always_loot;
	}

	public boolean shouldAlch() {
		return this.alch;
	}

	public void alch(RSItem x) {
		if (NormalSpell.HIGH_ALCHEMY.canCast())
			NormalSpell.HIGH_ALCHEMY.select();
		else if (NormalSpell.LOW_ALCHEMY.canCast())
			NormalSpell.LOW_ALCHEMY.select();
		else
			return;
		x.click("Cast");
		NPCChat.clickContinue(false);
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getAnimation() == -1;
			}
		}, General.random(600, 1100));
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setId(int id) {
		this.id = id;
		if (id == 995)
			this.price = 1;
		this.setImageAndPrice();
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Image getIcon() {
		return this.icon;
	}

	private void setImageAndPrice() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				icon = getIcon(id);
				if (price == 0)
					price = getPrice(id);
			}
		}, 0);
	}

	public static int getPrice(int id) {
		try {
			URL url = new URL("https://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent",
					"Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1");
			con.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			List<String> list = new ArrayList<String>();
			String ln;
			while ((ln = br.readLine()) != null)
				list.add(ln);

			if (list.size() > 0) {
				String[] work = list.get(0).split(",");
				if (work.length > 0)
					return Integer.parseInt(work[0].replaceAll("[^0-9]", ""));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static Image getIcon(int id) {
		try {
			URL url = new URL("http://cdn.rsbuddy.com/items/" + id + ".png");
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
	public int compareTo(LootItem arg0) {
		return this.getName().compareTo(arg0.getName());
	}

}
