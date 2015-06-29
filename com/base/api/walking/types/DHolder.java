package scripts.CombatAIO.com.base.api.walking.types;

import java.io.Serializable;

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
		StringBuilder line;
		if (cons.length > 0) {
			line = new StringBuilder("if ");
			for (int i = 0; i < cons.length; i++)
				if (i < cons.length - 1 && cons.length > 1)
					line.append(cons[i].toString() + " &");
				else
					line.append(cons[i].toString());
			line.append(": do ");
		} else
			line = new StringBuilder();
		for (int i = 0; i < actions.length; i++)
			if (i < actions.length - 1 && actions.length > 1)
				line.append(actions[i].toString() + " then ");
			else
				line.append(actions[i].toString());
		return line.toString();

	}

}
