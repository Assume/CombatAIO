package scripts.CombatAIO.com.base.api.threading.types.subtype;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class RSNPCArrayValue extends Value<RSNPC[]> {

	private RSNPC[] value;

	public RSNPCArrayValue(RSNPC[] value) {
		this.value = value;
	}

	@Override
	public RSNPC[] get() {
		return value;
	}
}