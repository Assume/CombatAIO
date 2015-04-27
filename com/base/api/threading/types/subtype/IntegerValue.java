package scripts.CombatAIO.com.base.api.threading.types.subtype;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class IntegerValue extends Value<Integer> {

	private Integer x;

	public IntegerValue(int x) {
		this.x = x;
	}

	@Override
	public Integer getValue() {
		return x;
	}

}
