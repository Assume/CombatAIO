package scripts.CombatAIO.com.base.api.threading.types;

public class IntegerValue extends Value {

	private Integer x;

	public IntegerValue(int x) {
		this.x = x;
	}

	@Override
	public Object get() {
		return x;
	}

}
