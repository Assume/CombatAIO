package scripts.CombatAIO.com.base.api.general.walking.custom.background.magic.books;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSInterface;

import scripts.priv.drennon.background.magic.Rune;
import scripts.priv.drennon.background.magic.SpellType;
import scripts.priv.drennon.background.magic.Staff;

/**
 * Constants that represent spells from the normal magic book.
 * 
 * @author Starfox
 */
public enum NormalSpell implements Serializable {

	// COMBAT SPELLS
	/*
	 * WIND_STRIKE("Wind Strike", 1, new Rune[]{Rune.MIND, Rune.AIR}, new
	 * int[]{1, 1}, SpellType.COMBAT), WATER_STRIKE("Water Strike", 5, new
	 * Rune[]{Rune.MIND, Rune.WATER, Rune.AIR}, new int[]{1, 1, 1},
	 * SpellType.COMBAT), EARTH_STRIKE("Earth Strike", 9, new Rune[]{Rune.MIND,
	 * Rune.EARTH, Rune.AIR}, new int[]{1, 2, 1}, SpellType.COMBAT),
	 * FIRE_STRIKE("Fire Strike", 13, new Rune[]{Rune.MIND, Rune.FIRE,
	 * Rune.AIR}, new int[]{1, 3, 2}, SpellType.COMBAT), WIND_BOLT("Wind Bolt",
	 * 17, new Rune[]{Rune.CHAOS, Rune.AIR}, new int[]{1, 2}, SpellType.COMBAT),
	 * WATER_BOLT("Water Bolt", 23, new Rune[]{Rune.CHAOS, Rune.WATER,
	 * Rune.AIR}, new int[]{1, 2, 2}, SpellType.COMBAT),
	 * EARTH_BOLT("Earth Bolt", 29, new Rune[]{Rune.CHAOS, Rune.EARTH,
	 * Rune.AIR}, new int[]{1, 3, 2}, SpellType.COMBAT), FIRE_BOLT("Fire Bolt",
	 * 23, new Rune[]{Rune.CHAOS, Rune.FIRE, Rune.AIR}, new int[]{1, 4, 3},
	 * SpellType.COMBAT), CRUMBLE_UNDEAD("Crumble Undead", 39, new
	 * Rune[]{Rune.CHAOS, Rune.EARTH, Rune.AIR}, new int[]{1, 2, 2},
	 * SpellType.COMBAT), WIND_BLAST("Wind Blast", 41, new Rune[]{Rune.DEATH,
	 * Rune.AIR}, new int[]{1, 3}, SpellType.COMBAT), WATER_BLAST("Water Blast",
	 * 47, new Rune[]{Rune.DEATH, Rune.WATER, Rune.AIR}, new int[]{1, 3, 3},
	 * SpellType.COMBAT), IBAN_BLAST("Iban Blast", 50, new Rune[]{Rune.DEATH,
	 * Rune.FIRE}, new int[]{1, 5}, SpellType.COMBAT), MAGIC_DART("Magic Dart",
	 * 50, new Rune[]{Rune.DEATH, Rune.MIND}, new int[]{1, 4},
	 * SpellType.COMBAT), EARTH_BLAST("Earth Blast", 53, new Rune[]{Rune.DEATH,
	 * Rune.EARTH, Rune.AIR}, new int[]{1, 4, 3}, SpellType.COMBAT),
	 * FIRE_BLAST("Fire Blast", 59, new Rune[]{Rune.DEATH, Rune.FIRE, Rune.AIR},
	 * new int[]{1, 5, 4}, SpellType.COMBAT), WIND_WAVE("Wind Wave", 62, new
	 * Rune[]{Rune.BLOOD, Rune.AIR}, new int[]{1, 5}, SpellType.COMBAT),
	 * WATER_WAVE("Water Wave", 65, new Rune[]{Rune.BLOOD, Rune.WATER,
	 * Rune.AIR}, new int[]{1, 7, 5}, SpellType.COMBAT),
	 * EARTH_WAVE("Earth Wave", 70, new Rune[]{Rune.BLOOD, Rune.EARTH,
	 * Rune.AIR}, new int[]{1, 7, 5}, SpellType.COMBAT), FIRE_WAVE("Fire Wave",
	 * 75, new Rune[]{Rune.BLOOD, Rune.FIRE, Rune.AIR}, new int[]{1, 7, 5},
	 * SpellType.COMBAT),
	 */

