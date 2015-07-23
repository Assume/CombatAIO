package scripts.CombatAIO.com.base.api.presets;

import scripts.CombatAIO.com.base.api.types.BankItem;

public abstract class Preset {

	private BankItem[] required_items;
	private String requirements;

	public Preset(String requirements, BankItem... required_items) {
		this.required_items = required_items;
		this.requirements = requirements;
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
