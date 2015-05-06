package scripts.CombatAIO.com.base.api.types.enums;

public enum Weapon {

	ABYSSAL_WHIP(1800, 1658);

	private long attack_speed_ms;
	private int animation_id;

	Weapon(long attack_speed_ms, int animation_id) {
		this.attack_speed_ms = attack_speed_ms;
		this.animation_id = animation_id;
	}

	public int getAnimationID() {
		return this.animation_id;
	}

	public long getAttackSpeed() {
		return this.attack_speed_ms;
	}

}
