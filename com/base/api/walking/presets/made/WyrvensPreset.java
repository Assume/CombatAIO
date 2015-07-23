package scripts.CombatAIO.com.base.api.walking.presets.made;

import scripts.CombatAIO.com.base.api.tasks.Dispatcher;
import scripts.CombatAIO.com.base.api.types.BankItem;
import scripts.CombatAIO.com.base.api.walking.presets.Preset;
import scripts.CombatAIO.com.base.api.walking.types.Teleport;

public class WyrvensPreset extends Preset {

	public static final String NAME = "Wyrvens House Teleport, mounted glory";

	public static final BankItem HOUSE_TELEPORT = new BankItem(8013, 1);

	public WyrvensPreset(BankItem... required_items) {
		super(null, required_items);
	}

	@Override
	public void executeToBank() {
		if (Teleport.HOUSE_TELEPORT.canTeleport())
			Teleport.HOUSE_TELEPORT.teleport();
		else {
			Dispatcher.get().stop("You do not have a house teleport");
			return;
		}

	}

	private void useMountedGlory() {

	}

	@Override
	public void executeToMonster() {
		// TODO Auto-generated method stub

	}

}
