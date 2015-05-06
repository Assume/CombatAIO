package scripts.CombatAIO.com.base.api.threading;

import java.util.ArrayList;
import java.util.Arrays;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Walking;
import org.w3c.dom.Element;

import scripts.CombatAIO.com.base.api.threading.threads.CombatTask;
import scripts.CombatAIO.com.base.api.threading.threads.ConsumptionTask;
import scripts.CombatAIO.com.base.api.threading.threads.Looter;
import scripts.CombatAIO.com.base.api.threading.threads.TargetCalculator;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.main.BaseCombat;
import scripts.xml.XMLReader;
import scripts.xml.XMLWriter;
import scripts.xml.XMLWriter.XMLLoader;
import scripts.xml.XMLable;

public class Dispatcher implements XMLable {

	private static Dispatcher dispatcher;

	public static void create(BaseCombat main_class, long hash_id) {
		dispatcher = new Dispatcher(main_class, hash_id);
	}

	public static Dispatcher get() {
		return dispatcher;
	}

	private boolean started = false;

	public void start() {
		if (!started) {
			run();
			started = true;
		}
	}

	private CombatTask combat_thread;
	private Looter looting_thread;
	private TargetCalculator calculation;
	private ConsumptionTask eat_thread;
	private BaseCombat main_class;
	private long hash_id;

	private Dispatcher(BaseCombat main_class, long hash_id) {
		this.main_class = main_class;
		this.combat_thread = new CombatTask(this.calculation,
				new String[] { "Cow" });
		this.looting_thread = new Looter();
		this.eat_thread = new ConsumptionTask();
		this.hash_id = hash_id != 0 ? this.hash_id : XMLWriter.generateHash();
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
			return null;
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
		case FOOD_NAME:
			return eat_thread.getFoodName();
		case MONSTER_NAMES:
			return this.combat_thread.getMonsterNames();
		case FIRST_MONSTER_NAME:
			return this.combat_thread.getFirstMonsterName();
		case HOME_TILE:
			return this.combat_thread.getHomeTile();
		case IS_RANGING:
			return this.combat_thread.isRanging();
		case RUN_TIME:
			return new Value<Long>(this.main_class.getRunningTime());
		case FLICKER_PRAYER:
			return this.combat_thread.getPrayer();
		default:
			break;
		}
		return null;
	}

	public void set(ValueType type, Value<?> val) {
		switch (type) {
		case MINIMUM_LOOT_VALUE:
			// TODO
			break;
		case FOOD_NAME:
			eat_thread.setFoodName((String) val.getValue());
		case MONSTER_NAMES:
			this.combat_thread.setMonsterNames((String[]) val.getValue());
		case HOME_TILE:
			this.combat_thread.setHomeTile((Positionable) val.getValue());
		case IS_RANGING:
			this.combat_thread.setRanging((Boolean) val.getValue());
		case FLICKER_PRAYER:
			this.combat_thread.setPrayer((Prayer) val.getValue());
		default:
			break;
		}

	}

	@SuppressWarnings("deprecation")
	public void pause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type))
				x.suspend();
	}

	@SuppressWarnings("deprecation")
	public void unpause(PauseType pause_type) {
		for (Threadable x : Threadable.getThreadables())
			if (x.hasPauseType(pause_type))
				x.resume();
	}

	private void run() {
		Walking.setControlClick(true);
		this.combat_thread.start();
		this.looting_thread.start();
		// this.calculation.start();
		// this.banking_thread.start();
		this.eat_thread.start();
		/*
		 * dispatch initital needed threads while(BaseCombat.isRunning())
		 * 
		 * check threads for issues and redispatch if needed check for
		 * conditions requiring the pausing of thread - enum system for this
		 */
	}

	public boolean isRunning() {
		return this.main_class.isRunning();
	}

	public void checkThreads() {
		if (!this.combat_thread.isAlive())
			System.out.println("nope");

	}

	@Override
	public Element toXML(XMLWriter writer, Element parent, Object... data) {
		writer.append(parent, "hash_id", this.hash_id);
		writer.append(parent, "food_name",
				(String) this.get(ValueType.FOOD_NAME).getValue());
		writer.appendArray(
				parent,
				"monster_names",
				(ArrayList<String>) Arrays.asList((String[]) this.get(
						ValueType.MONSTER_NAMES).getValue()),
				getStringXMLLoader());
		writer.append(parent, "prayer", this.get(ValueType.FLICKER_PRAYER)
				.getValue().toString());
		return parent;
	}

	private XMLLoader getStringXMLLoader() {
		return new XMLLoader<String>() {
			@Override
			public <T> XMLable toXMLable(final T t) {
				return new XMLable() {
					@Override
					public Element toXML(XMLWriter writer, Element parent,
							Object... data) {
						String s = (String) t;
						writer.append(parent, "monster_name", s);
						return parent;
					}

					@Override
					public void fromXML(XMLReader reader, String path,
							Object... data) {
					}

					@Override
					public String getXMLName() {
						return "";
					}
				};
			}
		};
	}

	@Override
	public void fromXML(XMLReader reader, String path, Object... data) {

	}

	@Override
	public String getXMLName() {
		return "all_generic_values";
	}

}
