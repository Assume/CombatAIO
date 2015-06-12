package scripts.CombatAIO.com.base.main.gui.elements;

import javax.swing.table.DefaultTableModel;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;

public class UneditableDefaultTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 230060999968548196L;

	public UneditableDefaultTableModel(Object[][] objects, String[] strings) {
		super(objects, strings);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 1 && Dispatcher.get().isLiteMode())
			return false;
		return true;
	}



}
