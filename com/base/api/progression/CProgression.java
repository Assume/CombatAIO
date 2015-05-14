package scripts.CombatAIO.com.base.api.progression;

import java.util.ArrayList;
import java.util.List;

import scripts.CombatAIO.com.base.api.progression.conditions.CCurrentLevel;

public class CProgression {

	private List<CProgressionMove> moves = new ArrayList<CProgressionMove>();
	private String name;

	public CProgression(String name, List<CProgressionMove> move) {
		this.moves = move;
		this.name = name;
	}

	public CProgression(String name, CProgressionMove... move) {
		this.moves = fillList(move);
		this.name = name;
	}

	private List<CProgressionMove> fillList(CProgressionMove[] moves) {
		List<CProgressionMove> list = new ArrayList<CProgressionMove>();
		for (CProgressionMove x : moves)
			list.add(x);
		return list;
	}

	public void removeInvalidMoves() {
		for (int i = 0; i < moves.size() - 1; i++) {
			CProgressionMove x = moves.get(i);
			CProgressionMove x2 = moves.get(i + 1);
			if (x instanceof CCurrentLevel
					&& x2 instanceof CCurrentLevel) {
				CCurrentLevel temp = (CCurrentLevel) x;
				CCurrentLevel tempx2 = (CCurrentLevel) x2;
				if (temp.getSkill() != tempx2.getSkill())
					continue;
				if (temp.getLevel() <= temp.getSkill().getActualLevel()
						&& temp.getSkill().getActualLevel() >= tempx2
								.getLevel())
					moves.remove(i);
			}
		}
	}

	public boolean shouldProgress() {
		if (this.moves.size() == 0)
			return false;
		return this.moves.get(0).shouldProgress();
	}

	public void executeNextAction() {
		moves.get(0).execute();
		moves.remove(0);
	}

	@Override
	public String toString() {
		return this.name;
	}

}