	// CURSE SPELLS
	CONFUSE("Confuse", 3, new Rune[] { Rune.BODY, Rune.EARTH, Rune.WATER },
			new int[] { 1, 2, 3 }, SpellType.CURSE), WEAKEN("Weaken", 11,
			new Rune[] { Rune.BODY, Rune.EARTH, Rune.WATER }, new int[] { 1, 2,
					3 }, SpellType.CURSE), CURSE("Curse", 19, new Rune[] {
			Rune.BODY, Rune.EARTH, Rune.WATER }, new int[] { 1, 3, 2 },
			SpellType.CURSE), VULNERABILITY("Vulnerability", 66, new Rune[] {
			Rune.SOUL, Rune.EARTH, Rune.WATER }, new int[] { 1, 5, 5 },
			SpellType.CURSE), ENFEEBLE("Enfeeble", 73, new Rune[] { Rune.SOUL,
			Rune.EARTH, Rune.WATER }, new int[] { 1, 8, 8 }, SpellType.CURSE), STUN(
			"Stun", 80, new Rune[] { Rune.SOUL, Rune.EARTH, Rune.WATER },
			new int[] { 1, 12, 12 }, SpellType.CURSE), BIND("Bind", 20,
			new Rune[] { Rune.NATURE, Rune.EARTH, Rune.WATER }, new int[] { 2,
					3, 3 }, SpellType.CURSE), SNARE("Snare", 50, new Rune[] {
			Rune.NATURE, Rune.EARTH, Rune.WATER }, new int[] { 3, 4, 4 },
			SpellType.CURSE), ENTANGLE("Entangle", 79, new Rune[] {
			Rune.NATURE, Rune.EARTH, Rune.WATER }, new int[] { 4, 5, 4 },
			SpellType.CURSE),

	// TELEPORT SPELLS
	LUMBRIDGE_HOME_TELEPORT("Lumbridge Home Teleport", 0, new Rune[] {},
			new int[] {}, SpellType.TELEPORT), VARROCK_TELEPORT(
			"Varrock Teleport", 25,
			new Rune[] { Rune.LAW, Rune.AIR, Rune.FIRE },
			new int[] { 1, 3, 1 }, SpellType.TELEPORT), LUMBRIDGE_TELEPORT(
			"Lumbridge Teleport", 31, new Rune[] { Rune.LAW, Rune.AIR,
					Rune.EARTH }, new int[] { 1, 3, 1 }, SpellType.TELEPORT), FALADOR_TELEPORT(
			"Falador Teleport", 37,
			new Rune[] { Rune.LAW, Rune.AIR, Rune.WATER },
			new int[] { 1, 3, 1 }, SpellType.TELEPORT), HOUSE_TELEPORT(
			"Teleport to House", 40, new Rune[] { Rune.LAW, Rune.EARTH,
					Rune.AIR }, new int[] { 1, 1, 1 }, SpellType.TELEPORT), CAMELOT_TELEPORT(
			"Camelot Teleport", 45, new Rune[] { Rune.LAW, Rune.AIR },
			new int[] { 1, 5 }, SpellType.TELEPORT), ARDOUGNE_TELEPORT(
			"Ardougne Teleport", 51, new Rune[] { Rune.LAW, Rune.WATER },
			new int[] { 2, 2 }, SpellType.TELEPORT), WATCHTOWER_TELEPORT(
			"Watchtower Teleport", 58, new Rune[] { Rune.LAW, Rune.EARTH },
			new int[] { 2, 2 }, SpellType.TELEPORT), TROLLHEIM_TELEPORT(
			"Trollheim Teleport", 61, new Rune[] { Rune.LAW, Rune.FIRE },
			new int[] { 2, 2 }, SpellType.TELEPORT),

