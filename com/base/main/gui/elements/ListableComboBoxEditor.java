package scripts.CombatAIO.com.base.main.gui.elements;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import scripts.starfox.interfaces.ui.Listable;

/**
 *
 * @author Spencer
 */
public class ListableComboBoxEditor extends BasicComboBoxEditor {

    /**
     * Sets the item to the specified value.
     *
     * @param o The Object that this editor is being set to.
     */
    @Override
    public void setItem(Object o) {
        if (o == null || checkObject(o)) {
            return;
        }
        if (o instanceof Listable) {
            super.setItem(((Listable) o).getPulldownDisplay());
        } else {
            super.setItem(o);
        }
    }

    /**
     * Checks whether the specified Object is valid or not.
     *
     * @param o The Object that has been selected by the ComboBox.
     * @return True if the specified Object ISN'T valid, false otherwise.
     */
    public boolean checkObject(Object o) {
        return false;
    }

    /**
     * Sets the specified JComboBox's editor to a new ListableComboBoxEditor.
     *
     * @param comboBox The JComboBox.
     */
    public static void setEditor(JComboBox comboBox) {
        comboBox.setEditor(new ListableComboBoxEditor());
    }
}
