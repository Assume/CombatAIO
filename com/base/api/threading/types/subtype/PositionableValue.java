package scripts.CombatAIO.com.base.api.threading.types.subtype;

import org.tribot.api.interfaces.Positionable;

import scripts.CombatAIO.com.base.api.threading.types.Value;

public class PositionableValue extends Value<Positionable> {

	private Positionable value;

	public PositionableValue(Positionable value) {
		this.value = value;
	}

	@Override
	public Positionable get() {
		return value;
	}

}
