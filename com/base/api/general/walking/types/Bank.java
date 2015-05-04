package scripts.CombatAIO.com.base.api.general.walking.types;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum Bank {

	LUMBRIDGE_CASTLE(new RSArea(new RSTile(3207, 3218, 2), new RSTile(3210,
			3220, 2))),

	LUMBRIDGE_BASEMENT(new RSArea(new RSTile(3217, 9620, 0), new RSTile(3218,
			9623, 0))),

	DRAYNOR_VILLAGE(new RSArea(new RSTile(3092, 3240, 0),
			new RSTile(3097, 3246))),

	EDGEVILLE(new RSArea(new RSTile(3091, 3489, 0), new RSTile(3098, 3499, 0))),

	VARROCK_WEST(new RSArea(new RSTile(3180, 3434, 0),
			new RSTile(3185, 3446, 0))),

	VARROCK_EAST(new RSArea(new RSTile(3250, 3419, 0),
			new RSTile(3257, 3423, 0))),

	FALADOR_WEST(new RSArea(new RSTile(2949, 3368, 0),
			new RSTile(2943, 3373, 0))),

	FALADOR_EAST(new RSArea(new RSTile(3009, 3355, 0),
			new RSTile(3018, 3358, 0))),

	SEERS_VILLAGE(new RSArea(new RSTile(2721, 3490, 0), new RSTile(2730, 3493,
			0))),

	ARDOUGNE_NORTH(
			new RSArea(new RSTile(2621, 3332, 0), new RSTile(2612, 3335))),

	CATHERBY(new RSArea(new RSTile(2807, 3438, 0), new RSTile(2812, 3441, 0))),

	CASTLE_WARS(
			new RSArea(new RSTile(2436, 3081, 0), new RSTile(2446, 3098, 0))),

	CANIFIS(new RSArea(new RSTile(3509, 3475, 0), new RSTile(3512, 3483, 0))),

	DUEL_ARENA(new RSArea(new RSTile(3373, 3276, 0), new RSTile(3390, 3265, 0))),

	AL_KHARID(new RSArea(new RSTile(3272, 3161, 0), new RSTile(3269, 3173, 0))),

	YANILLE(new RSArea(new RSTile(2609, 3088, 0), new RSTile(2613, 3097, 0))),

	LUNARS(new RSArea(new RSTile(2097, 3919, 0), new RSTile(2104, 3917, 0))),

	NARDAH(new RSArea(new RSTile(3430, 2889, 0), new RSTile(3427, 2894, 0))),

	ROGUE_DEN(new RSArea(new RSTile(3040, 4975, 1), new RSTile(3049, 4969, 1)));

	private RSArea area;

	Bank(RSArea area) {
		this.area = area;
	}

	public RSTile getTile() {
		return area.getRandomTile();
	}

	public static Bank getNearestBank() {
		int distance = Integer.MAX_VALUE;
		Bank nearest;
		RSTile pos = Player.getPosition();
		for (Bank bank : Bank.values()) {
			int temp = pos.distanceTo(bank.getTile());
			if (temp < distance) {
				nearest = bank;
				distance = temp;
			}
		}
		return nearest;
	}
}
