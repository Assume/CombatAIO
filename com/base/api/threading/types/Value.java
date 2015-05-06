package scripts.CombatAIO.com.base.api.threading.types;

public class Value<T> {

	private T t;

	public Value(T t) {
		this.t = t;
	}

	public T getValue() {
		return t;
	}

}
