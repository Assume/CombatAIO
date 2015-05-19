package scripts.CombatAIO.com.base.main.utils;

import java.util.ArrayList;
import org.tribot.api2007.types.RSItem;

/**
 * 
 * @author Spencer
 */
public class DataItem {

	private int unnotedId;
	private int notedId;
	private int unnotedStack;
	private int notedStack;

	public DataItem() {
		unnotedId = -1;
		notedId = -1;
		unnotedStack = 0;
		notedStack = 0;
	}

	/**
	 * Resets the unnoted and noted stacks back to 0.
	 */
	public void resetStacks() {
		setUnnotedStack(0);
		setNotedStack(0);
	}

	/**
	 * Returns the unnoted id of this DataItem.
	 * 
	 * @return The unnoted id of this DataItem.
	 */
	public int getUnnotedId() {
		return unnotedId;
	}

	/**
	 * Sets the unnoted id of this DataItem to the specified value.
	 * 
	 * @param id
	 *            The unnoted id.
	 */
	public void setUnnotedId(int id) {
		this.unnotedId = id;
	}

	/**
	 * Returns the noted id of this DataItem.
	 * 
	 * @return The noted id of this DataItem.
	 */
	public int getNotedId() {
		return notedId;
	}

	/**
	 * Sets the noted id of this DataItem to the specified value.
	 * 
	 * @param notedId
	 *            The noted id.
	 */
	public void setNotedId(int notedId) {
		this.notedId = notedId;
	}

	/**
	 * Returns the unnoted stack of this DataItem.
	 * 
	 * @return The unnoted stack of this DataItem.
	 */
	public int getUnnotedStack() {
		return unnotedStack;
	}

	/**
	 * Sets the unnoted stack of this DataItem to the specified value.
	 * 
	 * @param stack
	 *            The unnoted stack.
	 */
	public void setUnnotedStack(int stack) {
		this.unnotedStack = stack;
	}

	/**
	 * Removes the specified amount from this DataItem's unnoted stack.
	 * 
	 * @param amount
	 *            The amount being removed.
	 * @return The stack number that is left after the removal.
	 */
	public int removeFromUnnotedStack(int amount) {
		unnotedStack -= amount;
		return getUnnotedStack();
	}

	/**
	 * Returns the noted stack of this DataItem.
	 * 
	 * @return The noted stack of this DataItem.
	 */
	public int getNotedStack() {
		return notedStack;
	}

	/**
	 * Sets the noted stack of this DataItem to the specified value.
	 * 
	 * @param notedStack
	 *            The noted stack.
	 */
	public void setNotedStack(int notedStack) {
		this.notedStack = notedStack;
	}

	/**
	 * Removes the specified amount from this DataItem's noted stack.
	 * 
	 * @param amount
	 *            The amount being removed.
	 * @return The stack number that is left after the removal.
	 */
	public int removeFromNotedStack(int amount) {
		notedStack -= amount;
		return getNotedStack();
	}

	/**
	 * Sets the noted or unnoted stack of this DataItem depending on the status
	 * of the specified RSItem. Does nothing to the stack that isn't set.
	 * 
	 * @param item
	 *            The RSItem.
	 */
	public void setStack(RSItem item) {
		if (item.getDefinition().isNoted()) {
			setNotedStack(item.getStack());
		} else {
			setUnnotedStack(item.getStack());
		}
	}

	/**
	 * Updates this DataItem's stack to be the addition of the specified
	 * RSItem's stack value.
	 * 
	 * @param item
	 *            The RSItem.
	 */
	public void updateStack(RSItem item) {
		if (item.getDefinition().isNoted()) {
			setNotedStack(item.getStack() + getNotedStack());
		} else {
			setUnnotedStack(item.getStack() + getUnnotedStack());
		}
	}

	/**
	 * Returns the count of both noted and unnoted items.
	 * 
	 * @return The count of both noted and unnoted items.
	 */
	public int getStack() {
		return getUnnotedStack() + getNotedStack();
	}

