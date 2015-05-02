package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import scripts.priv.drennon.background.DAction;
import scripts.priv.drennon.background.MonitorMain;
import scripts.priv.drennon.background.magic.books.NormalSpell;

public class DTeleport implements DAction {

	private static final long serialVersionUID = -4313472754324162406L;

	private NormalSpell spell;

	public DTeleport(NormalSpell spell) {
		this.spell = spell;
	}

	@Override
	public void execute() {
		MonitorMain.pause();
		spell.select();
		MonitorMain.unpause();
	}

	@Override
	public String toString() {
		return "teleport with " + spell;
	}

}
