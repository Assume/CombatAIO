package scripts.CombatAIO.com.base.api.general.walking.custom.background;

import java.io.Serializable;

import scripts.priv.drennon.background.actions.DKillLine;

public class DHolder implements Serializable {

	/**
	 * 
	 */

	private DCondition[] cons;
	private DAction[] actions;

	public DHolder(DCondition[] con, DAction[] actions) {
		this.cons = con;
		this.actions = actions;
	}

	public boolean shouldExecute() {
		for (DCondition con : cons)
			if (!con.validate())
				return false;
		return true;
	}

	public void execute() {
		for (DAction a : actions)
			a.execute();
	}

	private static final long serialVersionUID = -2493190433082987019L;

	@Override
	public String toString() {
		StringBuilder line = new StringBuilder("if ");
		for (DCondition con : cons)
			line.append(", " + con.toString());
		line.append(": do ");
		for (DAction ac : actions)
			line.append(ac.toString() + " ");
		return line.toString();

	}

	public boolean hasKillLine() {
		for (DAction a : actions)
			if (a instanceof DKillLine)
				return true;
		return false;
	}

}
