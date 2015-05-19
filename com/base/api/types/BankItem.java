package scripts.CombatAIO.com.base.api.types;

public class BankItem {

	private int id;
	private int amount;

	public BankItem(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return this.id;
	}

	public int getAmount() {
		return this.amount;
	}

}
