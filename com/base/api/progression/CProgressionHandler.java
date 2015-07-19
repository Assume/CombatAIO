package scripts.CombatAIO.com.base.api.progression;

import java.util.ArrayList;
import java.util.List;

import scripts.CombatAIO.com.base.api.tasks.Dispatcher;
import scripts.CombatAIO.com.base.api.tasks.types.PauseType;

public class CProgressionHandler {

	private List<CProgressionMove> moves = new ArrayList<CProgressionMove>();
	private String name;

	public CProgressionHandler(String name, List<CProgressionMove> move) {
		this.moves = move;
		this.name = name;
	}

	public CProgressionHandler(String name, CProgressionMove... move) {
		this(name, fillList(move));
	}

	public CProgressionHandler() {
		this(null, new CProgressionMove[] {});

	}

	private static List<CProgressionMove> fillList(CProgressionMove[] moves) {
		List<CProgressionMove> list = new ArrayList<CProgressionMove>();
		for (CProgressionMove x : moves)
			list.add(x);
		return list;
	}

	/*
	 * public void removeInvalidMoves() { for (int i = 0; i < moves.size() - 1;
	 * i++) { CProgressionCondition x = moves.get(i); CProgressionCondition x2 =
	 * moves.get(i + 1); if (x instanceof CCurrentLevel && x2 instanceof
	 * CCurrentLevel) { CCurrentLevel temp = (CCurrentLevel) x; CCurrentLevel
	 * tempx2 = (CCurrentLevel) x2; if (temp.getSkill() != tempx2.getSkill())
	 * continue; if (temp.getLevel() <= temp.getSkill().getActualLevel() &&
	 * temp.getSkill().getActualLevel() >= tempx2 .getLevel()) moves.remove(i);
	 * } } }
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void addProgressionMove(CProgressionMove move) {
		this.moves.add(move);
	}

	public void checkAndExecute() {
		List<CProgressionMove> temp = getAllPossibleProgressions();
		if (temp == null)
			return;
		executeProgression(temp);
	}

	public void executeProgression(List<CProgressionMove> temp) {
		Dispatcher.get().pause(PauseType.COULD_INTERFERE_WITH_EATING);
		for (CProgressionMove x : temp) {
			x.execute();
			this.moves.remove(x);
		}
		Dispatcher.get().unpause(PauseType.COULD_INTERFERE_WITH_EATING);
	}

	public List<CProgressionMove> getAllPossibleProgressions() {
		if (this.moves.size() == 0)
			return null;
		List<CProgressionMove> list = new ArrayList<CProgressionMove>();
		for (CProgressionMove x : moves)
			if (x.shouldProgress())
				list.add(x);
		return list;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
