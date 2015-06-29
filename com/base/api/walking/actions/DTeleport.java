package scripts.CombatAIO.com.base.api.walking.actions;

import scripts.CombatAIO.com.base.api.magic.books.NormalSpell;
import scripts.CombatAIO.com.base.api.walking.types.DAction;

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
