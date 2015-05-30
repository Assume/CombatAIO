package scripts.CombatAIO.com.base.api.threading.threads;

import java.util.List;

import org.tribot.api2007.Combat;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSPlayer;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.PauseType;
import scripts.CombatAIO.com.base.api.threading.types.Threadable;

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
			if (this.isBeingAttackedByPlayer()) {
				System.out.println("PK_AVOIDER_THREAD IS CALLING PAUSE");
				Dispatcher.get().pause(PauseType.NON_ESSENTIAL_TO_BANKING);
				Dispatcher.get().bank(this.hop_on_attack);
				System.out.println("PK_AVOIDER_THREAD IS CALLING UNPAUSE");
				Dispatcher.get().unpause(PauseType.NON_ESSENTIAL_TO_BANKING);
			}
		}
	}

	private boolean isBeingAttackedByPlayer() {
		for (RSCharacter p : Combat.getAttackingEntities())
			if (p instanceof RSPlayer)
				return true;
		return false;
	}

}
