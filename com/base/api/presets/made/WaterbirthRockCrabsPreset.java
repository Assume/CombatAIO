package scripts.CombatAIO.com.base.api.presets.made;

import org.tribot.api.DynamicClicking;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.presets.Preset;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.types.Bank;
import scripts.CombatAIO.com.base.api.walking.types.Teleport;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.webwalker_logic.WebWalker;

public class WaterbirthRockCrabsPreset extends Preset {

	public static final String REQUIREMENTS = "Coins in bank, Camelot teletab, access to Waterbirth Island";

	public static final BankItem CAMELOT_TELEPORT_TAB = new BankItem(
			Teleport.CAMELOT_TELEPORT.getTabId(), 1);
	public static final BankItem COINS = new BankItem(995, 1000);

	private static final RSTile BOAT_LOCATION = new RSTile(2621, 3682);

	public WaterbirthRockCrabsPreset(String requirements,
			BankItem... required_items) {
		super(requirements, required_items);
	}

	@Override
	public void executeToBank() {
		Teleport.CAMELOT_TELEPORT.teleport();
		WalkingManager.walk(MovementType.TO_BANK, Bank.SEERS_VILLAGE.getTile());

	}

	@Override
	public void executeToMonster() {
		WalkingManager.walk(MovementType.TO_MONSTER, BOAT_LOCATION);
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return !Player.isMoving();
			}
		}, 5000);
		travelOnBoat();

	}

	private final String jarvald = "Jarvald";
	private final RSTile waterbirth_landing_tile = new RSTile(2547, 3758);

	private void travelOnBoat() {
		RSNPC[] npc = NPCs.findNearest(jarvald);
		if (npc.length == 0)
			return;
		DynamicClicking.clickRSNPC(npc[0], "Travel");
		if (!Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return NPCChat.getClickContinueInterface() != null;
			}
		}, 5000))
			travelOnBoat();
		NPCChat.clickContinue(true);
		NPCChat.selectOption("YES", true);
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getPosition().distanceTo(waterbirth_landing_tile) < 15;
			}
		}, 10000);
		WebWalker.walkTo(Dispatcher.get().getPreset().getHomeTile());
	}

}
