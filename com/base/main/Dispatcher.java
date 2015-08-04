package scripts.CombatAIO.com.base.main;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.presets.PresetFactory;
import scripts.CombatAIO.com.base.api.progression.CProgressionHandler;
import scripts.CombatAIO.com.base.api.tasks.helper.Banker;
import scripts.CombatAIO.com.base.api.tasks.threads.CombatTask;
import scripts.CombatAIO.com.base.api.tasks.threads.ConsumptionTask;
import scripts.CombatAIO.com.base.api.tasks.threads.Looter;
import scripts.CombatAIO.com.base.api.tasks.threads.PKAvoider;
import scripts.CombatAIO.com.base.api.tasks.threads.PriceUpdater;
import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Threadable;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;
import scripts.CombatAIO.com.base.api.types.constants.ScriptIDs;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.main.gui.BaseGUI;
import scripts.CombatAIO.com.base.main.utils.Logger;
import scripts.api.scriptapi.paint.types.CGUI;

public class Dispatcher {

	private static Dispatcher dispatcher;

	public static final int LITE_VERSION_REPO_ID = 64;

	public static void create(BaseCombat main_class) {
		dispatcher = new Dispatcher(main_class);
	}

	public static Dispatcher get() {
		return dispatcher;
	}

	private boolean started = false;
	private BaseGUI gui;
	private boolean run = true;

	public void start(String name) {
		gui = new BaseGUI();
		if (name == null || name.equals("null") || name.length() == 0)
			gui.setVisible(true);
		else {
			if (gui.load(name))
				gui.set();
			else
				throw new RuntimeException(
						"Profile not found, please try again");
		}
		while (gui.isVisible())
			General.sleep(300);
		if (!started) {
			run();
			started = true;
		}
	}

	private CombatTask combat_task;
	private Looter looting_task;
	private ConsumptionTask consumption_task;
	private PriceUpdater price_updater_task;
	private PKAvoider pk_avoider;
	private BaseCombat main_class;
	private ABCUtil abc_util;
	private CProgressionHandler handler;
	private Banker banker;
	private int repo_id;

	private PresetFactory preset;

	private Dispatcher(BaseCombat main_class) {
		this.main_class = main_class;
		this.combat_task = new CombatTask();
		this.looting_task = new Looter();
		this.consumption_task = new ConsumptionTask();
		this.pk_avoider = new PKAvoider(true);
		this.price_updater_task = new PriceUpdater();
		this.abc_util = new ABCUtil();
		this.handler = new CProgressionHandler();
		this.banker = new Banker();
		this.repo_id = main_class.getRepoID();
		this.preset = PresetFactory.Automatic;
	}

	private void run() {
		this.combat_task.setHomeTile(this.preset == PresetFactory.Automatic ? Player
				.getPosition() : this.preset.getHomeTile());
		this.preset.addBankItems();
		Walking.setControlClick(true);
		this.combat_task.start();
		this.combat_task.initiate();
		this.looting_task.start();
		this.consumption_task.start();
		this.pk_avoider.start();
		this.price_updater_task.start();
		this.combat_task.setAmmo();
		if (this.consumption_task.isUsingBonesToPeaches().getValue()) {
			this.looting_task.addPossibleLootItem(true, "Bones");
			this.looting_task.addPossibleLootItem(true, "Big bones");
		}

	}

