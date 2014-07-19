package scripts.CombatAIO.com.base.api.paint.handler;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

final class MonsterPaintHandler {

	private String[] monster_names;

	public MonsterPaintHandler(String[] monster_names) {
		this.monster_names = monster_names;
	}

	public void update() {
		RSNPC[] possible_npcs = NPCs.find(monster_names);
	}

	private void draw(RSNPC... npcs) {

	}

}
