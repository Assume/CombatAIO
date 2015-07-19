package scripts.CombatAIO.com.base.api.tasks.threads;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSPlayer;

import scripts.CombatAIO.com.base.api.tasks.Dispatcher;
import scripts.CombatAIO.com.base.api.tasks.types.PauseType;
import scripts.CombatAIO.com.base.api.tasks.types.Threadable;
import scripts.CombatAIO.com.base.main.utils.Logger;

public class PKAvoider extends Threadable {

	private boolean hop_on_attack;

	public PKAvoider(boolean hop_on_attack) {
		this(null);
		this.hop_on_attack = hop_on_attack;
		super.setName("PK_AVOIDER_THREAD");
	}

	private PKAvoider(List<PauseType> pause_types) {
		super(pause_types);
	}

	@Override
	public void run() {
		while (Dispatcher.get().isRunning()) {
			if (Combat.getWildernessLevel() == 0) {
				General.sleep(100);
				continue;
			}

			if (this.isBeingAttackedByPlayer()) {
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"PK_AVOIDER_THREAD IS CALLING PAUSE");
				Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
				Dispatcher.get().getBanker().bank(this.hop_on_attack);
				Logger.getLogger().print(Logger.SCRIPTER_ONLY,
						"PK_AVOIDER_THREAD IS CALLING UNPAUSE");
				Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING);
			}
			General.sleep(100);
		}
	}

	// TODO
	private boolean isThreat() {
		int cb_level = Player.getRSPlayer().getCombatLevel();
		int wildy_level = Combat.getWildernessLevel();
		final int maximum_combat = cb_level + wildy_level;
		final int minimum_combat = cb_level - wildy_level;

		RSPlayer[] players = Players.getAll(new Filter<RSPlayer>() {

			@Override
			public boolean accept(RSPlayer arg0) {
				int temp_cb = arg0.getCombatLevel();
				return temp_cb <= maximum_combat && temp_cb >= minimum_combat;
			}
		});
		return false;
	}

	private boolean isBeingAttackedByPlayer() {
		for (RSCharacter p : Combat.getAttackingEntities())
			if (p instanceof RSPlayer)
				return true;
		return false;
	}

}
