package scripts.CombatAIO.com.base.api.general.walking.custom.background.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import scripts.priv.drennon.background.DAction;

public class DWriteToNotepad implements DAction {

	private static final long serialVersionUID = 6426441294959966561L;

	private String path;
	private String what;

	public DWriteToNotepad(String path, String what) {
		this.path = path;
		this.what = what;
	}

	@Override
	public void execute() {
		File f = new File(path);
		try {
			FileWriter wr = new FileWriter(f);
			wr.write(what + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
