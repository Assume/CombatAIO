package scripts.CombatAIO.com.base.api.threading.helper;

import java.awt.Rectangle;
import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterface;

import scripts.CombatAIO.com.base.main.utils.ArrayUtil;

public class IngameWorldSwitcher {

	public static boolean isHopping = false;
	private static final int SWITCHER_PARENT_ID = 69;
	private static final int SWITCHER_CLOSE_CHILD_ID = 3;
	private static final int OPEN_SWITCHER_PARENT_ID = 182;
	private static final int OPEN_SWITCHER_CHILD_ID = 1;
	private static final int NPC_CHAT_PARENT_ID = 219;
	private static final int NPC_CHAT_TITLE_CHILD_ID = 0;
	private static final long SWITCH_TIMEOUT = 30000;

	private static final Rectangle CONTAINING_BOX = new Rectangle(547, 229,
			190, 204);

	private static final int[] NON_EXISTANT_WORLDS = new int[] { 7, 15, 23, 24,
			31, 32, 39, 40, 47, 48, 55, 56, 63, 64, 71, 72, 79, 80, 87, 88, 89,
			90, 91, 92 };

	private static final int[] P2P_WORLDS = { 2, 3, 4, 5, 6, 9, 10, 11, 12, 13,
			14, 17, 18, 19, 20, 21, 22, 27, 28, 29, 30, 33, 34, 36, 38, 41, 42,
			43, 44, 45, 46, 49, 50, 51, 52, 53, 54, 57, 58, 59, 60, 61, 62, 65,
			66, 67, 68, 69, 70, 73, 74, 75, 76, 77, 78 };

	private static final int[] F2P_WORLDS = { 1, 8, 16, 26, 35, 81, 82, 83, 84,
			93, 94 };

	private static int getWorldSkipDifference(int world) {
		int diff = 0;
		for (int i : NON_EXISTANT_WORLDS) {
			if (world > i)
				diff++;
		}
		return diff;
	}

	private static boolean isValidChat(int world) {
		RSInterface title = Interfaces.get(NPC_CHAT_PARENT_ID,
				NPC_CHAT_TITLE_CHILD_ID);
		if (title == null)
			return false;

		RSInterface child = title.getChild(0);
		if (child == null)
			return false;

		String text = child.getText();
		if (text == null)
			return false;
		try {
			String number = text.substring(16, world > 9 ? 18 : 17);
			return Integer.parseInt(number) == world;
		} catch (Exception e) {

		}
		return false;
	}

	public static boolean switchToRandomWorld() {
		if (ArrayUtil.contains(Game.getCurrentWorld() - 300, F2P_WORLDS))
			return switchWorld(F2P_WORLDS[General.random(0,
					F2P_WORLDS.length - 1)]);
		return switchWorld(P2P_WORLDS[General.random(0, P2P_WORLDS.length - 1)]);
	}

