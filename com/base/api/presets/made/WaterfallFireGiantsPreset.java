package scripts.CombatAIO.com.base.api.presets.made;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.CombatAIO.com.base.api.presets.DMethods;
import scripts.CombatAIO.com.base.api.presets.Preset;
import scripts.CombatAIO.com.base.api.tasks.Dispatcher;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.types.Bank;
import scripts.CombatAIO.com.base.api.walking.types.JEWELERY_TELEPORT_LOCATIONS;
import scripts.CombatAIO.com.base.api.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.walking.types.JeweleryTeleport;

public class WaterfallFireGiantsPreset extends Preset {

	public static final String REQUIREMENTS = "Glarial's amulet equipped, Games necklace in inventory, rope in inventory";

	public static final BankItem ROPE = new BankItem(954, 1);

	public static final BankItem GAMES_NECKLACE = new BankItem(3863, 1);

	private static final RSTile RAFT_TILE = new RSTile(2510, 3494);

	private static final RSTile RAFT_TILE_INITIAL = new RSTile(2528, 3499);

	private static final int RAFT_ID = 1987;

	private static final RSTile ISLAND_INITIAL_TILE = new RSTile(2512, 3481);

	private static final RSTile ISLAND_END_TILE = new RSTile(2512, 3478);

	private static final int ROCK_ID = 1996;

	private static final int TREE_ID = 2020;

	private static final int LEDGE_ID = 2010;

	// public static final BankItem[] REQUIRED_ITEMS = {

	public WaterfallFireGiantsPreset(String requirements,
			BankItem... required_items) {
		super(requirements, required_items);
	}

	@Override
	public void executeToBank() {
		new JeweleryTeleport(Jewelery.GAMES_NECKLACE,
				JEWELERY_TELEPORT_LOCATIONS.BARBARIAN_OUTPOST).operate();
		WalkingManager.walk(MovementType.TO_BANK,
				Bank.BARBARIAN_OUTPOST.getTile());

	}

	@Override
	public void executeToMonster() {
		WebWalking.walkTo(RAFT_TILE_INITIAL);
		new DPathNavigator().traverse(RAFT_TILE);
		while (Player.isMoving())
			General.sleep(100);
		DMethods.clickObject(RAFT_ID, "Board Log raft");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getPosition().distanceTo(ISLAND_INITIAL_TILE) < 3;
			}
		}, 10000);
		// Walking.walkScreenPath(Walking
		// .generateStraightScreenPath((ISLAND_END_TILE)));
		Camera.setCameraAngle(25);
		int rotation = Camera.getCameraRotation();
		if (rotation < 150 || rotation > 210)
			Camera.setCameraRotation(General.random(150, 210));
		if (!useRopeOnObject(ROCK_ID)) {
			Dispatcher.get().stop("You don't have a rope");
			return;
		}
		General.sleep(6000, 8000);
		if (!useRopeOnObject(TREE_ID)) {
			Dispatcher.get().stop("Unable to find tree");
			return;
		}
		General.sleep(6000, 8000);
		DMethods.clickObject(LEDGE_ID, "Open Ledge");
		General.sleep(3000, 6000);
		new DPathNavigator().traverse(Dispatcher.get().getPreset()
				.getHomeTile());

	}

	private boolean useRopeOnObject(int id) {
		RSItem[] rope = Inventory.find(id);
		if (rope.length == 0)
			return false;
		RSObject[] ob = Objects.find(999, id);
		if (ob.length == 0)
			return false;
		rope[0].click("Use");
		switch (id) {
		case ROCK_ID:
			ob[0].click("Use Rope -> Rock");
			break;
		case TREE_ID:
			ob[0].click("Use Rope -> Dead tree");
			break;
		}
		return true;

	}
}
