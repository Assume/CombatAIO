package scripts.CombatAIO.com.base.main.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.util.Util;

import scripts.CombatAIO.com.base.api.general.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.bgui.BMainGui;
import scripts.CombatAIO.com.base.api.general.walking.types.CustomMovement;
import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.helper.Banker;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.main.utils.ArrayUtil;

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
	private JCheckBox chckbx_wait_for_loot;
	private JCheckBox chckbx_loot_in_combat;
	private JLabel lblOnlySome;
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
	private JTextField text_field_loot_over_x;

	private JSpinner spinner_food;
	private JSpinner spinner_combat_radius;
	private JSpinner spinner_world_hop_tolerance;
	private RSTile safe_spot_tile;

	private DefaultComboBoxModel<String> model_combo_box = new DefaultComboBoxModel<String>();

	public BaseGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 645, 367);
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

		JLabel lblLootOverX = new JLabel("Loot over X");
		lblLootOverX.setBounds(10, 268, 70, 14);
		tab_two_panel.add(lblLootOverX);

		text_field_loot_over_x = new JTextField();
		text_field_loot_over_x.setBounds(87, 265, 105, 20);
		tab_two_panel.add(text_field_loot_over_x);
		text_field_loot_over_x.setColumns(10);

		chckbx_wait_for_loot = new JCheckBox("Wait for loot");
		chckbx_wait_for_loot.setBounds(213, 264, 111, 23);
		tab_two_panel.add(chckbx_wait_for_loot);

		chckbx_loot_in_combat = new JCheckBox("Loot in combat");
		chckbx_loot_in_combat.setBounds(333, 264, 111, 23);
		tab_two_panel.add(chckbx_loot_in_combat);
		tabbedPane.addTab("Combat", tab_three_panel);
		tab_three_panel.setLayout(null);
		tabbedPane.addTab("Advanced", tab_four_panel);
		tab_four_panel.setLayout(null);

		button = new JButton("Progression");
		button.setBounds(10, 22, 122, 23);
		tab_four_panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"This option is not currently available");
			}
		});

		JButton btnNewButton_1 = new JButton("Custom Walking");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BMainGui bmaingui = new BMainGui();
				bmaingui.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 56, 122, 23);
		tab_four_panel.add(btnNewButton_1);

		// TODO
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

		lblOnlySome = new JLabel(
				"* Only piety and chivalry are supported for flicker");
		lblOnlySome.setBounds(10, 243, 262, 14);
		tab_three_panel.add(lblOnlySome);

		chckbx_ranged = new JCheckBox("Ranged/Magic");
		chckbx_ranged.setBounds(131, 58, 119, 23);
		tab_three_panel.add(chckbx_ranged);

		// TODO
		combo_box_special_attack = new JComboBox<Weapon>(Weapon.values());
		combo_box_special_attack.setBounds(151, 31, 121, 20);
		tab_three_panel.add(combo_box_special_attack);

		lblSpecialAttack = new JLabel("Special Attack");
		lblSpecialAttack.setBounds(151, 11, 77, 14);
		tab_three_panel.add(lblSpecialAttack);

		JLabel lblWorldHopTolerance = new JLabel("World hop tolerance**");
		lblWorldHopTolerance.setBounds(10, 114, 121, 14);
		tab_three_panel.add(lblWorldHopTolerance);

		spinner_world_hop_tolerance = new JSpinner();
		spinner_world_hop_tolerance.setBounds(131, 111, 39, 20);
		tab_three_panel.add(spinner_world_hop_tolerance);
		spinner_world_hop_tolerance.setValue(-1);

		lblNewLabel_1 = new JLabel("** Leave at -1 for no hopping");
		lblNewLabel_1.setBounds(10, 268, 240, 14);
		tab_three_panel.add(lblNewLabel_1);

		lbl_safe_spot = new JLabel("Safe spot: ");
		lbl_safe_spot.setBounds(109, 162, 141, 14);
		tab_three_panel.add(lbl_safe_spot);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				safe_spot_tile = Player.getRSPlayer().getPosition();
				if (safe_spot_tile != null)
					lbl_safe_spot.setText("Safe spot: "
							+ safe_spot_tile.toString());
			}
		});
		btnSet.setBounds(10, 158, 89, 23);
		tab_three_panel.add(btnSet);

		// TODO
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
			}
		});
		btnStart.setBounds(354, 259, 89, 23);
		tab_one_panel.add(btnStart);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * JOptionPane.showMessageDialog(null,
				 * "Saving is currently disabled"); if (true) return;
				 */
				String name = JOptionPane
						.showInputDialog("Enter name for this profile");
				if (name == null) {
					JOptionPane.showMessageDialog(null,
							"That is an invalid name, save failed");
					return;
				}
				set();
				save(name);
				saveMovements(name);
			}
		});
		btnSave.setBounds(141, 259, 89, 23);
		tab_one_panel.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = combo_box_settings.getSelectedItem().toString();
				if (name == null)
					return;
				load(name);
				loadMovements(name);
			}
		});
		btnLoad.setBounds(42, 259, 89, 23);
		tab_one_panel.add(btnLoad);

		JLabel lblWithdrawAmount = new JLabel("Amount");
		lblWithdrawAmount.setBounds(10, 65, 89, 14);
		tab_one_panel.add(lblWithdrawAmount);

		spinner_food = new JSpinner();
		spinner_food.setBounds(85, 62, 46, 20);
		tab_one_panel.add(spinner_food);

		combo_box_settings = new JComboBox<String>();
		combo_box_settings.setBounds(10, 228, 121, 20);
		tab_one_panel.add(combo_box_settings);
		combo_box_settings.setModel(this.model_combo_box);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(141, 32, 195, 165);
		tab_one_panel.add(scrollPane_2);

		list_possible_monsters = new JList<String>();
		scrollPane_2.setViewportView(list_possible_monsters);
		list_possible_monsters.setModel(model_possible_monsters);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(410, 32, 195, 165);
		tab_one_panel.add(scrollPane_3);

		list_selected_monsters = new JList<String>();
		scrollPane_3.setViewportView(list_selected_monsters);
		list_selected_monsters.setModel(model_selected_monsters);

		button_add_to_possible = new JButton(">");
		button_add_to_possible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> selected = list_possible_monsters
						.getSelectedValuesList();
				for (String x : selected) {
					if (model_selected_monsters.contains(x))
						continue;
					model_selected_monsters.addElement(x);
				}
			}
		});
		button_add_to_possible.setBounds(346, 75, 54, 20);
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
		button_remove_from_possible.setBounds(346, 106, 54, 20);
		tab_one_panel.add(button_remove_from_possible);

		lblPossible = new JLabel("Possible");
		lblPossible.setBounds(142, 11, 46, 14);
		tab_one_panel.add(lblPossible);

		lblSelected = new JLabel("Selected");
		lblSelected.setBounds(410, 11, 46, 14);
		tab_one_panel.add(lblSelected);

		btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (RSNPC n : NPCs.getAll())
					if (n.getCombatLevel() > 0
							&& !model_possible_monsters.contains(n.getID()
									+ " -- " + n.getName() + " ("
									+ n.getCombatLevel() + ")")
							&& n.getName() != null
							&& !n.getName().equals("null"))
						model_possible_monsters.addElement(n.getID() + " -- "
								+ n.getName() + " (" + n.getCombatLevel() + ")");
			}
		});
		btnNewButton.setBounds(248, 207, 89, 23);
		tab_one_panel.add(btnNewButton);

		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setBounds(10, 90, 46, 14);
		tab_one_panel.add(lblRadius);

		spinner_combat_radius = new JSpinner();
		spinner_combat_radius.setBounds(85, 84, 46, 20);
		tab_one_panel.add(spinner_combat_radius);
		spinner_combat_radius.setValue(15);

		fillSettingsNames();

	}

	private void fillSettingsNames() {
		model_combo_box.removeAllElements();
		File[] files = new File(Util.getWorkingDirectory() + File.separator
				+ "Base").listFiles();
		if (files == null)
			return;
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".ini"))
				combo_box_settings.addItem(file.getName().replace(".ini", ""));
		}
	}

	private void set() {
		Dispatcher.get().set(ValueType.FOOD,
				new Value<Food>((Food) combo_box_food.getSelectedItem()));
		Dispatcher.get().set(ValueType.WAIT_FOR_LOOT,
				new Value<Boolean>(chckbx_wait_for_loot.isSelected()));
		Dispatcher.get().set(ValueType.LOOT_IN_COMBAT,
				new Value<Boolean>(chckbx_loot_in_combat.isSelected()));
		Dispatcher.get().set(ValueType.MONSTER_IDS, getMonsterIDs());
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
		Dispatcher.get().set(
				ValueType.FOOD_WITHDRAW_AMOUNT,
				new Value<Integer>(Integer.parseInt(spinner_food.getValue()
						.toString())));
		Dispatcher.get().set(ValueType.FLICKER,
				new Value<Boolean>(chckbx_flicker.isSelected()));
		Dispatcher.get().set(
				ValueType.COMBAT_RADIUS,
				new Value<Integer>(Integer.parseInt(spinner_combat_radius
						.getValue().toString())));
		String loot_over_x = text_field_loot_over_x.getText();
		Dispatcher.get().set(
				ValueType.WORLD_HOP_TOLERANCE,
				new Value<Integer>(Integer.parseInt(spinner_world_hop_tolerance
						.getValue().toString())));
		Dispatcher.get().set(ValueType.SAFE_SPOT_TILE,
				new Value<RSTile>(this.safe_spot_tile));
		if (loot_over_x != null && loot_over_x.length() != 0)
			Dispatcher.get().set(
					ValueType.MINIMUM_LOOT_VALUE,
					new Value<Integer>(Integer.parseInt(loot_over_x.replaceAll(
							"[^0-9]", ""))));
		setBankingList();
		setLootingList();
	}

	private void setLootingList() {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < 60 && table_loot.getValueAt(i, 0) != null; i++)
			if (table_loot.getValueAt(i, 0) != null)
				temp.add(table_loot.getValueAt(i, 0).toString().trim());
		Dispatcher.get().set(ValueType.LOOT_ITEM_NAMES,
				new Value<String[]>(temp.toArray(new String[temp.size()])));
	}

	private void setBankingList() {
		Banker b = (Banker) Dispatcher.get().get(ValueType.BANKER).getValue();
		for (int i = 0; i < 28; i++) {
			if (table_banking_items.getValueAt(i, 0) != null
					&& table_banking_items.getValueAt(i, 1) != null)
				b.addBankItem(
						Integer.parseInt(table_banking_items.getValueAt(i, 0)
								.toString().trim()),
						Integer.parseInt(table_banking_items.getValueAt(i, 1)
								.toString().trim()));
		}
	}

	public void save(String name) {
		try {
			Properties prop = new Properties();
			prop.setProperty("food", Dispatcher.get().get(ValueType.FOOD)
					.getValue().toString());
			prop.setProperty("ranging",
					Dispatcher.get().get(ValueType.IS_RANGING).getValue()
							.toString());
			prop.setProperty("prayer",
					Dispatcher.get().get(ValueType.FLICKER_PRAYER).getValue()
							.toString());
			prop.setProperty(
					"loot_items",
					stringArrayToString(((String[]) Dispatcher.get()
							.get(ValueType.LOOT_ITEM_NAMES).getValue())));
			prop.setProperty("loot_in_combat",
					Dispatcher.get().get(ValueType.LOOT_IN_COMBAT).getValue()
							.toString());
			prop.setProperty("wait_for_loot",
					Dispatcher.get().get(ValueType.WAIT_FOR_LOOT).getValue()
							.toString());
			prop.setProperty("special_attack_weapon",
					Dispatcher.get().get(ValueType.SPECIAL_ATTACK_WEAPON)
							.getValue().toString());
			prop.setProperty(
					"monster_ids",
					intArrayToString(((int[]) Dispatcher.get()
							.get(ValueType.MONSTER_IDS).getValue())));
			prop.setProperty("bank_item_ids",
					intArrayToString((int[]) Dispatcher.get().getBanker()
							.getItemIds()));
			prop.setProperty("bank_item_amounts",
					intArrayToString((int[]) Dispatcher.get().getBanker()
							.getItemAmounts()));
			prop.setProperty("minimum_loot_value",
					Dispatcher.get().get(ValueType.MINIMUM_LOOT_VALUE)
							.getValue().toString());
			prop.setProperty("food_withdraw_amount",
					Dispatcher.get().get(ValueType.FOOD_WITHDRAW_AMOUNT)
							.getValue().toString());
			prop.setProperty("use_flicker",
					Dispatcher.get().get(ValueType.FLICKER).getValue()
							.toString());
			prop.setProperty("use_guthans",
					Dispatcher.get().get(ValueType.USE_GUTHANS).getValue()
							.toString());
			prop.setProperty("combat_radius",
					Dispatcher.get().get(ValueType.COMBAT_RADIUS).getValue()
							.toString());
			prop.setProperty("world_hop_tolerance",
					Dispatcher.get().get(ValueType.WORLD_HOP_TOLERANCE)
							.getValue().toString());
			String safe_spot_tile_text = this.safe_spot_tile == null ? "null"
					: this.safe_spot_tile.toString().replaceAll("[^0-9,]", "");
			prop.setProperty("safe_spot_tile", safe_spot_tile_text);
			boolean exist = (new File(Util.getWorkingDirectory()
					+ File.separator + "Base").mkdirs());
			FileOutputStream streamO = new FileOutputStream(
					Util.getWorkingDirectory() + File.separator + "Base"
							+ File.separator + name + ".ini");
			prop.store(streamO, null);
			streamO.flush();
			streamO.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final String MOVEMENT_PATH = Util.getWorkingDirectory()
			+ File.separator + "Base" + File.separator + "movements";
	private JLabel lblNewLabel_1;
	private JLabel lbl_safe_spot;

	private void saveMovements(String name) {
		boolean exist = (new File(MOVEMENT_PATH).mkdirs());
		List<CustomMovement> movements = WalkingManager.getMovements();
		try {
			FileOutputStream fout = new FileOutputStream(MOVEMENT_PATH
					+ File.separator + name + ".cmov");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(movements);
			fout.flush();
			fout.close();
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadMovements(String name) {
		try {
			FileInputStream fis = new FileInputStream(MOVEMENT_PATH
					+ File.separator + name + ".cmov");
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<CustomMovement> cmovements = (List<CustomMovement>) ois
					.readObject();
			WalkingManager.setMovements(cmovements);
			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String intArrayToString(int[] ars) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < ars.length; i++) {
			if (i < ars.length - 1)
				b.append(ars[i] + ",");
			else
				b.append(ars[i]);
		}
		return b.toString();
	}

	private String stringArrayToString(String[] strings) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			if (i < strings.length - 1)
				b.append(strings[i] + ",");
			else
				b.append(strings[i]);
		}
		return b.toString();
	}

	private void load(String name) {
		try {
			FileInputStream in = new FileInputStream(Util.getWorkingDirectory()
					+ File.separator + "Base" + File.separator + name + ".ini");
			Properties prop = new Properties();
			prop.load(in);
			combo_box_food.setSelectedItem(Food.getFoodFromName(prop
					.getProperty("food")));
			chckbx_ranged.setSelected(Boolean.parseBoolean(prop
					.getProperty("ranging")));
			combo_box_prayer.setSelectedItem(Prayer.parse(prop
					.getProperty("prayer")));
			fillLootTable(prop.getProperty("loot_items"));
			chckbx_loot_in_combat.setSelected(Boolean.parseBoolean(prop
					.getProperty("loot_in_combat")));
			chckbx_wait_for_loot.setSelected(Boolean.parseBoolean(prop
					.getProperty("wait_for_loot")));
			combo_box_special_attack.setSelectedItem(Weapon
					.getWeaponFromName(prop
							.getProperty("special_attack_weapon")));
			String val = prop.getProperty("minimum_loot_value");
			if (!val.equalsIgnoreCase("2147483647"))
				text_field_loot_over_x.setText(prop
						.getProperty("minimum_loot_value"));
			spinner_food.setValue(Integer.parseInt(prop
					.getProperty("food_withdraw_amount")));
			fillSelectedMonster(prop.getProperty("monster_ids"));
			fillBankTable(prop);
			chckbx_flicker.setSelected(Boolean.parseBoolean(prop
					.getProperty("use_flicker")));
			chckbx_guthans.setSelected(Boolean.parseBoolean(prop
					.getProperty("use_guthans")));
			spinner_combat_radius.setValue(Integer.parseInt(prop
					.getProperty("combat_radius")));
			spinner_world_hop_tolerance.setValue(Integer.parseInt(prop
					.getProperty("world_hop_tolerance")));
			this.safe_spot_tile = getSafeSpotTile(prop);
			if (this.safe_spot_tile != null)
				lbl_safe_spot
						.setText("Safe spot: " + safe_spot_tile.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RSTile getSafeSpotTile(Properties prop) {
		String text = prop.getProperty("safe_spot_tile");
		if (text.equalsIgnoreCase("null"))
			return null;
		String[] text_ar = text.split(",");
		if (text_ar.length > 2)
			return new RSTile(getInt(text_ar[0]), getInt(text_ar[1]),
					getInt(text_ar[2]));
		return null;
	}

	private static int getInt(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9-]", ""));
	}

	private void fillSelectedMonster(String property) {
		int[] list = parseStringIntoIntegerArray(property);
		for (int x : list)
			model_selected_monsters.addElement("" + x);

	}

	private void fillBankTable(Properties prop) {
		int[] bank_ids = parseStringIntoIntegerArray(prop
				.getProperty("bank_item_ids"));
		int[] bank_amounts = parseStringIntoIntegerArray(prop
				.getProperty("bank_item_amounts"));
		for (int i = 0; i < bank_ids.length && i < bank_amounts.length; i++) {
			table_banking_items.setValueAt(bank_ids[i], i, 0);
			table_banking_items.setValueAt(bank_amounts[i], i, 1);
		}

	}

	private List<String> parseStringIntoList(String s) {
		List<String> intList = new ArrayList<String>();
		for (String a : Arrays.asList(s.substring(0, s.length()).split(",")))
			if (!a.equals(""))
				intList.add(a);
		return intList;
	}

	private int[] parseStringIntoIntegerArray(String s) {
		List<Integer> intList = new ArrayList<Integer>();
		for (String a : Arrays.asList(s.substring(0, s.length()).split(",")))
			if (!a.equals(""))
				intList.add(Integer.valueOf(a));
		return ArrayUtil.toArrayInt(intList);
	}

	private void fillLootTable(String text) {
		List<String> list = parseStringIntoList(text);
		for (int i = 0; i < list.size(); i++)
			table_loot.setValueAt(list.get(i), i, 0);
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

	private Value<int[]> getMonsterIDs() {
		int size = model_selected_monsters.getSize();
		if (size == 0)
			return new Value<int[]>(
					new int[] { parse(model_selected_monsters.get(0)) });
		List<Integer> temp_list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			temp_list.add(parse(model_selected_monsters.get(i)));
		return new Value<int[]>(ArrayUtil.toArrayInt(temp_list));
	}

	private int parse(String line) {
		String[] split = line.split("--");
		String fin = "";
		if (split.length > 0)
			fin = split[0].trim();
		else
			fin = line.trim();
		return Integer.parseInt(fin);
	}
}