	// TELEOTHER SPELLS
	TELE_OTHER_LUMBRIDGE("Teleother Lumbridge", 74, new Rune[] { Rune.SOUL,
			Rune.LAW, Rune.EARTH }, new int[] { 1, 1, 1 }, SpellType.TELE_OTHER), TELE_OTHER_FALADOR(
			"Teleother Falador", 82, new Rune[] { Rune.SOUL, Rune.LAW,
					Rune.WATER }, new int[] { 1, 1, 1 }, SpellType.TELE_OTHER), TELE_OTHER_CAMELOT(
			"Teleother Camelot", 74, new Rune[] { Rune.SOUL, Rune.LAW },
			new int[] { 2, 1 }, SpellType.TELE_OTHER),

	// TELEKINETIC GRAB
	TELEKINETIC_GRAB("Telekinetic Grab", 31, new Rune[] { Rune.LAW, Rune.AIR },
			new int[] { 1, 1 }, SpellType.TELEKINETIC_GRAB),

	// ALCHEMY SPELLS
	LOW_ALCHEMY("Low Level Alchemy", 21, new Rune[] { Rune.NATURE, Rune.FIRE },
			new int[] { 1, 3 }, SpellType.ALCHEMY), HIGH_ALCHEMY(
			"High Level Alchemy", 55, new Rune[] { Rune.NATURE, Rune.FIRE },
			new int[] { 1, 5 }, SpellType.ALCHEMY),

	// BONE SPELLS
	BONES_TO_BANANAS("Bones to Bananas", 15, new Rune[] { Rune.NATURE,
			Rune.EARTH, Rune.WATER }, new int[] { 1, 2, 2 },
			SpellType.BONES_TO_FRUIT), BONES_TO_PEACHES("Bones to Peaches", 60,
			new Rune[] { Rune.NATURE, Rune.EARTH, Rune.WATER }, new int[] { 2,
					4, 4 }, SpellType.BONES_TO_FRUIT),

	// SUPERHEAT
	SUPERHEAT_ITEM("Superheat Item", 43, new Rune[] { Rune.NATURE, Rune.FIRE },
			new int[] { 1, 4 }, SpellType.SUPERHEAT_ITEM),

	// ENCHANTMENT SPELLS
	ENCHANT_LEVEL_ONE("Lvl-1 Enchant", 7,
			new Rune[] { Rune.COSMIC, Rune.WATER }, new int[] { 1, 1 },
			SpellType.ENCHANTMENT), ENCHANT_LEVEL_TWO("Lvl-2 Enchant", 27,
			new Rune[] { Rune.COSMIC, Rune.AIR }, new int[] { 1, 3 },
			SpellType.ENCHANTMENT), ENCHANT_LEVEL_THREE("Lvl-3 Enchant", 49,
			new Rune[] { Rune.COSMIC, Rune.FIRE }, new int[] { 1, 5 },
			SpellType.ENCHANTMENT), ENCHANT_LEVEL_FOUR("Lvl-4 Enchant", 57,
			new Rune[] { Rune.COSMIC, Rune.EARTH }, new int[] { 1, 10 },
			SpellType.ENCHANTMENT), ENCHANT_LEVEL_FIVE("Lvl-5 Enchant", 68,
			new Rune[] { Rune.COSMIC, Rune.EARTH, Rune.WATER }, new int[] { 1,
					15, 15 }, SpellType.ENCHANTMENT), ENCHANT_LEVEL_SIX(
			"Lvl-6 Enchant", 87, new Rune[] { Rune.COSMIC, Rune.FIRE,
					Rune.EARTH }, new int[] { 1, 20, 20 },
			SpellType.ENCHANTMENT);

	public static final int NORMAL_BOOK_MASTER_INDEX = 192;

	private final String name;
	private final int requiredLevel;
	private final Rune[] requiredRunes;
	private final int[] numberOfRunes;
	private final SpellType type;

	private static final long serialVersionUID = -2483090238423784630L;

	/**
	 * Constructs a new normal spell.
	 * 
	 * @param name
	 *            The name of the spell.
	 * @param requiredLevel
	 *            The required level to cast the spell.
	 * @param requiredRunes
	 *            The runes required to cast the spell.
	 * @param numberOfRunes
	 *            The amount of each rune required to cast the spell.
	 */
	private NormalSpell(String name, int requiredLevel, Rune[] requiredRunes,
			int[] numberOfRunes, SpellType type) {
		this.name = name;
		this.requiredLevel = requiredLevel;
		this.requiredRunes = requiredRunes;
		this.numberOfRunes = numberOfRunes;
		this.type = type;
	}