	/*
	 * @param type the type of value you are looking for
	 * 
	 * @param extra_paramaters extra information that is required
	 * 
	 * @return the value requested, null if no value exists
	 */
	public Value<?> get(ValueType type, String... extra_paramaters) {
		switch (type) {
		case CURRENT_TARGET:
			return combat_task.getCurrentTarget();
		case MINIMUM_LOOT_VALUE:
			return this.looting_task.getMinimumLootValue();
		case TOTAL_KILLS:
			return combat_task.getTotalKills();
		case TOTAL_LOOT_VALUE:
			return looting_task.getTotalLootValue();
		case LOOT_ITEM:
			return looting_task.getLootItem(extra_paramaters);
		case ITEM_PRICE:
			return looting_task.getItemPrice(extra_paramaters);
		case AMOUNT_LOOTED_OF_ITEM:
			return looting_task.getAmountLooted(extra_paramaters);
		case FOOD:
			return consumption_task.getFood();
		case MONSTER_IDS:
			return this.combat_task.getMonsterIDs();
		case HOME_TILE:
			return this.combat_task.getHomeTile();
		case IS_RANGING:
			return this.combat_task.isRanging();
		case RUN_TIME:
			return new Value<Long>(this.main_class.getRunningTime());
		case PRAYER:
			return this.combat_task.getPrayer();
		case EAT_FOR_SPACE:
			return this.looting_task.shouldEatForSpace();
		case LOOT_ITEM_NAMES:
			return this.looting_task.getAllItemNamesValue();
		case IS_BONES_TO_PEACHES:
			return this.consumption_task.isUsingBonesToPeaches();
		case LOOT_IN_COMBAT:
			return this.looting_task.lootInCombat();
		case WAIT_FOR_LOOT:
			return this.looting_task.waitForLoot();
		case POSSIBLE_MONSTERS:
			return this.combat_task.getPossibleMonsters();
		case SPECIAL_ATTACK_WEAPON:
			return this.combat_task.getSpecialAttackWeapon();
		case GUTHANS_IDS:
			return this.combat_task.getGuthansIDs();
		case ALL_LOOT_ITEMS:
			return this.looting_task.getLootItems();
		case FOOD_WITHDRAW_AMOUNT:
			return this.banker.getFoodWithdrawAmount();
		case FLICKER:
			return this.combat_task.shouldFlicker();
		case USE_GUTHANS:
			return this.combat_task.getUseGuthans();
		case COMBAT_RADIUS:
			return this.combat_task.getCombatRadius();
		case WORLD_HOP_TOLERANCE:
			return this.combat_task.getWorldHopTolerance();
		case SAFE_SPOT_TILE:
			return this.combat_task.getSafeSpot();
		case ARMOR_HOLDER_IDS:
			return this.combat_task.getArmorHolderIds();
		case AMMO_ID:
			return this.combat_task.getAmmoId();
		case USE_TELEKINETIC_GRAB:
			return this.looting_task.shouldUseTelegrab();
		case USE_CANNON:
			return this.combat_task.getUseCannon();
		case CANNON_TILE:
			return this.combat_task.getCannonTile();
		case ATTACK_MONSTERS_IN_COMBAT:
			return this.combat_task.getAttackMonstersInCombat();
		case BURY_BONES:
			return this.combat_task.getBuryBones();
		default:
			break;
		}
		return null;
	}

