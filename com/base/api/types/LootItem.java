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

public class LootItem implements Comparable<LootItem> {

	public static void main(String[] args) {
		Image i = getIcon(314);
	}

	private int price;
	private int amount_looted;
	private boolean alch;
	private String name;
	private Image icon;
	private int id;

	public LootItem(String name) {
		this(name, false);
	}

	public LootItem(String name, boolean alch) {
		this.price = 0;
		this.amount_looted = 0;
		this.name = name;
		this.alch = alch;
		this.id = -1;
		this.icon = null;
	}

	public int getPrice() {
		if (this.price == -1)
			return price;
		if (this.price == 0) {
			// disptach zybez thread and return value
		}
		return price;
	}

	public int getAmountLooted() {
		return this.amount_looted;
	}

	public int incrementAmountLooted(int by) {
		return this.amount_looted += by;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setId(int id) {
		this.id = id;
		this.setImage();
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

	private void setImage() {
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
			URL url = new URL(
					"https://api.rsbuddy.com/grandExchange?a=guidePrice&i="
							+ id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent",
					"Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1");
			con.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
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
