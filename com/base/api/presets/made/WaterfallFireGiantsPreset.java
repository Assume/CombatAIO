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
import scripts.CombatAIO.com.base.api.presets.PresetFactory;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.types.Bank;
import scripts.CombatAIO.com.base.api.walking.types.JEWELERY_TELEPORT_LOCATIONS;
import scripts.CombatAIO.com.base.api.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class WaterfallFireGiantsPreset extends Preset {

	public static final String REQUIREMENTS = "Glarial's amulet equipped, Games necklace in inventory, rope in inventory";

	public static final BankItem ROPE = new BankItem(954, 1);

	public static final BankItem GAMES_NECKLACE = new BankItem(3863, 1);

	private static final RSTile RAFT_TILE = new RSTile(2510, 3494);

	private static final RSTile RAFT_TILE_INITIAL = new RSTile(2528, 3499);

	private static final int RAFT_ID = 1987;

	private static final RSTile FIRST_ISLAND_INITIAL_TILE = new RSTile(2512, 3481);

	private static final RSTile SECOND_ISLAND_FIRST_TILE = new RSTile(2513, 3468);

	private static final RSTile INSIDE_LEDGE_TILE = new RSTile(2575, 9861);

	private static final RSTile LEDGE_TILE = new RSTile(2511, 3463);

	private static final int ROCK_ID = 1996;

	private static final int TREE_ID = 2020;

	private static final int LEDGE_ID = 2010;

	private RSTile CENTER_OUTSIDE_DOOR_TILE = new RSTile(2577, 9882);
	private RSTile WEST_OUTSIDE_DOOR_TILE = new RSTile(2564, 9880);

	public WaterfallFireGiantsPreset(String requirements, BankItem... required_items) {
		super(requirements, required_items);
	}

	@Override
	public void executeToBank() {
		new JeweleryTeleport(Jewelery.GAMES_NECKLACE, JEWELERY_TELEPORT_LOCATIONS.BARBARIAN_OUTPOST).operate();
		WalkingManager.walk(MovementType.TO_BANK, Bank.BARBARIAN_OUTPOST.getTile());
	}

	@Override
	public void executeToMonster() {
		WebWalking.walkTo(RAFT_TILE_INITIAL);
		new DPathNavigator().traverse(RAFT_TILE);
		while (Player.isMoving())
			General.sleep(100);
		clickBoat();
		if (!Player.getPosition().equals(FIRST_ISLAND_INITIAL_TILE))
			clickBoat();
		if (clickRock()) {
			if (!Player.getPosition().equals(SECOND_ISLAND_FIRST_TILE))
				clickRock();
		} else
			return;
		if (clickTree()) {
			if (!Player.getPosition().equals(LEDGE_TILE))
				clickTree();
		} else
			return;
		General.sleep(2000, 3000);
		Camera.setCameraRotation(General.random(0, 50));
		clickLedge();
		General.sleep(3000, 6000);
		Walking.walkTo(Dispatcher.get().getPreset() == PresetFactory.FIRE_GIANTS_WATERFALL_W ? WEST_OUTSIDE_DOOR_TILE
				: CENTER_OUTSIDE_DOOR_TILE);
		new DPathNavigator().traverse(Dispatcher.get().getPreset().getHomeTile());

	}

	private void clickBoat() {
		DMethods.clickObject(RAFT_ID, "Board Log raft");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getPosition().distanceTo(FIRST_ISLAND_INITIAL_TILE) < 3;
			}
		}, 10000);
	}

	private boolean clickRock() {
		Camera.setCameraAngle(25);
		int rotation = Camera.getCameraRotation();
		if (rotation < 150 || rotation > 210)
			Camera.setCameraRotation(General.random(150, 210));
		if (!useRopeOnObject(ROCK_ID)) {
			Dispatcher.get().stop("You don't have a rope");
			return false;
		}
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getPosition().equals(SECOND_ISLAND_FIRST_TILE);
			}
		}, 16000);
		return true;
	}

	private boolean clickTree() {
		if (!useRopeOnObject(TREE_ID)) {
			Dispatcher.get().stop("Unable to find tree");
			return false;
		}
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getPosition().equals(LEDGE_TILE);
			}
		}, 10000);
		return true;
	}

	private boolean clickLedge() {
		DMethods.clickObject(LEDGE_ID, "Open Ledge");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Player.getPosition().distanceTo(INSIDE_LEDGE_TILE) < 10;
			}
		}, 5000);
		if (!Player.getPosition().equals(INSIDE_LEDGE_TILE) && Player.getPosition().equals(LEDGE_TILE))
			return clickLedge();
		return Player.getPosition().equals(INSIDE_LEDGE_TILE);
	}

	private boolean useRopeOnObject(int id) {
		RSItem[] rope = Inventory.find(ROPE.getId());
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
