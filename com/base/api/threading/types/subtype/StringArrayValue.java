package scripts.CombatAIO.com.base.api.threading.types.subtype;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class StringArrayValue extends Value<String[]> {

	private String[] value;

	public StringArrayValue(String[] value) {
		this.value = value;
	}

	@Override
	public String[] getValue() {
		return value;
	}
}