	/**
	 * Gets the name of the spell.
	 * 
	 * @return The name of the spell.
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Gets the level required to cast the spell.
	 * 
	 * @return The level required to cast the spell.
	 */
	public final double getRequiredLevel() {
		return this.requiredLevel;
	}

	/**
	 * Gets the requiredLength runes to cast the NormalSpell.
	 * 
	 * @return The requiredLength runes to cast the NormalSpell.
	 */
	public final Rune[] getRequiredRunes() {
		return this.requiredRunes;
	}

	/**
	 * Gets the number of runes required to cast the NormalSpell.
	 * <p>
	 * This method returns an array in which each element represents the amount
	 * of each rune required to cast the spell.
	 * 
	 * @return The number of runes required to cast the NormalSpell.
	 */
	public final int[] getNumberOfRunes() {
		return this.numberOfRunes;
	}

	/**
	 * Gets the id's of the required runes to cast the spell.
	 * 
	 * @return An array of ints representing the id's of the Runes.
	 */
	public final int[] getRequiredRuneIds() {
		int[] temp = new int[getRequiredRunes().length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = getRequiredRunes()[i].getId();
		}
		return temp;
	}

	/**
	 * Gets the type of the spell.
	 * 
	 * @return The type of the spell.
	 */
	public final SpellType getType() {
		return this.type;
	}

	/**
	 * Checks to see if the player has the required level to cast this spell.
	 * 
	 * @return True if the player has the required level, false otherwise.
	 */
	public final boolean canCast() {
		return this.hasRequiredLevel() && this.hasRequiredRunes();
	}

	public final boolean hasRequiredLevel() {
		return SKILLS.MAGIC.getActualLevel() >= getRequiredLevel();
	}

	/**
	 * Checks to see if the player has the required runes to cast the
	 * NormalSpell.
	 * <p>
	 * This method takes into account any staff that the player may have
	 * equipped.
	 * 
	 * @return True if the player has the required runes, false otherwise.
	 */
	public final boolean hasRequiredRunes() {
		final List<Rune> currentRunes = new ArrayList<>();
		final int requiredAmount = getRequiredRunes().length;

		Staff staff = Staff.getEquipped();

		// check the runes in the inventory
		for (int i = 0; i < getRequiredRunes().length; i++)
			if (Inventory.getCount(getRequiredRunes()[i].getId()) >= getNumberOfRunes()[i])
				currentRunes.add(getRequiredRunes()[i]);

		// check the runes that the staff provides
		if (staff != null && currentRunes.size() < requiredAmount)
			for (Rune r : staff.getRunes())
				for (Rune r2 : getRequiredRunes())
					if (r.getId() == r2.getId() && !currentRunes.contains(r))
						currentRunes.add(r);

		return currentRunes.size() == requiredAmount;
	}

	/**
	 * Selects the spell.
	 * <p>
	 * This method will return true if the spell is already selected.
	 * 
	 * @return True if the spell was successfully selected; false otherwise.
	 */
	public final boolean select() {
		String uptext = Game.getUptext();
		if (uptext != null && uptext.contains(getName() + " ->")) {
			return true;
		}
		fixSelectedMouse();
		return Magic.selectSpell(getName());
	}

	public static void fixSelectedMouse() {
		String uptext = Game.getUptext();
		if (uptext != null && uptext.contains("->")) {
			Mouse.click(1);
		}
	}

	/**
	 * Gets the interface of the spell.
	 * 
	 * @return The interface of the spell. Null if no interface was found.
	 */
	public final RSInterface getInterface() {
		RSInterface master = Interfaces.get(NORMAL_BOOK_MASTER_INDEX);
		if (master != null) {
			RSInterface[] children = master.getChildren();
			if (children != null && children.length > 0) {
				for (RSInterface child : children) {
					if (child != null) {
						String child_name = child.getComponentName();
						if (child_name != null
								&& child_name.contains(getName())) {
							return child;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}
}