	public static boolean switchWorld(final int world) {
		long start = Timing.currentTimeMillis();
		isHopping = true;

		while (Timing.timeFromMark(start) < SWITCH_TIMEOUT
				&& Game.getGameState() != 10
				&& (Game.getCurrentWorld() != world + 300 || Game
						.getGameState() != 30) && !Combat.isUnderAttack()) {
			String[] options = NPCChat.getOptions();
			if (options != null && Arrays.asList(options).contains("Yes.")
					&& isValidChat(world)) {
				Keyboard.typeString("2");
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(50, 100);
						int state = Game.getGameState();
						return state == 45 || state == 10;
					}
				}, General.random(1000, 2000));
			} else if (Game.getGameState() == 45 || Game.getGameState() == 25) {
				if (Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(50, 100);
						int state = Game.getGameState();
						return Game.getCurrentWorld() == world + 300
								&& (state == 30 || state == 10);
					}
				}, General.random(7000, 8000))) {
					isHopping = false;
					return true;
				}
			} else if (!isOpen()) {
				open();
			} else if (needToScroll(world)) {
				scroll(world);
			} else {
				clickWorld(world);
			}
			General.sleep(50, 100);
		}
		isHopping = false;
		return Game.getCurrentWorld() == world + 300
				&& (Game.getGameState() == 30 || Game.getGameState() == 10);
	}

	private static boolean needToScrollUp(int world) {
		RSInterface child = Interfaces.get(SWITCHER_PARENT_ID,
				(world - getWorldSkipDifference(world)) + 12);
		if (child == null)
			return false;

		Rectangle r = child.getAbsoluteBounds();
		if (r == null)
			return false;
		int y = (int) r.getY();
		return CONTAINING_BOX.getY() - y > 1;
	}

	private static boolean scrollUp(int world) {
		int ticks = 0;

		RSInterface child = Interfaces.get(SWITCHER_PARENT_ID,
				(world - getWorldSkipDifference(world)) + 12);
		if (child == null)
			return false;

		Rectangle r = child.getAbsoluteBounds();
		if (r == null)
			return false;
		int y = (int) r.getY();

		double diff1 = CONTAINING_BOX.getY() - y;

		while (diff1 > 1) {
			diff1 -= 45;
			ticks++;
		}

		Mouse.scroll(true, ticks + General.random(1, 3));
		r = child.getAbsoluteBounds();
		if (r == null)
			return false;
		y = (int) r.getY();
		return (int) CONTAINING_BOX.getY() - y > 1;
	}

	private static boolean needToScrollDown(int world) {
		RSInterface child = Interfaces.get(SWITCHER_PARENT_ID,
				(world - getWorldSkipDifference(world)) + 12);
		if (child == null)
			return false;

		Rectangle r = child.getAbsoluteBounds();
		if (r == null)
			return false;
		int y = (int) r.getY();
		return CONTAINING_BOX.getY() - y < -195;
	}

	private static boolean scrollDown(int world) {
		int ticks = 0;

		RSInterface child = Interfaces.get(SWITCHER_PARENT_ID,
				(world - getWorldSkipDifference(world)) + 12);
		if (child == null)
			return false;

		Rectangle r = child.getAbsoluteBounds();
		if (r == null)
			return false;
		int y = (int) r.getY();
		double diff1 = CONTAINING_BOX.getY() - y;

		while (diff1 < -195) {
			diff1 += 45;
			ticks++;
		}

		Mouse.scroll(false, ticks + General.random(1, 3));
		r = child.getAbsoluteBounds();
		if (r == null)
			return false;
		y = (int) r.getY();
		return (int) CONTAINING_BOX.getY() - y < -195;
	}

	public static boolean needToScroll(int world) {
		return needToScrollUp(world) || needToScrollDown(world);
	}

	public static boolean scroll(int world) {

		if (!CONTAINING_BOX.contains(Mouse.getPos())) {
			Mouse.moveBox(CONTAINING_BOX);
		} else if (needToScrollDown(world)) {
			return scrollDown(world);
		} else if (needToScrollUp(world)) {
			return scrollUp(world);
		}
		return false;
	}

	private static boolean clickWorld(final int world) {
		RSInterface child = Interfaces.get(SWITCHER_PARENT_ID,
				(world - getWorldSkipDifference(world)) + 12);
		if (child == null)
			return false;

		if (child.click()) {
			return Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					General.sleep(50, 100);
					return isValidChat(world) || Game.getGameState() == 45;
				}
			}, General.random(1000, 2000));
		}
		return false;
	}

	public static boolean isSelected() {
		return Interfaces.isInterfaceValid(69);
	}

	public static boolean isOpen() {
		return isSelected() && TABS.LOGOUT.isOpen();
	}

	public static boolean open() {

		if (!TABS.LOGOUT.isOpen()) {
			if (!TABS.LOGOUT.open())
				return false;
		}

		if (!isSelected()) {
			if (!Interfaces.isInterfaceValid(OPEN_SWITCHER_PARENT_ID))
				return false;

			RSInterface switcher = Interfaces.get(OPEN_SWITCHER_PARENT_ID,
					OPEN_SWITCHER_CHILD_ID);
			if (switcher == null)
				return false;

			if (switcher.click())
				return Timing.waitCondition(new Condition() {

					@Override
					public boolean active() {
						General.sleep(50, 100);
						return isOpen();
					}
				}, General.random(1000, 2000));
		}

		return isOpen();
	}

	public static boolean close() {

		RSInterface close = Interfaces.get(SWITCHER_PARENT_ID,
				SWITCHER_CLOSE_CHILD_ID);
		if (close == null)
			return false;

		if (close.click())
			return Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					General.sleep(50, 100);
					return !isOpen();
				}
			}, General.random(1000, 2000));

		return false;
	}

}