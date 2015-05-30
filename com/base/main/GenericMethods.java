package scripts.CombatAIO.com.base.main;

import org.tribot.api.General;

public class GenericMethods {

	public static int[] combineArrays(int[]... ars) {
		int tot = 0;
		for (int i = 0; i < ars.length; i++)
			tot += ars.length;
		int[] new_ar = new int[tot];
		int overall = 0;
		for (int i = 0; i < ars.length; i++)
			for (int x = 0; x < ars[i].length; x++)
				new_ar[overall++] = ars[i][x];
		return new_ar;

	}

	public static void println(Object j) {
		if (j == null)
			return;
		if (General.getTRiBotUsername().equalsIgnoreCase("assume"))
			System.out.println(j.toString());
	}

}
