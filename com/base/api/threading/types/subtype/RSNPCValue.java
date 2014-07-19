package scripts.CombatAIO.com.base.api.threading.types.subtype;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class RSNPCValue extends Value<RSNPC> {

	private RSNPC value;

	public RSNPCValue(RSNPC value) {
		this.value = value;
	}

	@Override
	public RSNPC get() {
		return value;
	}
}