	public void set(ValueType type, Value<?> val) {
		switch (type) {
		case MINIMUM_LOOT_VALUE:
			this.looting_task.setMinimumLootValue((Integer) val.getValue());
			break;
		case FOOD:
			consumption_task.setFood((Food) val.getValue());
			break;
		case MONSTER_IDS:
			this.combat_task.setMonsterIDs((int[]) val.getValue());
			break;
		case HOME_TILE:
			this.combat_task.setHomeTile((RSTile) val.getValue());
			break;
		case IS_RANGING:
			this.combat_task.setRanging((Boolean) val.getValue());
			break;
		case PRAYER:
			this.combat_task.setPrayer((Prayer) val.getValue());
			break;
		case LOOT_ITEM_NAMES:
			this.looting_task.addPossibleLootItem(true,
					(String[]) val.getValue());
			break;
		case LOOT_ITEM:
			this.looting_task.addLootItem((LootItem) val.getValue());
			break;
		case LOOT_IN_COMBAT:
			this.looting_task.setLootInCombat((Boolean) val.getValue());
			break;
		case WAIT_FOR_LOOT:
			this.looting_task.setWaitForLoot((Boolean) val.getValue());
			break;
		case SPECIAL_ATTACK_WEAPON:
			this.combat_task.setSpecialAttackWeapon((Weapon) val.getValue());
			break;
		case USE_GUTHANS:
			this.combat_task.setUseGuthans((Boolean) val.getValue());
			break;
		case FOOD_WITHDRAW_AMOUNT:
			this.banker.setFoodWithdrawAmount((Integer) val.getValue());
			break;
		case FLICKER:
			this.combat_task.setUseFlicker((Boolean) val.getValue());
			break;
		case COMBAT_RADIUS:
			this.combat_task.setCombatRadius((Integer) val.getValue());
			break;
		case WORLD_HOP_TOLERANCE:
			this.combat_task.setWorldHopTolerance((Integer) val.getValue());
			break;
		case SAFE_SPOT_TILE:
			this.combat_task.setSafeSpot((RSTile) val.getValue());
			break;
		case USE_TELEKINETIC_GRAB:
			this.looting_task.setUseTelegrab((Boolean) val.getValue());
			break;
		case USE_CANNON:
			this.combat_task.setUseCannon((Boolean) val.getValue());
			break;
		case CANNON_TILE:
			this.combat_task.setCannonTile((RSTile) val.getValue());
			break;
		case ATTACK_MONSTERS_IN_COMBAT:
			this.combat_task
					.setAttackMonstersInCombat((Boolean) val.getValue());
			break;
		case BURY_BONES:
			this.combat_task.setBuryBones((Boolean) val.getValue());
			break;
		default:
			break;
		}

	}

	public Banker getBanker() {
		return this.banker;
	}

	public Looter getLooter() {
		return this.looting_task;
	}

	public ConsumptionTask getConsumptionTask() {
		return this.consumption_task;
	}

	public CombatTask getCombatTask() {
		return this.combat_task;
	}

	@SuppressWarnings("deprecation")
	public void pause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type)) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						x.getName() + " " + x.getId());
				x.setPaused(true);
				x.suspend();
			}

	}

	@SuppressWarnings("deprecation")
	public void unpause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type)) {
				x.setPaused(false);
				x.resume();
			}

	}

	public void checkThreads() {
		this.handler.checkAndExecute();
		if (this.combat_task.isPaused()
				&& Timing.timeFromMark(this.combat_task.getPauseTime()) > 30000) {
			this.combat_task.resume();
			this.combat_task.setPaused(false);
		}
	}

	public void checkAndExecuteProgression() {
		this.handler.checkAndExecute();
	}

	public boolean isRunning() {
		return this.main_class.isRunning();
	}

	public boolean hasStarted() {
		return this.started;
	}

	public ABCUtil getABCUtil() {
		return this.abc_util;
	}

	public void attackTarget() {
		this.combat_task.attackCurrentTarget();
	}

	public static boolean hasBeenInitialized() {
		return dispatcher != null;
	}

	public void stop(String reason) {
		this.run = false;
		Login.logout();
		this.main_class.setLoginBotState(false);
		throw new RuntimeException(reason);
	}

	public int getRepoID() {
		return this.repo_id;
	}

	public CGUI getGUI() {
		return this.gui;
	}

	public boolean isLiteMode() {
		return Dispatcher.get().getRepoID() == Dispatcher.LITE_VERSION_REPO_ID;
	}

	public boolean shouldRun() {
		return this.run;
	}

	public void setPreset(PresetFactory preset) {
		this.preset = preset;
	}

	public PresetFactory getPreset() {
		return this.preset;
	}

	public boolean isRockCrabsPreset() {
		return this.preset == PresetFactory.RELLEKKA_WEST_ROCK_CRABS
				|| this.preset == PresetFactory.RELLEKKA_EAST_ROCK_CRABS;
	}

	public boolean isRockCrabsScriptID() {
		return getRepoID() == ScriptIDs.ASSUMES_GOT_CRABS;
	}

	public boolean isFireGiantsPreset() {
		return this.preset == PresetFactory.FIRE_GIANTS_WATERFALL_C
				|| this.preset == PresetFactory.FIRE_GIANTS_WATERFALL_W;
	}

}
