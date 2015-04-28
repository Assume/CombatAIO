package scripts.CombatAIO.com.base.api.threading.types.subtype;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class LongValue extends Value<Long> {

	private Long x;

	public LongValue(Long x) {
		this.x = x;
	}

	@Override
	public Long getValue() {
		return x;
	}

}
