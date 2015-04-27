package scripts.CombatAIO.com.base.api.threading.types.subtype;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class BooleanValue extends Value<Boolean> {

	private boolean value;

	public BooleanValue(boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getValue() {
		return value;
	}
}