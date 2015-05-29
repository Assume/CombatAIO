package scripts.CombatAIO.com.base.main.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
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
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.main.utils.FileUtil;

public class BaseGUI extends JFrame {

	private JPanel contentPane;

	private JComboBox<Food> combo_box_food;

	private JPanel tab_one_panel;
	private JPanel tab_two_panel;
	private JPanel tab_three_panel;
	private JPanel tab_four_panel;

	private JComboBox<Prayer> combo_box_prayer;
	private JLabel lblPrayer;
	private JTable table_loot;
	private JTable table_banking_items;
	private JLabel lblBankItems;
	private JScrollPane scrollPane_1;
	private JCheckBox chckbx_flicker;
	private JCheckBox chckbx_guthans;
	private JCheckBox chckbx_ranged;
	private JLabel lblOnlySome;
	private JCheckBox chckbox_wait_for_loot;
	private JCheckBox chckbox_loot_in_combat;
	private JButton button;
	private JButton button_add_to_possible;
	private JButton button_remove_from_possible;
	private JLabel lblPossible;
	private JLabel lblSelected;
	private JButton btnNewButton;

	private JList<String> list_possible_monsters;
	private JList<String> list_selected_monsters;

	private DefaultListModel<String> model_possible_monsters;
	private DefaultListModel<String> model_selected_monsters;

	private JComboBox<Weapon> combo_box_special_attack;
	private JComboBox<String> combo_box_settings;
	private JLabel lblSpecialAttack;

	public BaseGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 485, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		tab_one_panel = new JPanel();
		tab_two_panel = new JPanel();
		tab_three_panel = new JPanel();
		tab_four_panel = new JPanel();

