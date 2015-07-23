package scripts.CombatAIO.com.base.api.types.constants;

import java.io.File;

import org.tribot.util.Util;

import scripts.CombatAIO.com.base.api.tasks.Dispatcher;

public class FileSaveLocations {

	private static final String COMBAT_AIO = Util.getWorkingDirectory()
			+ File.separator + "Base";

	private static final String ASSUMES_GOT_CRABS = Util.getWorkingDirectory()
			+ File.separator + "AssumesGotCrabs";

	public static final String getFileLocation() {
		switch (Dispatcher.get().getRepoID()) {
		case ScriptIDs.COMBAT_AIO_LITE:
		case ScriptIDs.COMBAT_AIO_PREMIUM:
			return COMBAT_AIO;
		case ScriptIDs.ASSUMES_GOT_CRABS:
			return ASSUMES_GOT_CRABS;
		}
		return null;
	}

}
