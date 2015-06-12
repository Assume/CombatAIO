package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;
import scripts.CombatAIO.com.base.api.magic.books.NormalSpell;

public class DTeleport implements DAction {

	private static final long serialVersionUID = -4313472754324162406L;

	private NormalSpell spell;

	public DTeleport(NormalSpell spell) {
		this.spell = spell;
	}

	@Override
	public void execute() {
		spell.select();
	}

	@Override
	public String toString() {
		return "teleport with " + spell;
	}

}
