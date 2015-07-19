package scripts.CombatAIO.com.base.api.tasks.types;

public class Value<T> {

	private T t;

	public Value(T t) {
		this.t = t;
	}

	public T getValue() {
		return t;
	}

}
