package scripts.CombatAIO.com.base.api.walking.presets;

import scripts.CombatAIO.com.base.api.types.BankItem;

public abstract class Preset {

	private String name;
	private BankItem[] required_items;

	public Preset(String name, BankItem... required_items) {
		this.name = name;
		this.required_items = required_items;
	}

	public String getName() {
		return this.name;
	}

	public BankItem[] getRequiredItems() {
		return this.required_items;
	}

	public abstract void executeToBank();

	public abstract void executeToMonster();
}
