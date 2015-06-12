package scripts.CombatAIO.com.base.main;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;

import scripts.CombatAIO.com.base.main.utils.ArrayUtil;

public class GenericMethods {

	public static int[] combineArrays(int[]... ars) {
		List<Integer> ar = new ArrayList<Integer>();
		for (int i = 0; i < ars.length; i++)
			for (int x = 0; x < ars[i].length; x++)
				ar.add(ars[i][x]);
		return ArrayUtil.toArrayInt(ar);

	}

	public static void println(Object j) {
		if (General.getTRiBotUsername().equalsIgnoreCase("assume"))
			System.out.println(j == null ? "null" : j.toString());
	}

}