		model_possible_monsters = new DefaultListModel<String>();
		model_selected_monsters = new DefaultListModel<String>();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("General", tab_one_panel);
		tab_one_panel.setLayout(null);
		tabbedPane.addTab("Items & Banking", tab_two_panel);
		tab_two_panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 182, 227);
		tab_two_panel.add(scrollPane);

		table_loot = new JTable();
		scrollPane.setViewportView(table_loot);
		table_loot.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table_loot.setModel(new DefaultTableModel(new Object[][] { { null },
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

		JLabel lblNewLabel = new JLabel("Loot items");
		lblNewLabel.setBounds(10, 11, 70, 14);
		tab_two_panel.add(lblNewLabel);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(227, 31, 217, 227);
		tab_two_panel.add(scrollPane_1);

		table_banking_items = new JTable();
		scrollPane_1.setViewportView(table_banking_items);
		table_banking_items.putClientProperty("terminateEditOnFocusLost",
				Boolean.TRUE);
		table_banking_items.setModel(new DefaultTableModel(new Object[][] {
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null } },

		new String[] { "Item ID", "Amount" }));

		lblBankItems = new JLabel("Bank items");
		lblBankItems.setBounds(227, 11, 70, 14);
		tab_two_panel.add(lblBankItems);
		tabbedPane.addTab("Combat", tab_three_panel);
		tab_three_panel.setLayout(null);
		tabbedPane.addTab("Advanced", tab_four_panel);
		tab_four_panel.setLayout(null);

		button = new JButton("Progression");
		button.setBounds(10, 22, 89, 23);
		tab_four_panel.add(button);

		combo_box_prayer = new JComboBox<Prayer>(Prayer.values());
		combo_box_prayer.setBounds(10, 31, 121, 20);
		tab_three_panel.add(combo_box_prayer);

		lblPrayer = new JLabel("Prayer");
		lblPrayer.setBounds(10, 11, 46, 14);
		tab_three_panel.add(lblPrayer);

		chckbx_flicker = new JCheckBox("Flicker *");
		chckbx_flicker.setBounds(10, 58, 97, 23);
		tab_three_panel.add(chckbx_flicker);

		chckbx_guthans = new JCheckBox("Guthans");
		chckbx_guthans.setBounds(10, 84, 97, 23);
		tab_three_panel.add(chckbx_guthans);

		lblOnlySome = new JLabel("* Only some prayers supported");
		lblOnlySome.setBounds(10, 198, 169, 14);
		tab_three_panel.add(lblOnlySome);

		chckbox_wait_for_loot = new JCheckBox("Wait for loot");
		chckbox_wait_for_loot.setBounds(20, 110, 111, 23);
		tab_three_panel.add(chckbox_wait_for_loot);

		chckbox_loot_in_combat = new JCheckBox("Loot in combat");
		chckbox_loot_in_combat.setBounds(20, 136, 111, 23);
		tab_three_panel.add(chckbox_loot_in_combat);

		chckbx_ranged = new JCheckBox("Ranged");
		chckbx_ranged.setBounds(131, 58, 97, 23);
		tab_three_panel.add(chckbx_ranged);

		combo_box_special_attack = new JComboBox<Weapon>(Weapon.values());
		combo_box_special_attack.setBounds(151, 31, 121, 20);
		tab_three_panel.add(combo_box_special_attack);

		lblSpecialAttack = new JLabel("Special Attack");
		lblSpecialAttack.setBounds(151, 11, 77, 14);
		tab_three_panel.add(lblSpecialAttack);

		combo_box_food = new JComboBox<Food>(Food.values());
		combo_box_food.setBounds(10, 31, 121, 20);
		tab_one_panel.add(combo_box_food);

		JLabel lblFood = new JLabel("Food");
		lblFood.setBounds(10, 11, 46, 14);
		tab_one_panel.add(lblFood);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				set();
				setVisible(false);
				dispose();
			}
		});
		btnStart.setBounds(353, 235, 89, 23);
		tab_one_panel.add(btnStart);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Saving is currently disabled");
				if (true)
					return;
				String name = JOptionPane
						.showInputDialog("Enter name for this profile");
				if (name == null) {
					JOptionPane.showMessageDialog(null,
							"That is an invalid name, save failed");
					return;
				}
				set();
				Dispatcher.get().save(name);
			}
		});
		btnSave.setBounds(141, 235, 89, 23);
		tab_one_panel.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnLoad.setBounds(42, 235, 89, 23);
		tab_one_panel.add(btnLoad);

		JLabel lblWithdrawAmount = new JLabel("Withdraw amount");
		lblWithdrawAmount.setBounds(10, 65, 89, 14);
		tab_one_panel.add(lblWithdrawAmount);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(102, 62, 29, 20);
		tab_one_panel.add(spinner);

		combo_box_settings = new JComboBox<String>();
		combo_box_settings.setBounds(10, 204, 121, 20);
		tab_one_panel.add(combo_box_settings);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(141, 31, 121, 148);
		tab_one_panel.add(scrollPane_2);

		list_possible_monsters = new JList<String>();
		scrollPane_2.setViewportView(list_possible_monsters);
		list_possible_monsters.setModel(model_possible_monsters);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(321, 32, 121, 145);
		tab_one_panel.add(scrollPane_3);

		list_selected_monsters = new JList<String>();
		scrollPane_3.setViewportView(list_selected_monsters);
		list_selected_monsters.setModel(model_selected_monsters);

		button_add_to_possible = new JButton(">");
		button_add_to_possible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selected = list_possible_monsters.getSelectedValue();
				if (selected == null)
					return;
				if (model_selected_monsters.contains(selected))
					return;
				model_selected_monsters.addElement(selected);
			}
		});
		button_add_to_possible.setBounds(265, 75, 54, 20);
		tab_one_panel.add(button_add_to_possible);

		button_remove_from_possible = new JButton("<");
		button_remove_from_possible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = list_selected_monsters.getSelectedIndex();
				if (selected == -1)
					return;
				model_selected_monsters.remove(selected);
			}
		});
		button_remove_from_possible.setBounds(265, 106, 54, 20);
		tab_one_panel.add(button_remove_from_possible);

		lblPossible = new JLabel("Possible");
		lblPossible.setBounds(142, 11, 46, 14);
		tab_one_panel.add(lblPossible);

		lblSelected = new JLabel("Selected");
		lblSelected.setBounds(321, 11, 46, 14);
		tab_one_panel.add(lblSelected);

		btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (RSNPC n : NPCs.getAll())
					if (n.getCombatLevel() > 0
							&& !model_possible_monsters.contains(n.getName())
							&& n.getName() != null
							&& !n.getName().equals("null"))
						model_possible_monsters.addElement(n.getName());
			}
		});
		btnNewButton.setBounds(249, 186, 89, 23);
		tab_one_panel.add(btnNewButton);

		fillSettingsNames();

	}

	private void fillSettingsNames() {
		File[] files = FileUtil.getFilesInDirectory("bset", FileUtil.getDir()
				.getAbsolutePath());
		if (files == null)
			return;
		for (File f : files)
			combo_box_settings.addItem(f.getName());
	}

	private void set() {
		Dispatcher.get().set(ValueType.FOOD,
				new Value<Food>((Food) combo_box_food.getSelectedItem()));
		Dispatcher.get().set(ValueType.WAIT_FOR_LOOT,
				new Value<Boolean>(chckbox_wait_for_loot.isSelected()));
		Dispatcher.get().set(ValueType.LOOT_IN_COMBAT,
				new Value<Boolean>(chckbox_loot_in_combat.isSelected()));
		Dispatcher.get().set(ValueType.MONSTER_NAMES, getMonsterNames());
		Dispatcher.get().set(
				ValueType.SPECIAL_ATTACK_WEAPON,
				new Value<Weapon>((Weapon) combo_box_special_attack
						.getSelectedItem()));
		Dispatcher.get().set(ValueType.USE_GUTHANS,
				new Value<Boolean>(chckbx_guthans.isSelected()));
		Dispatcher.get().set(ValueType.IS_RANGING,
				new Value<Boolean>(chckbx_ranged.isSelected()));
		Dispatcher.get().set(ValueType.FLICKER_PRAYER,
				new Value<Prayer>((Prayer) combo_box_prayer.getSelectedItem()));
		setBankingList();
		setLootingList();
	}

	private void setLootingList() {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < 60 && table_loot.getValueAt(i, 0) != null; i++)
			if (table_loot.getValueAt(i, 0) != null)
				temp.add(table_loot.getValueAt(i, 0).toString());
		Dispatcher.get().set(ValueType.LOOT_ITEM_NAMES,
				new Value<String[]>(temp.toArray(new String[temp.size()])));
	}

	private void setBankingList() {
		Banker b = (Banker) Dispatcher.get().get(ValueType.BANKER).getValue();
		for (int i = 0; i < 28; i++) {
			if (table_banking_items.getValueAt(i, 0) != null
					&& table_banking_items.getValueAt(i, 1) != null)
				b.addBankItem(Integer.parseInt(table_banking_items.getValueAt(
						i, 0).toString()), Integer.parseInt(table_banking_items
						.getValueAt(i, 1).toString()));
		}
	}

	private void load(String name) {
		try {
			FileInputStream in = new FileInputStream(FileUtil.getFile(true,
					"name", "bset", FileUtil.getDir().getAbsolutePath()));
			Properties prop = new Properties();
			prop.load(in);
			combo_box_food.setSelectedItem(Food.getFoodFromName(prop
					.getProperty("food")));
			chckbx_ranged.setSelected(Boolean.parseBoolean(prop
					.getProperty("ranging")));
			combo_box_prayer.setSelectedItem(Prayer.parse(prop
					.getProperty("prayer")));
			chckbox_loot_in_combat.setSelected(Boolean.parseBoolean(prop
					.getProperty("loot_in_combat")));
			chckbox_wait_for_loot.setSelected(Boolean.parseBoolean(prop
					.getProperty("wait_for_loot")));
			fillSelectedMonster(prop.getProperty("monster_names"));
			fillLootTable(prop.getProperty("loot_items"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fillSelectedMonster(String property) {
		List<String> list = parseStringIntoList(property);
		for (String x : list)
			model_selected_monsters.addElement(x);

	}

	private void fillBankTable(String property) {
		// TODO Auto-generated method stub

	}

	private List<String> parseStringIntoList(String s) {
		List<String> intList = new ArrayList<String>();
		for (String a : Arrays.asList(s.substring(1, s.length() - 1)
				.split(", ")))
			if (!a.equals(""))
				intList.add(a);

		return intList;
	}

	private void fillLootTable(String text) {
		List<String> list = parseStringIntoList(text);
		for (int i = 0; i < list.size(); i++)
			table_loot.setValueAt(list.get(i), 0, i);
	}

	private static Map<Integer, String> convertStringToHashMap(String text) {
		Map<Integer, String> data = new HashMap<Integer, String>();
		Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
		String[] split = p.split(text);
		for (int i = 1; i + 2 <= split.length; i += 2) {
			data.put(Integer.parseInt(split[i]), split[i + 1]);
		}
		return data;
	}

	private Value<String[]> getMonsterNames() {
		int size = model_selected_monsters.getSize();
		if (size == 0)
			return new Value<String[]>(new String[0]);
		List<String> temp_list = new ArrayList<String>();
		for (int i = 0; i < size; i++)
			temp_list.add(model_selected_monsters.get(i));
		return new Value<String[]>(temp_list.toArray(new String[temp_list
				.size()]));
	}
}
