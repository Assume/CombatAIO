package scripts.CombatAIO.com.base.main;

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

}
