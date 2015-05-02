package scripts.CombatAIO.com.base.api.general.walking.custom.background.conditions;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DCondition;

public enum DConditionMaker {

	TEST {
		@Override
		public DCondition make() {
			return null;
		}
	};

	public abstract DCondition make();

	private static int getInt(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9-]", ""));
	}

	@Override
	public String toString() {
		return this.name().replaceFirst("D", "");
	}

}
