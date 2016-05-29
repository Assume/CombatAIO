package scripts.CombatAIO.com.base.main.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.util.Util;

/**
 * The Items07 class is a utility class that provides methods related to
 * RSItem's.
 * 
 * @author Nolan
 */
public class Items07 {

	private static final ArrayList<DumpItem> DUMP_DATA = new ArrayList<>();
	public static final Object LOCK = new Object();

	/**
	 * Gets all of the item definitions that exist.
	 * 
	 * @return An array of all of the item definitions.
	 */
	public static final RSItemDefinition[] getAllDefinitions() {
		List<RSItemDefinition> definitions = new ArrayList<>();
		for (int i = 0; i < 25000; i++) {
			RSItemDefinition def = RSItemDefinition.get(i);
			if (def != null) {
				definitions.add(def);
			}
		}
		return definitions.toArray(new RSItemDefinition[definitions.size()]);
	}

	/**
	 * Generates a new empty item array with a length of 0.
	 * 
	 * @return A new empty item array.
	 */
	public static final RSItem[] empty() {
		return new RSItem[0];
	}

	/**
	 * Gets the item dump file.
	 * 
	 * @return The item dump file.
	 */
	public static File getDumpFile() {
		return FileUtil.getFile(true, "item_dump", "txt");
	}

	/**
	 * Checks to see if the items have been dumped yet.
	 * 
	 * @return True if the items have been dumped, false otherwise.
	 */
	public static boolean hasDumped() {
		return getDumpFile() != null;
	}

	private static final String dump_url = "http://www.mediafire.com/download/j6s88az8rj2750k/item_dump.txt";

	/**
	 * Dumps items into a text file in the Sigma directory of TRiBot.
	 * 
	 * This method is NOT thread safe.
	 */
	public static void dump() {
		synchronized (LOCK) {
			if (getDumpFile() == null) {
				Downloader.download(dump_url, Util.getWorkingDirectory().getAbsolutePath() + "/Base", "item_dump.txt");
			}
		}
	}

