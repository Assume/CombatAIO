package scripts.CombatAIO.com.base.api.threading.types.subtype;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class StringValue extends Value<String> {

	private String value;

	public StringValue(String value) {
		this.value = value;
	}

	@Override
	public String get() {
		return value;
	}

}
