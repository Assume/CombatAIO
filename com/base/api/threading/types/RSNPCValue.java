package scripts.CombatAIO.com.base.api.threading.types;

import org.tribot.api2007.types.RSNPC;

public class RSNPCValue extends Value {

	private RSNPC value;

	public RSNPCValue(RSNPC value) {
		this.value = value;
	}

	@Override
	public Object get() {
		return value;
	}
}
