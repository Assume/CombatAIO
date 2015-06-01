package scripts.CombatAIO.com.base.api.general.walking.types;

import java.io.Serializable;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;

public class CustomMovement implements Serializable {

	private static final long serialVersionUID = 4566961031926936239L;
	private DFullHolder holder;
	private String name;
	private MovementType type;
	private String rad;
	private RSTile center_tile;

	public CustomMovement(RSTile center_tile, DFullHolder holder, String name,
			MovementType type, String rad) {
		this.holder = holder;
		this.name = name;
		this.type = type;
		this.rad = rad;
		this.center_tile = center_tile;
	}

	public MovementType getMovementType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public RSArea getActivationArea() {
		return new RSArea(center_tile, Integer.parseInt(rad));
	}

	public void execute() {
		holder.checkAndExecute();
	}

	public DFullHolder getFullHolder() {
		return this.holder;
	}

	public void setMovementType(MovementType type2) {
		this.type = type2;

	}

	public void setFullHolder(DFullHolder dfh) {
		this.holder = dfh;
	}

	public String getRadius() {
		return this.rad;
	}

	public void setRadius(String rad) {
		this.rad = rad;
	}

	public void setCenterTile(RSTile tile) {
		this.center_tile = tile;

	}

}
