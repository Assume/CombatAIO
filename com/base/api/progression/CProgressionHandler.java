package scripts.CombatAIO.com.base.api.progression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.tribot.util.Util;

import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.custom.types.CustomMovement;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class CProgressionHandler {

	private static final String SAVE_PATH = Util.getWorkingDirectory()
			+ File.separator + "Base" + File.separator + "progression";

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
		if (move == null)
			return;
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

	public void save(String name) {
		new File(SAVE_PATH).mkdirs();
		List<CProgressionMove> movements = this.getAllMoves();
		try {
			FileOutputStream fout = new FileOutputStream(SAVE_PATH
					+ File.separator + name + ".cprg");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(movements);
			fout.flush();
			fout.close();
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(String name) {
		try {
			FileInputStream fis = new FileInputStream(SAVE_PATH
					+ File.separator + name + ".cprg");
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<CProgressionMove> movements = (List<CProgressionMove>) ois
					.readObject();
			this.moves = movements;
			fis.close();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CProgressionMove> getAllMoves() {
		return moves;
	}

}
