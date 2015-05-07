package scripts.CombatAIO.com.base.api.general.walking.types;

public class JeweleryTeleport {

	private Jewelery jewelery;
	private JEWELERY_TELEPORT_LOCATIONS teleport;

	public JeweleryTeleport(Jewelery jewelery,
			JEWELERY_TELEPORT_LOCATIONS teleport) {
		this.jewelery = jewelery;
		this.teleport = teleport;
	}

	public Jewelery getJewelery() {
		return this.jewelery;
	}

	public boolean canTeleport() {
		return jewelery.hasJewelery();
	}

	public JEWELERY_TELEPORT_LOCATIONS getTeleportLocation() {
		return this.teleport;
	}

	public boolean operate() {
		return this.jewelery.operate(this);
	}

}