	/**
	 * Removes the specified amount from this DataItem's stack.
	 * 
	 * Will clear the unnoted stack before moving onto the noted stack.
	 * 
	 * @param amount
	 *            The amount being removed.
	 * @return The number of items left after the stack has been cleared.
	 */
	public int removeFromStack(int amount) {
		amount = removeFromUnnotedStack(amount);
		if (amount < 0) {
			setUnnotedStack(0);
			removeFromNotedStack(-amount);
		}
		return getStack();
	}

	/**
	 * Checks to see if the unnoted stack of this item matches the specified
	 * amount.
	 * 
	 * @param amount
	 *            The amount.
	 * @param exact
	 *            True if the first value must be exactly equal to the second,
	 *            false if the first must be greater than or equal to the
	 *            second.
	 * @param other
	 *            True if the comparison should be flipped, false otherwise.
	 * @return True if the unnoted stack of this item matches the specified
	 *         amount, false otherwise.
	 */
	public boolean unnotedStackMatches(int amount, boolean exact, boolean other) {
		if (exact) {
			return amount == unnotedStack;
		} else {
			return other ? amount >= unnotedStack : unnotedStack >= amount;
		}
	}

	/**
	 * Checks to see if the noted stack of this item matches the specified
	 * amount.
	 * 
	 * @param amount
	 *            The amount.
	 * @param exact
	 *            True if the first value must be exactly equal to the second,
	 *            false if the first must be greater than or equal to the
	 *            second.
	 * @param other
	 *            True if the comparison should be flipped, false otherwise.
	 * @return True if the noted stack of this item matches the specified
	 *         amount, false otherwise.
	 */
	public boolean notedStackMatches(int amount, boolean exact, boolean other) {
		if (exact) {
			return amount == notedStack;
		} else {
			return other ? amount >= notedStack : notedStack >= amount;
		}
	}

	/**
	 * Checks to see if this DataItem is equal to the specified RSItem.
	 * 
	 * @param item
	 *            The RSItem being compared.
	 * @return True if this DataItem is equal to the specified RSItem, false
	 *         otherwise.
	 */
	public boolean equalsRS(RSItem item) {
		return Items07.getIdUnnoted(item) == getUnnotedId();
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 19 * hash + this.unnotedId;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DataItem other = (DataItem) obj;
		return (this.unnotedId > 0 && this.unnotedId == other.unnotedId)
				|| (this.notedId > 0 && this.notedId == other.notedId);
	}

	/**
	 * Returns the DataItem contained within the specified ArrayList that
	 * matches the specified id.
	 * 
	 * @param id
	 *            The id.
	 * @param items
	 *            The ArrayList of DataItems.
	 * @return The DataItem contained within the specified ArrayList that
	 *         matches the specified id.
	 */
	public static DataItem getItem(int id, ArrayList<DataItem> items) {
		for (DataItem item : items) {
			if (item.getUnnotedId() == id || item.getNotedId() == id) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Returns a DataItem representation of the specified RSItem.
	 * 
	 * @param item
	 *            The RSItem.
	 * @return A DataItem representation of the specified RSItem.
	 */
	public static DataItem fromRSItem(RSItem item) {
		DataItem dataItem = new DataItem();
		dataItem.setUnnotedId(Items07.getIdUnnoted(item));
		if (!item.getDefinition().isStackable()) {
			dataItem.setNotedId(Items07.getIdNoted(item));
		}
		dataItem.setStack(item);
		return dataItem;
	}

	/**
	 * Returns an ArrayList of DataItems from the specified array of RSItems.
	 * 
	 * @param items
	 *            The RSItems.
	 * @return An ArrayList of DataItems from the specified array of RSItems.
	 */
	public static ArrayList<DataItem> fromRSItems(RSItem... items) {
		ArrayList<DataItem> dataItems = new ArrayList<>();
		for (RSItem item : items) {
			DataItem dataItem = getItem(Items07.getIdUnnoted(item), dataItems);
			if (dataItem != null) {
				dataItem.updateStack(item);
			} else {
				dataItems.add(DataItem.fromRSItem(item));
			}
		}
		return dataItems;
	}
}
