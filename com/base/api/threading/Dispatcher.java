package scripts.CombatAIO.com.base.api.threading;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.progression.CProgressionHandler;
import scripts.CombatAIO.com.base.api.threading.helper.Banker;
import scripts.CombatAIO.com.base.api.threading.threads.CombatTask;
import scripts.CombatAIO.com.base.api.threading.threads.ConsumptionTask;
import scripts.CombatAIO.com.base.api.threading.threads.Looter;
import scripts.CombatAIO.com.base.api.threading.threads.PriceUpdater;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.api.xml.XMLWriter;
import scripts.CombatAIO.com.base.main.BaseCombat;
import scripts.CombatAIO.com.base.main.GenericMethods;
import scripts.CombatAIO.com.base.main.gui.BaseGUI;

public class Dispatcher {

	private static Dispatcher dispatcher;

	public static void create(BaseCombat main_class, long hash_id) {
		dispatcher = new Dispatcher(main_class, hash_id);
	}

	public static Dispatcher get() {
		return dispatcher;
	}

	private boolean started = false;
	private BaseGUI gui;

	public void start() {
		gui = new BaseGUI();
		gui.setVisible(true);
		while (gui.isVisible())
			General.sleep(300);
		if (!started) {
			run();
			started = true;
		}
	}

	private CombatTask combat_thread;
	private Looter looting_thread;
	private ConsumptionTask eat_thread;
	private BaseCombat main_class;
	private long hash_id;
	private ABCUtil abc_util;
	private CProgressionHandler handler;
	private Banker banker;
	private PriceUpdater price_updater_thread;

