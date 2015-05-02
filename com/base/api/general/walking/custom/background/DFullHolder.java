package scripts.CombatAIO.com.base.api.general.walking.custom.background;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import scripts.priv.drennon.background.actions.DKillLine;

public class DFullHolder implements Serializable {

	private static final long serialVersionUID = 1742634870016114852L;

	private List<DHolder> holders;

	private List<Integer> killed_lines;

	public DFullHolder(DHolder[] holderss) {
		this.holders = Arrays.asList(holderss);
		this.killed_lines = new ArrayList<Integer>();
	}

	public void checkAndExecute() {
		for (int i = 0; i < holders.size(); i++) {
			DHolder d = holders.get(i);
			if (d.shouldExecute() && !isKilled(i)) {
				if (d.hasKillLine())
					killed_lines.add(i);
				d.execute();
			}
		}
	}

	private boolean isKilled(int i) {
		return killed_lines.contains(i);
	}

	public void kill(DKillLine dKillLine) {
		// TODO Auto-generated method stub

	}

}
