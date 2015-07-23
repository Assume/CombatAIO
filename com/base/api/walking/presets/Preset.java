package scripts.CombatAIO.com.base.api.walking.presets;

import scripts.CombatAIO.com.base.api.types.BankItem;

public abstract class Preset {

	private String name;
	private BankItem[] required_items;
	private String requirements;

	public Preset(String name, String requirements, BankItem... required_items) {
		this.name = name;
		this.required_items = required_items;
		this.requirements = requirements;
	}

	public String getName() {
		return this.name;
	}

	public String getRequirements() {
		return requirements;
	}

	public BankItem[] getRequiredItems() {
		return this.required_items;
	}

	public abstract void executeToBank();

	public abstract void executeToMonster();
}
