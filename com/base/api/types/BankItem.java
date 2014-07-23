package scripts.CombatAIO.com.base.api.types;

public class BankItem {

	private String name;
	private int amount;

	public BankItem(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}

	public String getName() {
		return this.name;
	}

	public int getAmount() {
		return this.amount;
	}

}