	/**
	 * Gets the data from the item dump.
	 * 
	 * Dumps the item data if a dump file has not yet been created.
	 * 
	 * @return The data.
	 */
	public static String[] getDumpData() {
		if (!hasDumped()) {
			dump();
		}
		ArrayList<String> dumpData = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(getDumpFile()));
			String ln;
			while ((ln = br.readLine()) != null) {
				dumpData.add(ln);
			}
			br.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return dumpData.toArray(new String[dumpData.size()]);
	}

	/**
	 * Gets the data from the item dump.
	 * 
	 * Dumps the item data if a dump file has not yet been created.
	 * 
	 * @return The data.
	 */
	public static DumpItem[] getDumpItemData() {
		synchronized (LOCK) {
			if (!hasDumped()) {
				dump();
			}
			if (DUMP_DATA.isEmpty()) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(getDumpFile()));
					String ln;
					while ((ln = br.readLine()) != null) {
						DUMP_DATA.add(DumpItem.fromString(ln));
					}
					br.close();
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return DUMP_DATA.toArray(new DumpItem[DUMP_DATA.size()]);
		}
	}

	/**
	 * Returns the DumpItem matching the specified ID.
	 * 
	 * @param id
	 *            The ID of the item.
	 * @return The DumpItem matching the specified ID.
	 */
	public static DumpItem getDumpItem(int id) {
		synchronized (LOCK) {
			for (DumpItem item : getDumpItemData()) {
				if (item.getId() == id) {
					return item;
				}
			}
			return null;
		}
	}

	/**
	 * Returns the unnoted id of the specified RSItem, regardless of its state.
	 * Returns -1 if the item or item definition is null.
	 * 
	 * @param item
	 *            The RSItem.
	 * @return The unnoted id of the specified RSItem, regardless of its state.
	 *         Returns -1 if the item or item definition is null.
	 */
	public static int getIdUnnoted(RSItem item) {
		if (item != null) {
			return getDefIdUnnoted(item.getDefinition());
		}
		return -1;
	}

	/**
	 * Returns the noted id of the specified RSItem, regardless of its state.
	 * Returns -1 if the item or item definition is null.
	 * 
	 * @param item
	 *            The RSItem.
	 * @return The noted id of the specified RSItem, regardless of its state.
	 *         Returns -1 if the item or item definition is null.
	 */
	public static int getIdNoted(RSItem item) {
		if (item != null) {
			RSItemDefinition definition = item.getDefinition();
			if (definition != null && !definition.isStackable()) {
				return definition.isNoted() ? item.getID() : definition.getSwitchNoteItemID();
			}
		}
		return -1;
	}

	/**
	 * Returns the current id of the specified RSItem.
	 * 
	 * @param item
	 *            The RSItem.
	 * @return The current id of the specified RSItem.
	 */
	public static int getId(RSItem item) {
		return item.getID();
	}

	/**
	 * Returns the unnoted id of the specified RSItemDefinition, regardless of
	 * its state. Returns -1 if the item or item definition is null.
	 * 
	 * @param definition
	 *            The definition.
	 * @return The unnoted id of the specified RSItemDefinition, regardless of
	 *         its state. Returns -1 if the item or item definition is null.
	 */
	public static int getDefIdUnnoted(RSItemDefinition definition) {
		if (definition != null) {
			return definition.isNoted() ? definition.getSwitchNoteItemID() : definition.getID();
		}
		return -1;
	}

	/**
	 * Returns the noted id of the specified RSItemDefinition, regardless of its
	 * state. Returns -1 if the item or item definition is null.
	 * 
	 * @param definition
	 * @return The noted id of the specified RSItemDefinition, regardless of its
	 *         state. Returns -1 if the item or item definition is null.
	 */
	public static int getDefIdNoted(RSItemDefinition definition) {
		if (definition != null && (definition.isNoted() || !definition.isStackable())) {
			return definition.isNoted() ? definition.getID() : definition.getSwitchNoteItemID();
		}
		return -1;
	}

	/**
	 * Returns the current id of the specified RSItemDefinition.
	 * 
	 * @param definition
	 *            The RSItemDefinition.
	 * @return The current id of the specified RSItemDefinition.
	 */
	public static int getDefId(RSItemDefinition definition) {
		return definition.getID();
	}

	/**
	 * Returns the unnoted id of the item represented by the specified id,
	 * regardless of its state. Returns -1 if the item or item definition is
	 * null.
	 * 
	 * @param id
	 *            The id of the item, noted or unnoted.
	 * @return The unnoted id of the item represented by the specified id,
	 *         regardless of its state. Returns -1 if the item or item
	 *         definition is null.
	 */
	public static int getIdUnnoted(int id) {
		return getDefIdUnnoted(RSItemDefinition.get(id));
	}

	/**
	 * Returns the noted id of the item represented by the specified id,
	 * regardless of its state. Returns -1 if the item or item definition is
	 * null.
	 * 
	 * @param id
	 *            The id of the item, noted or unnoted.
	 * @return The noted id of the item represented by the specified id,
	 *         regardless of its state. Returns -1 if the item or item
	 *         definition is null.
	 */
	public static int getIdNoted(int id) {
		return getDefIdNoted(RSItemDefinition.get(id));
	}

	/**
	 * Checks to see if the item associated with the specified id is an F2P
	 * item.
	 * 
	 * @param id
	 *            The id.
	 * @return True if the item associated with the specified id is an F2P item,
	 *         false otherwise.
	 */
	public static boolean isF2P(int id) {
		RSItemDefinition definition = RSItemDefinition.get(id);
		if (definition != null) {
			return !definition.isMembersOnly();
		}
		return false;
	}

	/**
	 * Checks to see if the specified RSItem matches any of the specified ids.
	 * 
	 * @param item
	 *            The item being checked.
	 * @param checkCurrentState
	 *            True if the id must match the current state id of the item
	 *            (noted/unnoted), false if the id can match any state id of the
	 *            item.
	 * @param ids
	 *            The ids being checked.
	 * @return True if the specified RSItem matches any of the specified ids,
	 *         false otherwise.
	 */
	public static boolean matches(RSItem item, boolean checkCurrentState, int... ids) {
		if (item != null && ids != null) {
			for (int id : ids) {
				if (getId(item) != -1 && (checkCurrentState ? getId(item) == id
						: (getIdNoted(item) == id || getIdUnnoted(item) == id))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks to see if the specified RSItem matches any of the specified names.
	 * 
	 * The name matching is NOT case sensitive.
	 * 
	 * @param item
	 *            The item being checked.
	 * @param names
	 *            The names being checked.
	 * @return True if the specified RSItem matches any of the specified names,
	 *         false otherwise.
	 */
	public static boolean matches(RSItem item, String... names) {
		if (item != null && names != null) {
			RSItemDefinition definition = item.getDefinition();
			if (definition != null) {
				String itemName = definition.getName();
				if (itemName != null) {
					for (String name : names) {
						if (name != null && itemName.equalsIgnoreCase(name)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns the name of the item based on the specified id.
	 * 
	 * @param id
	 *            The id of the item. Accepts non-noted ids only.
	 * @return The name of the item based on the specified id.
	 */
	public static String getName(int id) {
		for (DumpItem item : getDumpItemData()) {
			if (item.matches(id)) {
				return item.getName();
			}
		}
		return null;
	}

	public static String[] getActions(RSItem item) {
		RSItemDefinition def = item.getDefinition();
		if (def == null)
			return new String[0];
		return def.getActions();
	}
}
