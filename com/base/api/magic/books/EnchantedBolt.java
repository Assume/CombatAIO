package scripts.CombatAIO.com.base.api.magic.books;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;

import scripts.CombatAIO.com.base.api.magic.Rune;
import scripts.CombatAIO.com.base.api.magic.SpellType;
import scripts.CombatAIO.com.base.api.magic.Staff;


/**
 * @author Starfox
 */
public enum EnchantedBolt implements Serializable {

	OPAL("Opal", 2, new Rune[] { Rune.COSMIC, Rune.AIR }, new int[] { 1, 2 },
			SpellType.BOLT_ENCHANTMENT), SAPPHIRE("Sapphire", 3, new Rune[] {
			Rune.COSMIC, Rune.WATER, Rune.MIND }, new int[] { 1, 1, 1 },
			SpellType.BOLT_ENCHANTMENT), JADE("Jade", 4, new Rune[] {
			Rune.COSMIC, Rune.EARTH }, new int[] { 1, 2 },
			SpellType.BOLT_ENCHANTMENT), PEARL("Pearl", 5, new Rune[] {
			Rune.COSMIC, Rune.WATER }, new int[] { 1, 2 },
			SpellType.BOLT_ENCHANTMENT), EMERALD("Emerald", 6, new Rune[] {
			Rune.COSMIC, Rune.NATURE, Rune.AIR }, new int[] { 1, 1, 3 },
			SpellType.BOLT_ENCHANTMENT), TOPAZ("Topaz", 7, new Rune[] {
			Rune.COSMIC, Rune.FIRE }, new int[] { 1, 2 },
			SpellType.BOLT_ENCHANTMENT), RUBY("Ruby", 8, new Rune[] {
			Rune.COSMIC, Rune.BLOOD, Rune.FIRE }, new int[] { 1, 1, 5 },
			SpellType.BOLT_ENCHANTMENT), DIAMOND("Diamond", 9, new Rune[] {
			Rune.COSMIC, Rune.LAW, Rune.EARTH }, new int[] { 1, 2, 10 },
			SpellType.BOLT_ENCHANTMENT), DRAGONSTONE("Dragonstone", 10,
			new Rune[] { Rune.COSMIC, Rune.SOUL, Rune.EARTH }, new int[] { 1,
					1, 15 }, SpellType.BOLT_ENCHANTMENT), ONYX("Onyx", 11,
			new Rune[] { Rune.COSMIC, Rune.DEATH, Rune.FIRE }, new int[] { 1,
					1, 20 }, SpellType.BOLT_ENCHANTMENT);

	public static final int MASTER_INDEX = 80;
	public static final int COMPONENT_INDEX = 2;

	private final String name;
	private final int childIndex;
	private final Rune[] requiredRunes;
	private final int[] numberOfRunes;
	private final SpellType type;

	/**
	 * Constructs a new enchanted bolt.
	 * 
	 * @param name
	 *            The name of the enchanted bolt.
	 * @param childIndex
	 *            The child index of the interface.
	 * @param requiredRunes
	 *            The required runes.
	 * @param numberOfRunes
	 *            The required number of runes.
	 * @param type
	 *            The spell type.
	 */
	EnchantedBolt(String name, int childIndex, Rune[] requiredRunes,
			int[] numberOfRunes, SpellType type) {
		this.name = name;
		this.childIndex = childIndex;
		this.requiredRunes = requiredRunes;
		this.numberOfRunes = numberOfRunes;
		this.type = type;
	}

	/**
	 * Gets the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the child index.
	 * 
	 * @return The child index.
	 */
	public int getChildIndex() {
		return this.childIndex;
	}

	/**
	 * Gets the required runes.
	 * 
	 * @return The required runes.
	 */
	public Rune[] getRequiredRunes() {
		return this.requiredRunes;
	}

	/**
	 * Gets the required number of runes.
	 * 
	 * @return The required number of runes.
	 */
	public int[] getNumberOfRunes() {
		return this.numberOfRunes;
	}

	/**
	 * Gets the spell type.
	 * 
	 * @return The spell type.
	 */
	public SpellType getType() {
		return this.type;
	}

	/**
	 * Checks to see if the player has the required runes to cast the spell to
	 * enchant the bolt.
	 * <p>
	 * This method takes into account any staff that the player may have
	 * equipped.
	 * 
	 * @return True if the player has the required runes, false otherwise.
	 */
	public boolean hasRequiredRunes() {
		final List<Rune> currentRunes = new ArrayList<>();
		final int requiredAmount = getRequiredRunes().length;

		Staff staff = Staff.getEquipped();

		// check the runes in the inventory
		for (int i = 0; i < getRequiredRunes().length; i++) {
			if (Inventory.getCount(getRequiredRunes()[i].getId()) >= getNumberOfRunes()[i]) {
				currentRunes.add(getRequiredRunes()[i]);
			}
		}

		// check the runes that the staff provides
		if (staff != null && currentRunes.size() < requiredAmount) {
			for (Rune r : staff.getRunes()) {
				for (Rune r2 : getRequiredRunes()) {
					if (r.getId() == r2.getId() && !currentRunes.contains(r)) {
						currentRunes.add(r);
					}
				}
			}
		}

		// check the amount of the current runes against the amount of required
		// runes; if they are the same, we have the required runes.
		return currentRunes.size() == requiredAmount;
	}

	/**
	 * Checks to see if the interface for the bolt is valid.
	 * 
	 * @return True if it is valid, false otherwise.
	 */
	public boolean isInterfaceValid() {
		if (!Interfaces.isInterfaceValid(MASTER_INDEX)) {
			return false;
		}
		RSInterfaceChild child = Interfaces.get(MASTER_INDEX, getChildIndex());
		if (child != null) {
			RSInterfaceComponent comp = child.getChild(2);
			return comp != null;
		}
		return false;
	}

	/**
	 * Selects the spell to open the enchant bolt interface.
	 * 
	 * @return True if the spell was selected, false otherwise.
	 */
	public boolean selectSpell() {

		return Magic.selectSpell("Enchant Crossbow Bolt");
	}

	/**
	 * Clicks the component for the bolt on the interface.
	 * 
	 * @return True if the click was successful, false otherwise.
	 */
	public boolean clickComponent() {
		RSInterfaceChild child = Interfaces.get(MASTER_INDEX, getChildIndex());
		if (child != null) {
			RSInterfaceComponent comp = child.getChild(COMPONENT_INDEX);
			return comp != null && Clicking.click(comp);
		}
		return false;
	}

	@Override
	public String toString() {
		return getName() + " bolt enchant";
	}
}
