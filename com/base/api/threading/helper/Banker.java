package scripts.CombatAIO.com.base.api.threading.helper;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSItem;

import scripts.CombatAIO.com.base.api.general.walking.CWalking;
import scripts.CombatAIO.com.base.api.general.walking.types.Jewelery;
import scripts.CombatAIO.com.base.api.general.walking.types.JeweleryTeleport;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.types.enums.Potions;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.main.GenericMethods;

public class Banker {

	private List<BankItem> list;
	private int food_amount;

	public Banker() {
		list = new ArrayList<BankItem>();
		this.food_amount = 0;
	}

	/*
	 * @Override public void run() { while (Dispatcher.get().isRunning()) { if
	 * (this.shouldBank()) {
	 * Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
	 * 
	 * String[] monster_names = (String[]) Dispatcher.get()
	 * .get(ValueType.MONSTER_NAMES).getValue(); CustomPaths modified_path =
	 * getModifiedPath(monster_names); if (modified_path != null) ;
	 * modified_path.getWebWalkingDeactivationArea (MovementType.TO_BANK);
	 * 
	 * TODO make it so it grabs the first modified area to pass to webwalking
	 * bank(); Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING); }
	 * General.sleep(2000); } }
	 */

	public void bank(boolean world_hop) {
		Camera.setCameraRotation(General.random(Camera.getCameraAngle() - 15,
				Camera.getCameraAngle() + 15));
		JeweleryTeleport teleport = CWalking.walk(MovementType.TO_BANK);
		if (teleport != null && teleport.getJewelery() == Jewelery.GLORY)
			checkAndRemoveGlory();
		openBank(world_hop);
		handleBankWindow(world_hop, teleport);
		if (world_hop)
			WorldHopper.changeWorld(WorldHopper.getRandomWorld(true));
		CWalking.walk(MovementType.TO_MONSTER);
	}

	// TODO DEPOSIT ALL EXCEPT WHAT?
	private void handleBankWindow(boolean world_hop, JeweleryTeleport teleport) {
		int weapon = ((Weapon) (Dispatcher.get().get(
				ValueType.SPECIAL_ATTACK_WEAPON).getValue())).getID();
		if (Inventory.getAll().length > 0)
			Banking.depositAllExcept(GenericMethods.combineArrays(
					new int[] { weapon },
					(int[]) Dispatcher.get().get(ValueType.GUTHANS_IDS)
							.getValue()));
		boolean withdraw_jewelery = withdraw(teleport == null ? null : teleport
				.getJewelery());
		Banking.close();
		if (withdraw_jewelery)
			scripts.CombatAIO.com.base.api.general.walking.custom.background.CEquipment
					.equip(teleport.getJewelery().getIDs());
	}

	private boolean withdraw(Jewelery jewelery) {
		Banking.withdraw(this.food_amount,
				((Food) Dispatcher.get().get(ValueType.FOOD).getValue())
						.getId());
		for (BankItem x : list) {
			if (Potions.isPotionId(x.getId()))
				withdrawYofX(x.getAmount(), Potions.getAllIds(x.getId()));
			Banking.withdraw(x.getAmount(), x.getId());
		}
		if (jewelery != null && needToWithdrawJewelery(jewelery)) {
			withdrawYofX(1, jewelery.getIDs());
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
			Banking.withdraw(amount - tot, ids[i]);
			tot += item[0].getStack();
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
			return Equipment.find(teleport.getIDs()).length == 0;
		case RING_OF_DUELING:
		case GAMES_NECKLACE:
			return Equipment.find(teleport.getIDs()).length == 0
					&& Inventory.find(teleport.getIDs()).length == 0;
		}
		return false;

	}

	public static boolean shouldBank() {
		int id = ((Food) Dispatcher.get().get(ValueType.FOOD).getValue())
				.getId();
		int food_length = Inventory.find(id).length;
		if ((Boolean) Dispatcher.get().get(ValueType.EAT_FOR_SPACE).getValue()
				&& food_length > 0)
			return false;
		return Inventory.isFull()
				|| (id != -1 && Inventory.find(id).length == 0);
	}

	public void addBankItem(int id, int amount) {
		this.list.add(new BankItem(id, amount));
	}

}
