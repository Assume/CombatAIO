package scripts.CombatAIO.com.base.api.types.enums;

import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Players;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.tasks.helper.CombatHelper;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.main.Dispatcher;

public enum WorldHoppingCondition {

	NONE(0) {
		@Override
		public boolean shouldHop() {
			return false;
		}
	},
	PLAYERS_PRESENT(0) {
		@Override
		public boolean shouldHop() {
			return Players.find(new Filter<RSPlayer>() {
				@Override
				public boolean accept(RSPlayer arg0) {
					return arg0.getPosition().distanceTo(
							(RSTile) Dispatcher.get().get(ValueType.HOME_TILE)
									.getValue()) <= (Integer) Dispatcher.get()
							.get(ValueType.COMBAT_RADIUS).getValue();
				}
			}).length > value;
		}
	},
	PLAYER_TALKING(0) {
		@Override
		public boolean shouldHop() {
			if (Dispatcher.get().getPlayerMessageReceived()) {
				Dispatcher.get().setPlayerMessageReceived(false);
				return true;
			}
			return false;
		}
	},
	CANNON_PRESENT(0) {
		@Override
		public boolean shouldHop() {
			int cannon_length = Objects.find(50, CombatHelper.CANNON_IDS).length;
			return (Boolean) Dispatcher.get().get(ValueType.USE_CANNON)
					.getValue() ? cannon_length > 1 : cannon_length > 0;
		}
	};

	protected int value;

	WorldHoppingCondition(int value) {
		this.value = value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public abstract boolean shouldHop();

	public int getValue() {
		return this.value;
	}

}
