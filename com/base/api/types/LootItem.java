package scripts.CombatAIO.com.base.api.types;

public class LootItem {

	private int price;
	private int amount_looted;
	private String name;

	public LootItem(String name) {
		this.price = 0;
		this.amount_looted = 0;
		this.name = name;
	}

	public int getPrice() {
		if (this.price == -1)
			return price;
		if (this.price == 0) {
			// disptach zybez thread and return 0
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

	public String getName() {
		return this.name;
	}
}