	private Dispatcher(BaseCombat main_class, long hash_id) {
		this.main_class = main_class;
		this.combat_thread = new CombatTask();
		this.looting_thread = new Looter();
		this.eat_thread = new ConsumptionTask();
		this.price_updater_thread = new PriceUpdater();
		this.hash_id = hash_id != 0 ? this.hash_id : XMLWriter.generateHash();
		this.abc_util = new ABCUtil();
		this.handler = new CProgressionHandler();
		this.banker = new Banker();
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
			return combat_thread.getCurrentTarget();
		case MINIMUM_LOOT_VALUE:
			return this.looting_thread.getMinimumLootValue();
		case TOTAL_KILLS:
			return combat_thread.getTotalKills();
		case TOTAL_LOOT_VALUE:
			return looting_thread.getTotalLootValue();
		case LOOT_ITEM:
			return looting_thread.getLootItem(extra_paramaters);
		case ITEM_PRICE:
			return looting_thread.getItemPrice(extra_paramaters);
		case AMOUNT_LOOTED_OF_ITEM:
			return looting_thread.getAmountLooted(extra_paramaters);
		case FOOD:
			return eat_thread.getFood();
		case MONSTER_IDS:
			return this.combat_thread.getMonsterIDs();
		case HOME_TILE:
			return this.combat_thread.getHomeTile();
		case IS_RANGING:
			return this.combat_thread.isRanging();
		case RUN_TIME:
			return new Value<Long>(this.main_class.getRunningTime());
		case FLICKER_PRAYER:
			return this.combat_thread.getPrayer();
		case EAT_FOR_SPACE:
			return this.looting_thread.shouldEatForSpace();
		case LOOT_ITEM_NAMES:
			return this.looting_thread.getAllLootableItemNames();
		case IS_BONES_TO_PEACHES:
			return new Value<Boolean>(this.eat_thread.isUsingBonesToPeaches());
		case LOOT_IN_COMBAT:
			return new Value<Boolean>(this.looting_thread.lootInCombat());
		case WAIT_FOR_LOOT:
			return new Value<Boolean>(this.looting_thread.waitForLoot());
		case BANKER:
			return new Value<Banker>(this.banker);
		case POSSIBLE_MONSTERS:
			return this.combat_thread.getPossibleMonsters();
		case SPECIAL_ATTACK_WEAPON:
			return this.combat_thread.getSpecialAttackWeapon();
		case GUTHANS_IDS:
			return new Value<int[]>(this.combat_thread.getGuthansIDs());
		case ALL_LOOT_ITEMS:
			return this.looting_thread.getLootItems();
		case FOOD_WITHDRAW_AMOUNT:
			return this.banker.getFoodWithdrawAmount();
		case FLICKER:
			return this.combat_thread.shouldFlicker();
		case USE_GUTHANS:
			return new Value<Boolean>(this.combat_thread.getUseGuthans());
		case COMBAT_RADIUS:
			return this.combat_thread.getCombatRadius();
		case WORLD_HOP_TOLERANCE:
			return this.combat_thread.getWorldHopTolerance();
		case SAFE_SPOT_TILE:
			return this.combat_thread.getSafeSpot();
		case ARMOR_HOLDER_IDS:
			return this.combat_thread.getArmorHolderIDs();
		default:
			break;
		}
		return null;
	}

	public void set(ValueType type, Value<?> val) {
		switch (type) {
		case MINIMUM_LOOT_VALUE:
			this.looting_thread.setMinimumLootValue((Integer) val.getValue());
			break;
		case FOOD:
			eat_thread.setFood((Food) val.getValue());
			break;
		case MONSTER_IDS:
			this.combat_thread.setMonsterIDs((int[]) val.getValue());
			break;
		case HOME_TILE:
			this.combat_thread.setHomeTile((RSTile) val.getValue());
			break;
		case IS_RANGING:
			this.combat_thread.setRanging((Boolean) val.getValue());
			break;
		case FLICKER_PRAYER:
			this.combat_thread.setPrayer((Prayer) val.getValue());
			break;
		case LOOT_ITEM_NAMES:
			this.looting_thread.addPossibleLootItem(true,
					(String[]) val.getValue());
			break;
		case LOOT_ITEM:
			this.looting_thread.addLootItem((LootItem) val.getValue());
			break;
		case LOOT_IN_COMBAT:
			this.looting_thread.setLootInCombat((Boolean) val.getValue());
			break;
		case WAIT_FOR_LOOT:
			this.looting_thread.setWaitForLoot((Boolean) val.getValue());
			break;
		case SPECIAL_ATTACK_WEAPON:
			this.combat_thread.setSpecialAttackWeapon((Weapon) val.getValue());
			break;
		case USE_GUTHANS:
			this.combat_thread.setUseGuthans((Boolean) val.getValue());
			break;
		case FOOD_WITHDRAW_AMOUNT:
			this.banker.setFoodWithdrawAmount((Integer) val.getValue());
			break;
		case FLICKER:
			this.combat_thread.setUseFlicker((Boolean) val.getValue());
			break;
		case COMBAT_RADIUS:
			this.combat_thread.setCombatRadius((Integer) val.getValue());
			break;
		case WORLD_HOP_TOLERANCE:
			this.combat_thread.setWorldHopTolerance((Integer) val.getValue());
			break;
		case SAFE_SPOT_TILE:
			this.combat_thread.setSafeSpot((RSTile) val.getValue());
			break;
		default:
			break;
		}

	}

	@SuppressWarnings("deprecation")
	public void pause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type)) {
				GenericMethods.println(x.getName() + " " + x.getId());
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

	private void run() {
		Walking.setControlClick(true);
		this.combat_thread.start();
		this.combat_thread.initiate();
		this.looting_thread.start();
		this.eat_thread.start();
		this.price_updater_thread.start();
		this.combat_thread.setAmmo();
		if (this.eat_thread.isUsingBonesToPeaches())
			this.looting_thread.addPossibleLootItem(true, "Bones");
	}

	public void checkAndExecuteProgression() {
		this.handler.checkAndExecute();
	}

	public boolean isRunning() {
		return this.main_class.isRunning();
	}

	public void checkThreads() {
		this.handler.checkAndExecute();
		// this.checkSkillsForExperiencedGained();
		if (this.combat_thread.isPaused()
				&& Timing.timeFromMark(this.combat_thread.getPauseTime()) > 30000) {
			this.combat_thread.resume();
			this.combat_thread.setPaused(false);
		}
	}

	public boolean hasStarted() {
		return this.started;
	}

	public ABCUtil getABCUtil() {
		return this.abc_util;
	}

	public void attackTarget() {
		this.combat_thread.attackCurrentTarget();
	}

	public static boolean hasBeenInitialized() {
		return dispatcher != null;
	}

	public void bank(boolean world_hop) {
		this.banker.bank(world_hop);
	}

	public Banker getBanker() {
		return this.banker;
	}

	public BaseGUI getGUI() {
		return this.gui;
	}

}
