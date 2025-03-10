package scripts.CombatAIO.com.base.api.walking.custom.types;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DFullHolder implements Serializable {

	private static final long serialVersionUID = 1742634870016114852L;

	private List<DHolder> holders;

	public DFullHolder(DHolder[] holderss) {
		this.holders = Arrays.asList(holderss);
	}

	public void checkAndExecute() {
		for (int i = 0; i < holders.size(); i++) {
			DHolder d = holders.get(i);
			if (d.shouldExecute())
				d.execute();
		}
	}

	public DHolder[] getHolders() {
		return holders.toArray(new DHolder[holders.size()]);
	}

}
