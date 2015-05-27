package scripts.CombatAIO.com.base.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.helper.Banker;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;

public class TemporaryGUI extends JFrame {

	private JPanel contentPane;

	private JList<String> monster_list;
	private JComboBox<Food> combo_box_food;
	private JCheckBox check_box_loot_in_combat;
	private JCheckBox check_box_wait_for_loot;
	private JCheckBox check_box_guthans;
	private JTable loot_table;
	private JComboBox<Weapon> special_attack_combo_box;
	private JTable bank_table;

	public TemporaryGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 739);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		combo_box_food = new JComboBox<Food>(Food.values());
		combo_box_food.setBounds(131, 11, 142, 26);
		contentPane.add(combo_box_food);

		JLabel lblFood = new JLabel("Food");
		lblFood.setBounds(10, 17, 46, 14);
		contentPane.add(lblFood);

		JLabel lblSpecialAttackWeapon = new JLabel("Special Attack Weapon");
		lblSpecialAttackWeapon.setBounds(10, 50, 122, 14);
		contentPane.add(lblSpecialAttackWeapon);

		special_attack_combo_box = new JComboBox<Weapon>(Weapon.values());
		special_attack_combo_box.setBounds(131, 48, 142, 26);
		contentPane.add(special_attack_combo_box);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 424, 202, 265);
		contentPane.add(scrollPane);
		final DefaultListModel<String> monster_list_model = new DefaultListModel<String>();
		monster_list = new JList<String>();
		monster_list.setModel(monster_list_model);
		scrollPane.setViewportView(monster_list);

		check_box_loot_in_combat = new JCheckBox("Loot in Combat");
		check_box_loot_in_combat.setBounds(10, 88, 113, 23);
		contentPane.add(check_box_loot_in_combat);

		check_box_wait_for_loot = new JCheckBox("Wait for loot");
		check_box_wait_for_loot.setBounds(10, 114, 113, 23);
		contentPane.add(check_box_wait_for_loot);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				set();
				setVisible(false);
				dispose();
			}

		});
		btnStart.setBounds(321, 666, 89, 23);
		contentPane.add(btnStart);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				monster_list_model.clear();
				for (RSNPC n : NPCs.getAll())
					if (n.getCombatLevel() > 0
							&& !monster_list_model.contains(n.getName())
							&& n.getName() != null
							&& !n.getName().equals("null"))
						monster_list_model.addElement(n.getName());
			}
		});
		btnRefresh.setBounds(222, 666, 89, 23);
		contentPane.add(btnRefresh);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 172, 202, 242);
		contentPane.add(scrollPane_1);

		loot_table = new JTable();
		scrollPane_1.setViewportView(loot_table);

		JLabel lblLootIds = new JLabel("Loot ids");
		lblLootIds.setBounds(10, 151, 46, 14);
		contentPane.add(lblLootIds);

		loot_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		loot_table.setModel(new DefaultTableModel(new Object[][] { { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, }, new String[] { "Item name" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		JLabel lblBankItems = new JLabel("Bank items");
		lblBankItems.setBounds(222, 151, 64, 14);
		contentPane.add(lblBankItems);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(222, 173, 216, 241);
		contentPane.add(scrollPane_2);

		bank_table = new JTable();

		bank_table.setModel(new DefaultTableModel(new Object[][] {
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null } },

		new String[] { "Item ID", "Amount" }));
		bank_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		scrollPane_2.setViewportView(bank_table);

		check_box_guthans = new JCheckBox("Guthans");
		check_box_guthans.setBounds(131, 88, 97, 23);
		contentPane.add(check_box_guthans);
	}

	public void set() {
		Dispatcher.get().set(ValueType.FOOD,
				new Value<Food>((Food) combo_box_food.getSelectedItem()));
		Dispatcher.get().set(ValueType.WAIT_FOR_LOOT,
				new Value<Boolean>(check_box_wait_for_loot.isSelected()));
		Dispatcher.get().set(ValueType.LOOT_IN_COMBAT,
				new Value<Boolean>(check_box_loot_in_combat.isSelected()));
		Dispatcher.get().set(ValueType.MONSTER_NAMES, getMonsterNames());
		Dispatcher.get().set(
				ValueType.SPECIAL_ATTACK_WEAPON,
				new Value<Weapon>((Weapon) special_attack_combo_box
						.getSelectedItem()));
		Dispatcher.get().set(ValueType.USE_GUTHANS,
				new Value<Boolean>(check_box_guthans.isSelected()));
		setBankingList();
		setLootingList();
	}

	private void setLootingList() {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < 60 && loot_table.getValueAt(i, 0) != null; i++)
			if (loot_table.getValueAt(i, 0) != null)
				temp.add(loot_table.getValueAt(i, 0).toString());
		Dispatcher.get().set(ValueType.LOOT_ITEM_NAMES,
				new Value<String[]>(temp.toArray(new String[temp.size()])));
	}

	private void setBankingList() {
		Banker b = (Banker) Dispatcher.get().get(ValueType.BANKER).getValue();
		for (int i = 0; i < 28; i++) {
			if (bank_table.getValueAt(i, 0) != null
					&& bank_table.getValueAt(i, 1) != null)
				b.addBankItem(Integer.parseInt(bank_table.getValueAt(i, 0)
						.toString()), Integer.parseInt(bank_table.getValueAt(i,
						1).toString()));
		}
	}

	private Value<String[]> getMonsterNames() {
		List<String> temp = monster_list.getSelectedValuesList();
		return new Value<String[]>(temp.toArray(new String[temp.size()]));
	}
}
