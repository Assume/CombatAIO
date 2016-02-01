package scripts.CombatAIO.com.base.api.tasks.helper;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Player;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.presets.Preset;
import scripts.CombatAIO.com.base.api.presets.PresetFactory;
import scripts.CombatAIO.com.base.api.tasks.threads.CombatTask;
import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.types.enums.Potions;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.api.walking.CWalking;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.custom.types.CEquipment;
import scripts.CombatAIO.com.base.api.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.CombatAIO.com.base.main.utils.ArrayUtil;

public class Banker {

	private List<BankItem> list;
	private int food_amount;

	private boolean log_when_out_of_food = false;

	private long retry_time = System.currentTimeMillis();

	public Banker() {
		list = new ArrayList<BankItem>();
		this.food_amount = 0;
	}

	public void bank(boolean world_hop) {
		if (Dispatcher.get().getCombatTask().isUsingCannon())
			Dispatcher.get().getCombatTask().pickupCannon();
		if (Dispatcher.get().isLiteMode()) {
			Dispatcher
					.get()
					.stop("Stopping script due to needing to bank and being in lite mode");
		}
		executeBanking(world_hop);
	}

	private void executeBanking(boolean world_hop) {
		Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
		if (this.log_when_out_of_food)
			if (Player.getRSPlayer().isInCombat()) {
				Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING);
				this.retry_time = System.currentTimeMillis() + 10000;
				return;
			}
		Prayer p = (Prayer) Dispatcher.get().get(ValueType.PRAYER).getValue();
		if (p.isActivated())
			p.disable();
		if (Dispatcher.get().getPreset() == PresetFactory.Automatic
				|| Dispatcher.get().getPreset().get() == null) {
			JeweleryTeleport teleport = WalkingManager.walk(MovementType.TO_BANK);
			if (teleport != null && teleport.getJewelery() == Jewelery.GLORY)
				checkAndRemoveGlory();
			openBank(world_hop);
			handleBankWindow(world_hop, teleport);
			if (world_hop)
				WorldHopper.changeWorld(WorldHopper.getRandomWorld(true));
			WalkingManager.walk(MovementType.TO_MONSTER);
		} else {
			Preset temp_preset = Dispatcher.get().getPreset().get();
			temp_preset.executeToBank();
			openBank(world_hop);
			handleBankWindow(world_hop, null);
			temp_preset.executeToMonster();
		}
		Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING);
	}

	// TODO DEPOSIT ALL EXCEPT WHAT?
	private void handleBankWindow(boolean world_hop, JeweleryTeleport teleport) {
		int[] weapon = ((Weapon) (Dispatcher.get().get(
				ValueType.SPECIAL_ATTACK_WEAPON).getValue())).getIDs();
		if (Inventory.getAll().length > 0) {
			if (weapon[0] == -1
					&& !((Boolean) Dispatcher.get().get(ValueType.USE_GUTHANS)
							.getValue()))
				Banking.depositAll();
			else
				Banking.depositAllExcept(ArrayUtil.combineArrays(weapon,
						(int[]) Dispatcher.get().get(ValueType.GUTHANS_IDS)
								.getValue(),
						(int[]) Dispatcher.get()
								.get(ValueType.ARMOR_HOLDER_IDS).getValue(),
						CombatHelper.CANNON_IDS,
						new int[] { CombatHelper.CANNON_BALL_ID }));
		}

		boolean withdraw_jewelery = withdraw(list.toArray(new BankItem[list
				.size()]), teleport == null ? null : teleport.getJewelery());
		Banking.close();
		if (withdraw_jewelery
				&& Dispatcher.get().getPreset() != PresetFactory.FIRE_GIANTS_WATERFALL_C
				&& Dispatcher.get().getPreset() != PresetFactory.FIRE_GIANTS_WATERFALL_W)
			CEquipment.equip(teleport.getJewelery().getIDs());
	}

	private BankItem[] getItemsFailedToWithdraw() {
		List<BankItem> temp_list = new ArrayList<BankItem>();
		for (BankItem x : list) {
			if (Jewelery.isJeweleryId(x.getId())) {
				boolean has = false;
				for (int y : Jewelery.getAllIds(x.getId()))
					if (y == x.getId()) {
						has = true;
						break;
					}
				if (!has)
					temp_list.add(x);
			} else if (Inventory.find(x.getId()).length == 0)
				temp_list.add(x);
		}
		return temp_list.toArray(new BankItem[temp_list.size()]);
	}

	private boolean withdraw(BankItem[] items, Jewelery jewelery) {
		Banking.withdraw(this.food_amount,
				((Food) Dispatcher.get().get(ValueType.FOOD).getValue())
						.getId());
		for (BankItem x : list) {
			if (Potions.isPotionId(x.getId()))
				Banking.withdraw(x.getAmount(), Potions.getAllIds(x.getId()));
			else if (Jewelery.isJeweleryId(x.getId()))
				Banking.withdraw(1, Jewelery.getAllIds(x.getId()));
			else
				Banking.withdraw(x.getAmount(), x.getId());
		}
		if (jewelery != null && needToWithdrawJewelery(jewelery)) {
			Banking.withdraw(1, jewelery.getIDs());
			return true;
		}
		return false;
	}

	private static void withdrawYofX(int amount, int... ids) {
		int tot = 0;
		for (int i = 0; i < ids.length && tot <= amount; i++) {
			RSItem[] item = Banking.find(ids[i]);
			if (item.length == 0)
				continue;
			tot += item[0].getStack();
			Banking.withdraw(amount - tot, ids[i]);
			General.sleep(500, 800);
		}
	}

	private void openBank(boolean world_hop) {
		Banking.openBank();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Banking.isBankScreenOpen();
			}
		}, 5000);
		if (!Banking.isBankScreenOpen()) {
			bank(world_hop);
			return;
		}
		General.sleep(250, 800);
	}

	private boolean checkAndRemoveGlory() {
		RSItem[] equipment = Equipment.find(SLOTS.AMULET);
		if (equipment.length > 0)
			if (equipment[0].getID() == 1704) {
				Equipment.remove(SLOTS.AMULET);
				return true;
			}
		return false;
	}

	private boolean needToWithdrawJewelery(Jewelery teleport) {
		switch (teleport) {
		case GLORY:
			return Equipment.find(teleport.getIDs()).length == 0
					&& Inventory.find(teleport.getIDs()).length == 0;
		case RING_OF_DUELING:
		case GAMES_NECKLACE:
			return Equipment.find(teleport.getIDs()).length == 0
					&& Inventory.find(teleport.getIDs()).length == 0;
		}
		return false;

	}

	public boolean shouldBank(CombatTask task) {
		if (retry_time > System.currentTimeMillis())
			return false;
		Food food = ((Food) Dispatcher.get().get(ValueType.FOOD).getValue());
		if (food.getId() == -1 && Inventory.isFull()
				&& Inventory.find(Potions.PRAYER.getPotionsIDs()).length == 0)
			return true;
		if (food.getId() == -1)
			return false;
		int food_length = Inventory.find(food.getId()).length;
		if (task.isUsingProtectionPrayer()
				&& Inventory.find(Potions.PRAYER.getPotionsIDs()).length == 0)
			return true;
		if (food == Food.BonesToPeaches
				&& Inventory.find("Bones to peaches").length == 0)
			return true;
		else if (food == Food.BonesToPeaches)
			return false;
		if ((Boolean) Dispatcher.get().get(ValueType.EAT_FOR_SPACE).getValue()
				&& food_length > 0)
			return false;
		return Inventory.isFull()
				|| (food.getId() != -1 && Inventory.find(food.getId()).length == 0);
	}

	public void addBankItem(int id, int amount) {
		this.addBankItem(new BankItem(id, amount));
	}

	public void addBankItem(BankItem item) {
		this.list.add(item);
	}

	public int[] getItemIds() {
		List<Integer> items = new ArrayList<Integer>();
		for (BankItem x : this.list)
			items.add(x.getId());
		return ArrayUtil.toArrayInt(items);
	}

	public int[] getItemAmounts() {
		List<Integer> items = new ArrayList<Integer>();
		for (BankItem x : this.list)
			items.add(x.getAmount());
		return ArrayUtil.toArrayInt(items);
	}

	public Value<Integer> getFoodWithdrawAmount() {
		return new Value<Integer>(this.food_amount);
	}

	public void setFoodWithdrawAmount(int amount) {
		this.food_amount = amount;
	}

	public Value<?> getLogOutWhenOutOfFood() {
		return new Value<Boolean>(this.log_when_out_of_food);
	}

	public void setLogOutWhenOutOFood(boolean value) {
		this.log_when_out_of_food = value;

	}

}
