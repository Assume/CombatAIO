package scripts.CombatAIO.com.base.api.types.enums;

public enum Weapon {

	ABYSSAL_WHIP(1800, 1658, 50), DRAGON_SCIMITAR(2400, -1, 55), DRAGON_DAGGER(
			-1, -1, 25), MAGIC_SHORT_BOW(-1, -1, 55), DRAGON_LONG_SWORD(-1, -1,
			25), ARMADYL_GODSWORD(-1, -1, 50), BANDSOS_GODSWORD(-1, -1, 100), SARADOMIN_GODSWORD(
			-1, -1, 50), ZAMORAK_GODSWORD(-1, -1, 60), DARK_BOW(-1, -1, 55);

	private long attack_speed_ms;
	private int animation_id;

	Weapon(long attack_speed_ms, int animation_id, int special_attack_usage) {
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
