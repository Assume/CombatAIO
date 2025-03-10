package scripts.CombatAIO.com.base.main.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.util.Util;

import scripts.CombatAIO.com.base.api.presets.PresetFactory;
import scripts.CombatAIO.com.base.api.tasks.helper.Banker;
import scripts.CombatAIO.com.base.api.tasks.types.Value;
import scripts.CombatAIO.com.base.api.tasks.types.ValueType;
import scripts.CombatAIO.com.base.api.types.LootItem;
import scripts.CombatAIO.com.base.api.types.constants.FileSaveLocations;
import scripts.CombatAIO.com.base.api.types.constants.MonsterIDs;
import scripts.CombatAIO.com.base.api.types.enums.Food;
import scripts.CombatAIO.com.base.api.types.enums.Prayer;
import scripts.CombatAIO.com.base.api.types.enums.Weapon;
import scripts.CombatAIO.com.base.api.types.enums.WorldHoppingCondition;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.custom.types.CustomMovement;
import scripts.CombatAIO.com.base.main.Dispatcher;
import scripts.CombatAIO.com.base.main.gui.elements.UneditableDefaultTableModel;
import scripts.CombatAIO.com.base.main.utils.ArrayUtil;

public class BaseGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String CHANGELOG = "Changelog\r\n\r\nV2.0.7_9: Fixed bank withdrawing of potions so that it no longer withdraws the incorrect amount"
			+ "\r\n\r\nV2.0.8_0: Added the framework for presets. The first two presets have been added; Rellekka West and Rellekka East"
			+ "\r\n\r\nV2.0.8_1: Fixed banking for non-presets"
			+ "\r\n\r\nV2.0.8_2: Fixed picking up cannon on decay"
			+ "\r\n\r\nV2.0.8_3: Removed Bone2Peaches for lite mode -- was accidentally added"
			+ "\r\n\r\nV2.0.8_4: Added bury bones for premium subscribers"
			+ "\r\n\r\nV2.0.8_5: Fixed an issue where profiles wouldn't load"
			+ "\r\n\r\nV2.0.8_6: Updated the ingame world hopper and fixed an issue with loading"
			+ "\r\n\r\nV2.0.8_7: Fixed an issue with banking when also withdrawing other items"
			+ "\r\n\r\nV2.0.8_8: Blowpipe special added, minor changes to the paint"
			+ "\r\n\r\nV2.0.8_9: Updated world hopper for skill worlds" + "\r\n\r\nV2.0.9_0: Improved cannon handling"
			+ "\r\n\r\nV2.0.9_1: Fixed an issue with pickup cannon on world hop"
			+ "\r\n\r\nV2.0.9_2: Fixed an issue with Bones2Peaches"
			+ "\r\n\r\nV2.0.9_3: Removed strength potions from trash list"
			+ "\r\n\r\nV2.0.9_4: Fixed an issue Bones2Peaches and eating for space"
			+ "\r\n\r\nV2.0.9_5: Improvements to combat, possiblity of idle issue being fixed"
			+ "\r\n\r\nV2.0.9_6: Fixed an issue with the cannon" + "\r\n\r\nV2.0.9_7: Updated ID for Saradomin Godsword"
			+ "\r\n\r\nV2.0.9_8: Minor improvements to combat and bug fixes at East Rock Crabs"
			+ "\r\n\r\nV2.1.0_0: Progression mode released" + "\r\n\r\nV2.1.0_1: Saving added to progression mode"
			+ "\r\n\r\nV2.1.0_1: Minor changes to the back end"
			+ "\r\n\r\nV2.2.0_0: ABCV2 released at level 10. Small changes to paint and backend systems"
			+ "\r\n\r\nV2.2.0_1: Fixed an NPE"
			+ "\r\n\r\nV2.2.1_0: Added more conditions for world hopping, improved backend, lowered CPU usage, added more antipoison potions"
			+ "\r\n\r\nV2.2.1_1: Added Defence as a condition for progression mode"
			+ "\r\n\r\nV2.2.1_2: Fixed an issue where it wouldn't click second screen after a level-up"
			+ "\r\n\r\nV2.2.1_3: Fixed formatting issues with the GUI and issues with cannon support"
			+ "\r\n\r\nV2.2.3_0: A wide range of updates. Including a new private key for DaxWebWalker, an updated World Hopper, and Combat Improvements";

	private JPanel contentPane;

	private JComboBox<Food> combo_box_food;
	private JComboBox<Prayer> combo_box_prayer;
	private JComboBox<Weapon> combo_box_special_attack;
	private JComboBox<String> combo_box_settings;
	private JComboBox<PresetFactory> combo_box_preset;
	private JComboBox<WorldHoppingCondition> combo_box_world_hopping;

	private DefaultComboBoxModel<String> model_combo_box = new DefaultComboBoxModel<String>();

	private JPanel tab_one_panel;
	private JPanel tab_two_panel;
	private JPanel tab_three_panel;
	private JPanel tab_four_panel;

	private JTable table_loot;
	private JTable table_banking_items;

	private JScrollPane scrollPane_1;

	private JCheckBox chckbx_flicker;
	private JCheckBox chckbx_guthans;
	private JCheckBox chckbx_ranged;
	private JCheckBox chckbx_wait_for_loot;
	private JCheckBox chckbx_loot_in_combat;
	private JCheckBox chckbx_telekinetic_grab;
	private JCheckBox chckbx_cannon;
	private JCheckBox chckbx_attack_monsters_in_combat;
	private JCheckBox chckbx_bury_bones;
	private final JCheckBox chckbx_log_when_out_of_food;

	private JLabel lblBankItems;
	private JLabel lblPrayer;
	private JLabel lblOnlySome;
	private JLabel lblPossible;
	private JLabel lblSelected;
	private JLabel lblSpecialAttack;
	private JLabel lbl_cannon_tile;
	private JLabel lbl_safe_spot;

	private JButton button_refresh;
	private JButton button;
	private JButton button_add_to_possible;
	private JButton button_remove_from_possible;

	private JList<String> list_possible_monsters;
	private JList<String> list_selected_monsters;

	private DefaultListModel<String> model_possible_monsters;
	private DefaultListModel<String> model_selected_monsters;

	private JTextField text_field_loot_over_x;

	private JSpinner spinner_food;
	private JSpinner spinner_combat_radius;

	private RSTile safe_spot_tile;

	private RSTile cannon_tile;

	public BaseGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 645, 368);
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

		UneditableDefaultTableModel uneditable_default_table_model = new UneditableDefaultTableModel(new Object[][] {
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, { null, false }, { null, false }, { null, false },
				{ null, false }, { null, false }, { null, false }, }, new String[] { "Item name", "Alch" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, Boolean.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		table_loot = new JTable(uneditable_default_table_model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component c = super.prepareRenderer(renderer, row, col);
				if (col == 1)
					c.setEnabled(!Dispatcher.get().isLiteMode());
				return c;
			}
		};
		scrollPane.setViewportView(table_loot);
		table_loot.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		table_loot.getColumnModel().getColumn(1).setMaxWidth(35);

		JLabel lblNewLabel = new JLabel("Loot items");
		lblNewLabel.setBounds(10, 11, 70, 14);
		tab_two_panel.add(lblNewLabel);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(341, 31, 243, 227);
		tab_two_panel.add(scrollPane_1);

		table_banking_items = new JTable();
		scrollPane_1.setViewportView(table_banking_items);
		table_banking_items.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table_banking_items.setModel(new DefaultTableModel(new Object[][] { { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null } },

				new String[] { "Item ID", "Amount" }));

		lblBankItems = new JLabel("Bank items");
		lblBankItems.setBounds(374, 11, 70, 14);
		tab_two_panel.add(lblBankItems);

		JLabel lblLootOverX = new JLabel("Loot over X");
		lblLootOverX.setBounds(202, 38, 103, 14);
		tab_two_panel.add(lblLootOverX);

		text_field_loot_over_x = new JTextField();
		text_field_loot_over_x.setBounds(225, 63, 80, 20);
		tab_two_panel.add(text_field_loot_over_x);
		text_field_loot_over_x.setColumns(10);
		if (Dispatcher.get().isLiteMode())
			text_field_loot_over_x.setEditable(false);

		chckbx_wait_for_loot = new JCheckBox("Wait for loot");
		chckbx_wait_for_loot.setBounds(198, 90, 111, 23);
		tab_two_panel.add(chckbx_wait_for_loot);

		chckbx_loot_in_combat = new JCheckBox("Loot in combat");
		chckbx_loot_in_combat.setBounds(198, 116, 174, 23);
		tab_two_panel.add(chckbx_loot_in_combat);

		chckbx_telekinetic_grab = new JCheckBox("Telekinetic Grab");
		chckbx_telekinetic_grab.setBounds(198, 142, 174, 23);
		tab_two_panel.add(chckbx_telekinetic_grab);

		chckbx_log_when_out_of_food = new JCheckBox("Logout when out of food");
		chckbx_log_when_out_of_food.addActionListener(e -> {
			if (chckbx_log_when_out_of_food.isSelected())
				JOptionPane.showMessageDialog(null, "Do not use if fighting aggressive monsters");
		});
		chckbx_log_when_out_of_food.setBounds(10, 265, 220, 23);
		tab_two_panel.add(chckbx_log_when_out_of_food);
		if (Dispatcher.get().isLiteMode())
			this.chckbx_telekinetic_grab.setEnabled(false);
		tabbedPane.addTab("Combat", tab_three_panel);
		tab_three_panel.setLayout(null);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tabbedPane.addTab("Advanced", tab_four_panel);
		tab_four_panel.setLayout(null);

		button = new JButton("Progression");
		button.setBounds(10, 22, 122, 23);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_four_panel.add(button);
		button.addActionListener(e -> {
			if (Dispatcher.get().isLiteMode()) {
				showPremiumMessageDialog("Progression");
				return;
			} else
				new ProgressionGUI().setVisible(true);
		});

		JButton btnNewButton_1 = new JButton("Custom Walking");
		btnNewButton_1.addActionListener(e -> {
			if (Dispatcher.get().isLiteMode()) {
				showPremiumMessageDialog("Custom walking");
				return;
			}
			CustomWalkingGUI bmaingui = new CustomWalkingGUI();
			bmaingui.setVisible(true);
		});
		btnNewButton_1.setBounds(10, 56, 122, 23);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_four_panel.add(btnNewButton_1);

		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(185, 11, 419, 248);
		tab_four_panel.add(scrollPane_4);

		JTextPane text_pane_changelog = new JTextPane();
		scrollPane_4.setViewportView(text_pane_changelog);
		text_pane_changelog.setText(CHANGELOG);
		text_pane_changelog.setEditable(false);

		// TODO Prayer.values()
		combo_box_prayer = new JComboBox<Prayer>(Prayer.values());
		combo_box_prayer.setBounds(10, 31, 121, 20);
		tab_three_panel.add(combo_box_prayer);
		if (Dispatcher.get().isLiteMode())
			combo_box_prayer.setEnabled(false);

		lblPrayer = new JLabel("Prayer");
		lblPrayer.setBounds(10, 11, 46, 14);
		tab_three_panel.add(lblPrayer);

		chckbx_flicker = new JCheckBox("Flicker*");
		chckbx_flicker.setBounds(10, 58, 97, 23);
		tab_three_panel.add(chckbx_flicker);
		if (Dispatcher.get().isLiteMode())
			chckbx_flicker.setEnabled(false);

		chckbx_guthans = new JCheckBox("Guthans");
		chckbx_guthans.setBounds(10, 84, 97, 23);
		tab_three_panel.add(chckbx_guthans);
		if (Dispatcher.get().isLiteMode())
			chckbx_guthans.setEnabled(false);

		lblOnlySome = new JLabel("*Only piety and chivalry are supported for flicker");
		lblOnlySome.setBounds(10, 266, 347, 14);
		tab_three_panel.add(lblOnlySome);

		chckbx_ranged = new JCheckBox("Ranged/Magic");
		chckbx_ranged.setBounds(131, 58, 119, 23);
		tab_three_panel.add(chckbx_ranged);

		// TODO Weapon.values()
		combo_box_special_attack = new JComboBox<Weapon>(Weapon.values());
		combo_box_special_attack.setBounds(151, 31, 121, 20);
		tab_three_panel.add(combo_box_special_attack);
		if (Dispatcher.get().isLiteMode())
			combo_box_special_attack.setEnabled(false);

		lblSpecialAttack = new JLabel("Special Attack");
		lblSpecialAttack.setBounds(151, 11, 121, 14);
		tab_three_panel.add(lblSpecialAttack);

		lbl_safe_spot = new JLabel("Safe spot: ");
		lbl_safe_spot.setBounds(131, 172, 141, 14);
		// if (!Dispatcher.get().isRockCrabsScriptID())
		tab_three_panel.add(lbl_safe_spot);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(e -> {
			if (Dispatcher.get().isLiteMode())
				showPremiumMessageDialog("Safe spot");
			else {
				safe_spot_tile = Player.getRSPlayer().getPosition();
				if (safe_spot_tile != null)
					lbl_safe_spot.setText("Safe spot: " + safe_spot_tile.toString());
			}
		});
		btnSet.setBounds(10, 168, 77, 20);
		// if (!Dispatcher.get().isRockCrabsScriptID())
		tab_three_panel.add(btnSet);
		if (Dispatcher.get().isLiteMode())
			btnSet.setEnabled(false);

		chckbx_cannon = new JCheckBox("Cannon");
		chckbx_cannon.addActionListener(e -> {
			if (chckbx_cannon.isSelected() && cannon_tile == null)
				JOptionPane.showMessageDialog(null, "Please now set a cannon tile");
		});
		chckbx_cannon.setBounds(131, 84, 82, 23);
		//tab_three_panel.add(chckbx_cannon);
		if (Dispatcher.get().isLiteMode())
			chckbx_cannon.setEnabled(false);

		lbl_cannon_tile = new JLabel("Cannon tile: ");
		lbl_cannon_tile.setBounds(131, 143, 161, 14);
		//tab_three_panel.add(lbl_cannon_tile);

		JButton btn_cannon_tile = new JButton("Set");
		btn_cannon_tile.addActionListener(e -> {
			if (Dispatcher.get().isLiteMode()) {
				showPremiumMessageDialog("Cannon");
				return;
			}
			cannon_tile = Player.getRSPlayer().getPosition();
			if (cannon_tile != null)
				lbl_cannon_tile.setText("Cannon tile: " + cannon_tile.toString());
		});
		btn_cannon_tile.setBounds(10, 140, 77, 20);
		//tab_three_panel.add(btn_cannon_tile);

		chckbx_attack_monsters_in_combat = new JCheckBox("Attack monsters in combat");
		chckbx_attack_monsters_in_combat.setBounds(131, 110, 238, 23);
		tab_three_panel.add(chckbx_attack_monsters_in_combat);

		chckbx_bury_bones = new JCheckBox("Bury bones");
		chckbx_bury_bones.setBounds(10, 110, 121, 23);
		tab_three_panel.add(chckbx_bury_bones);

		// TODO WorldHoppingCondition.values()
		combo_box_world_hopping = new JComboBox<WorldHoppingCondition>(WorldHoppingCondition.values());
		if (Dispatcher.get().isLiteMode())
			combo_box_world_hopping.setEnabled(false);
		combo_box_world_hopping.addActionListener(e -> {
			try {
				if (combo_box_world_hopping.getSelectedIndex() == 1)
					WorldHoppingCondition.PLAYERS_PRESENT.setValue(
							getInt(JOptionPane.showInputDialog("Enter number of players at which you should hop: ")));
			} catch (Exception r) {
			}
		});
		combo_box_world_hopping.setBounds(10, 235, 161, 20);
		tab_three_panel.add(combo_box_world_hopping);

		JLabel lblworldhopping = new JLabel("World hopping condition");
		lblworldhopping.setBounds(10, 210, 218, 14);
		tab_three_panel.add(lblworldhopping);
		if (Dispatcher.get().isLiteMode())
			chckbx_bury_bones.setEnabled(false);

		if (Dispatcher.get().isLiteMode())
			btn_cannon_tile.setEnabled(false);

		// TODO Food.values()
		combo_box_food = new JComboBox<Food>(Food.values());
		combo_box_food.setBounds(10, 31, 121, 20);
		tab_one_panel.add(combo_box_food);
		if (Dispatcher.get().isLiteMode())
			combo_box_food.removeItemAt(9);

		JLabel lblFood = new JLabel("Food");
		lblFood.setBounds(10, 11, 46, 14);
		tab_one_panel.add(lblFood);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(e -> {
			set();
			setVisible(false);
		});
		btnStart.setBounds(519, 261, 89, 23);
		tab_one_panel.add(btnStart);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter name for this profile");
			if (name == null) {
				JOptionPane.showMessageDialog(null, "That is an invalid name, save failed");
				return;
			}
			set();
			save(name);
			saveMovements(name);
		});
		btnSave.setBounds(142, 261, 89, 23);
		tab_one_panel.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(e -> {
			try {
				String name = combo_box_settings.getSelectedItem().toString();
				if (name == null)
					return;
				load(name);
				loadMovements(name);
			} catch (Exception x) {
				x.printStackTrace();
			}
		});
		btnLoad.setBounds(42, 261, 89, 23);
		tab_one_panel.add(btnLoad);

		JLabel lblWithdrawAmount = new JLabel("Amount");
		lblWithdrawAmount.setBounds(10, 65, 89, 14);
		tab_one_panel.add(lblWithdrawAmount);

		spinner_food = new JSpinner();
		spinner_food.setBounds(85, 62, 46, 20);
		tab_one_panel.add(spinner_food);
		spinner_food.setValue(12);

		combo_box_settings = new JComboBox<String>();
		combo_box_settings.setBounds(10, 229, 160, 20);
		tab_one_panel.add(combo_box_settings);
		combo_box_settings.setModel(this.model_combo_box);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(141, 32, 195, 165);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_one_panel.add(scrollPane_2);

		list_possible_monsters = new JList<String>();
		scrollPane_2.setViewportView(list_possible_monsters);
		list_possible_monsters.setModel(model_possible_monsters);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(410, 32, 195, 165);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_one_panel.add(scrollPane_3);

		list_selected_monsters = new JList<String>();
		scrollPane_3.setViewportView(list_selected_monsters);
		list_selected_monsters.setModel(model_selected_monsters);

		button_add_to_possible = new JButton(">");
		button_add_to_possible.addActionListener(e -> {
			List<String> selected = list_possible_monsters.getSelectedValuesList();
			for (String x : selected) {
				if (model_selected_monsters.contains(x))
					continue;
				model_selected_monsters.addElement(x);
			}
		});
		button_add_to_possible.setBounds(346, 75, 54, 20);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_one_panel.add(button_add_to_possible);

		button_remove_from_possible = new JButton("<");
		button_remove_from_possible.addActionListener(e -> {
			List<String> selected = list_selected_monsters.getSelectedValuesList();
			for (String x : selected)
				model_selected_monsters.removeElement(x);

		});
		button_remove_from_possible.setBounds(346, 106, 54, 20);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_one_panel.add(button_remove_from_possible);

		lblPossible = new JLabel("Possible");
		lblPossible.setBounds(142, 11, 70, 14);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_one_panel.add(lblPossible);

		lblSelected = new JLabel("Selected");
		lblSelected.setBounds(410, 11, 70, 14);
		if (!Dispatcher.get().isRockCrabsScriptID())
			tab_one_panel.add(lblSelected);

		button_refresh = new JButton("Refresh");
		button_refresh.addActionListener(e -> {
			for (RSNPC n : NPCs.getAll())
				if (n.getCombatLevel() > 0
						&& !model_possible_monsters
								.contains(n.getID() + " -- " + n.getName() + " (" + n.getCombatLevel() + ")")
						&& n.getName() != null && !n.getName().equals("null"))
					model_possible_monsters
							.addElement(n.getID() + " -- " + n.getName() + " (" + n.getCombatLevel() + ")");
		});
		button_refresh.setBounds(248, 207, 89, 23);
		tab_one_panel.add(button_refresh);

		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setBounds(10, 90, 46, 14);
		tab_one_panel.add(lblRadius);

		spinner_combat_radius = new JSpinner();
		spinner_combat_radius.setBounds(85, 84, 46, 20);
		tab_one_panel.add(spinner_combat_radius);
		spinner_combat_radius.setValue(15);

		JLabel lblPreset = new JLabel("Preset");
		lblPreset.setBounds(10, 123, 59, 14);
		tab_one_panel.add(lblPreset);

		// TODO PresetFactory.getPresetsForScript()
		combo_box_preset = new JComboBox<PresetFactory>(PresetFactory.values());
		combo_box_preset.setBounds(10, 148, 121, 20);
		tab_one_panel.add(combo_box_preset);
		combo_box_preset.addItemListener(event -> {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				if (!combo_box_preset.getSelectedItem().equals(PresetFactory.Automatic)) {
					button_add_to_possible.setEnabled(false);
					button_remove_from_possible.setEnabled(false);
					list_possible_monsters.setEnabled(false);
					list_selected_monsters.setEnabled(false);
					button_refresh.setEnabled(false);
				} else {
					button_add_to_possible.setEnabled(true);
					button_remove_from_possible.setEnabled(true);
					list_possible_monsters.setEnabled(true);
					list_selected_monsters.setEnabled(true);
					button_refresh.setEnabled(true);
				}
			}
		});

		lblProfile = new JLabel("Profile");
		lblProfile.setBounds(10, 203, 59, 14);
		tab_one_panel.add(lblProfile);
		if (Dispatcher.get().isLiteMode())
			combo_box_preset.setEnabled(false);

		fillSettingsNames();

	}

	private void fillSettingsNames() {
		model_combo_box.removeAllElements();
		File[] files = new File(Util.getWorkingDirectory() + File.separator + "Base").listFiles();
		if (files == null)
			return;
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".ini"))
				combo_box_settings.addItem(file.getName().replace(".ini", ""));
		}
	}

	public void set() {
		Dispatcher.get().setPreset(Dispatcher.get().isLiteMode() ? PresetFactory.Automatic
				: (PresetFactory) this.combo_box_preset.getSelectedItem());
		Dispatcher.get().set(ValueType.FOOD, new Value<Food>((Food) combo_box_food.getSelectedItem()));
		Dispatcher.get().set(ValueType.WAIT_FOR_LOOT, new Value<Boolean>(chckbx_wait_for_loot.isSelected()));
		Dispatcher.get().set(ValueType.LOOT_IN_COMBAT, new Value<Boolean>(chckbx_loot_in_combat.isSelected()));
		// Dispatcher.get().set(ValueType.MONSTER_IDS, getMonsterIDs());
		setMonsterIDs();
		Dispatcher.get().set(ValueType.SPECIAL_ATTACK_WEAPON,
				new Value<Weapon>((Weapon) combo_box_special_attack.getSelectedItem()));
		Dispatcher.get().set(ValueType.USE_GUTHANS,
				new Value<Boolean>(Dispatcher.get().isLiteMode() ? false : chckbx_guthans.isSelected()));
		Dispatcher.get().set(ValueType.IS_RANGING, new Value<Boolean>(chckbx_ranged.isSelected()));
		Dispatcher.get().set(ValueType.PRAYER, new Value<Prayer>((Prayer) combo_box_prayer.getSelectedItem()));
		Dispatcher.get().set(ValueType.FOOD_WITHDRAW_AMOUNT,
				new Value<Integer>(Integer.parseInt(spinner_food.getValue().toString())));
		Dispatcher.get().set(ValueType.FLICKER,
				new Value<Boolean>(Dispatcher.get().isLiteMode() ? false : chckbx_flicker.isSelected()));
		Dispatcher.get().set(ValueType.COMBAT_RADIUS,
				new Value<Integer>(Integer.parseInt(spinner_combat_radius.getValue().toString())));
		Dispatcher.get().set(ValueType.SAFE_SPOT_TILE, new Value<RSTile>(this.safe_spot_tile));
		Dispatcher.get().set(ValueType.USE_TELEKINETIC_GRAB,
				new Value<Boolean>(Dispatcher.get().isLiteMode() ? false : chckbx_telekinetic_grab.isSelected()));
		Dispatcher.get().set(ValueType.USE_CANNON, new Value<Boolean>(
				Dispatcher.get().isLiteMode() || this.cannon_tile == null ? false : chckbx_cannon.isSelected()));
		Dispatcher.get().set(ValueType.CANNON_TILE, new Value<RSTile>(this.cannon_tile));
		Dispatcher.get().set(ValueType.ATTACK_MONSTERS_IN_COMBAT,
				new Value<Boolean>(chckbx_attack_monsters_in_combat.isSelected()));
		Dispatcher.get().set(ValueType.BURY_BONES,
				new Value<Boolean>(Dispatcher.get().isLiteMode() ? false : chckbx_bury_bones.isSelected()));
		Dispatcher.get().set(ValueType.WORLD_HOP_CONDITION,
				new Value<WorldHoppingCondition>((WorldHoppingCondition) combo_box_world_hopping.getSelectedItem()));
		Dispatcher.get().set(ValueType.LOG_WHEN_OUT_OF_FOOD,
				new Value<Boolean>(chckbx_log_when_out_of_food.isSelected()));
		String loot_over_x = text_field_loot_over_x.getText();
		if (loot_over_x != null && loot_over_x.length() != 0)
			Dispatcher.get().set(ValueType.MINIMUM_LOOT_VALUE,
					new Value<Integer>(Integer.parseInt(loot_over_x.replaceAll("[^0-9]", ""))));
		setBankingList();
		setLootingList();
	}

	private void setMonsterIDs() {
		Value<int[]> value = null;
		if (Dispatcher.get().isRockCrabsPreset())
			value = new Value<int[]>(MonsterIDs.ROCK_CRAB_AWAKE_IDS);
		else if (Dispatcher.get().isFireGiantsPreset())
			value = new Value<int[]>(MonsterIDs.FIRE_GIANT);
		else
			value = getMonsterIDs();
		Dispatcher.get().set(ValueType.MONSTER_IDS, value);

	}

	private void setLootingList() {
		for (int i = 0; i < 60; i++) {
			Object val = table_loot.getValueAt(i, 0);
			Boolean alch = (Boolean) table_loot.getValueAt(i, 1);
			if (val != null)
				Dispatcher.get().set(ValueType.LOOT_ITEM,
						new Value<LootItem>(new LootItem(val.toString(), true, alch)));
		}
	}

	private void setBankingList() {
		Banker b = Dispatcher.get().getBanker();
		for (int i = 0; i < 28; i++) {
			Object id = table_banking_items.getValueAt(i, 0);
			Object amount = table_banking_items.getValueAt(i, 1);
			if (id != null && amount != null)
				b.addBankItem(Integer.parseInt(id.toString().trim()), Integer.parseInt(amount.toString().trim()));
		}
	}

	public void save(String name) {
		try {
			Properties prop = new Properties();
			prop.setProperty("food", Dispatcher.get().get(ValueType.FOOD).getValue().toString());
			prop.setProperty("ranging", Dispatcher.get().get(ValueType.IS_RANGING).getValue().toString());
			prop.setProperty("prayer", Dispatcher.get().get(ValueType.PRAYER).getValue().toString());
			prop.setProperty("loot_items",
					lootItemsToString(((LootItem[]) Dispatcher.get().get(ValueType.ALL_LOOT_ITEMS).getValue())));
			prop.setProperty("loot_in_combat", Dispatcher.get().get(ValueType.LOOT_IN_COMBAT).getValue().toString());
			prop.setProperty("wait_for_loot", Dispatcher.get().get(ValueType.WAIT_FOR_LOOT).getValue().toString());
			prop.setProperty("special_attack_weapon",
					Dispatcher.get().get(ValueType.SPECIAL_ATTACK_WEAPON).getValue().toString());
			prop.setProperty("monster_ids",
					intArrayToString(((int[]) Dispatcher.get().get(ValueType.MONSTER_IDS).getValue())));
			prop.setProperty("bank_item_ids", intArrayToString(Dispatcher.get().getBanker().getItemIds()));
			prop.setProperty("bank_item_amounts", intArrayToString(Dispatcher.get().getBanker().getItemAmounts()));
			prop.setProperty("minimum_loot_value",
					Dispatcher.get().get(ValueType.MINIMUM_LOOT_VALUE).getValue().toString());
			prop.setProperty("food_withdraw_amount",
					Dispatcher.get().get(ValueType.FOOD_WITHDRAW_AMOUNT).getValue().toString());
			prop.setProperty("use_flicker", Dispatcher.get().get(ValueType.FLICKER).getValue().toString());
			prop.setProperty("use_guthans", Dispatcher.get().get(ValueType.USE_GUTHANS).getValue().toString());
			prop.setProperty("combat_radius", Dispatcher.get().get(ValueType.COMBAT_RADIUS).getValue().toString());
			prop.setProperty("world_hop_condition",
					Dispatcher.get().get(ValueType.WORLD_HOP_CONDITION).getValue().toString());
			prop.setProperty("world_hop_value",
					Integer.toString(
							((WorldHoppingCondition) Dispatcher.get().get(ValueType.WORLD_HOP_CONDITION).getValue())
									.getValue()));

			String safe_spot_tile_text = this.safe_spot_tile == null ? "null"
					: this.safe_spot_tile.toString().replaceAll("[^0-9,]", "");
			prop.setProperty("safe_spot_tile", safe_spot_tile_text);
			prop.setProperty("tele_grab",
					((Boolean) Dispatcher.get().get(ValueType.USE_TELEKINETIC_GRAB).getValue()).toString());
			prop.setProperty("use_cannon", Dispatcher.get().get(ValueType.USE_CANNON).getValue().toString());
			prop.setProperty("attack_monsters_in_combat",
					Dispatcher.get().get(ValueType.ATTACK_MONSTERS_IN_COMBAT).getValue().toString());
			String cannon_tile_text = this.cannon_tile == null ? "null"
					: this.cannon_tile.toString().replaceAll("[^0-9,]", "");
			prop.setProperty("cannon_tile", cannon_tile_text);
			prop.setProperty("preset", Dispatcher.get().getPreset().toString());
			prop.setProperty("bury_bones", Dispatcher.get().get(ValueType.BURY_BONES).getValue().toString());
			prop.setProperty("log_out_when_out_of_food",
					Dispatcher.get().get(ValueType.LOG_WHEN_OUT_OF_FOOD).getValue().toString());
			new File(FileSaveLocations.getFileLocation()).mkdirs();
			FileOutputStream streamO = new FileOutputStream(
					FileSaveLocations.getFileLocation() + File.separator + name + ".ini");
			prop.store(streamO, null);
			streamO.flush();
			streamO.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean load(String name) {
		try {
			FileInputStream in = new FileInputStream(
					FileSaveLocations.getFileLocation() + File.separator + name + ".ini");
			Properties prop = new Properties();
			prop.load(in);
			combo_box_food.setSelectedItem(Food.getFoodFromName(prop.getProperty("food")));
			chckbx_ranged.setSelected(Boolean.parseBoolean(prop.getProperty("ranging")));
			combo_box_prayer.setSelectedItem(
					Dispatcher.get().isLiteMode() ? Prayer.NONE : Prayer.parse(prop.getProperty("prayer")));
			fillLootTable(prop.getProperty("loot_items"));
			chckbx_loot_in_combat.setSelected(Boolean.parseBoolean(prop.getProperty("loot_in_combat")));
			chckbx_wait_for_loot.setSelected(Boolean.parseBoolean(prop.getProperty("wait_for_loot")));
			combo_box_special_attack.setSelectedItem(Dispatcher.get().isLiteMode() ? Weapon.NONE
					: Weapon.getWeaponFromName(prop.getProperty("special_attack_weapon")));
			String val = prop.getProperty("minimum_loot_value");
			if (!val.equalsIgnoreCase("2147483647"))
				text_field_loot_over_x
						.setText(Dispatcher.get().isLiteMode() ? "" : prop.getProperty("minimum_loot_value"));
			spinner_food.setValue(Integer.parseInt(prop.getProperty("food_withdraw_amount")));
			fillSelectedMonster(prop.getProperty("monster_ids"));
			fillBankTable(prop);
			chckbx_flicker.setSelected(
					Dispatcher.get().isLiteMode() ? false : Boolean.parseBoolean(prop.getProperty("use_flicker")));
			chckbx_guthans.setSelected(
					Dispatcher.get().isLiteMode() ? false : Boolean.parseBoolean(prop.getProperty("use_guthans")));
			spinner_combat_radius.setValue(Integer.parseInt(prop.getProperty("combat_radius")));
			chckbx_ranged.setSelected(Dispatcher.get().isLiteMode() ? false : Boolean.parseBoolean("tele_grab"));
			combo_box_preset.setSelectedItem(PresetFactory.getPresetForName(prop.getProperty("preset")));
			chckbx_attack_monsters_in_combat
					.setSelected(Boolean.parseBoolean(prop.getProperty("attack_monsters_in_combat")));
			chckbx_bury_bones.setSelected(
					Dispatcher.get().isLiteMode() ? false : Boolean.parseBoolean(prop.getProperty("bury_bones")));
			chckbx_log_when_out_of_food.setSelected(Boolean.parseBoolean(prop.getProperty("log_out_when_out_of_food")));
			combo_box_world_hopping
					.setSelectedItem(WorldHoppingCondition.valueOf(prop.getProperty("world_hop_condition")));
			WorldHoppingCondition.PLAYERS_PRESENT.setValue(getInt(prop.getProperty("world_hop_value")));
			if (Dispatcher.get().isLiteMode())
				this.safe_spot_tile = null;
			else
				this.safe_spot_tile = getSafeSpotTile(prop);
			if (this.safe_spot_tile != null)
				lbl_safe_spot.setText("Safe spot:        " + safe_spot_tile.toString());
			if (Dispatcher.get().isLiteMode()) {
				this.cannon_tile = null;
				chckbx_cannon.setSelected(false);
			} else
				this.cannon_tile = getCannonTile(prop);
			if (this.cannon_tile != null)
				lbl_cannon_tile.setText("Cannon tile: " + this.cannon_tile.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private static final String MOVEMENT_PATH = Util.getWorkingDirectory() + File.separator + "Base" + File.separator
			+ "movements";
	private JLabel lblProfile;
	private JScrollPane scrollPane_4;

	private void saveMovements(String name) {
		new File(MOVEMENT_PATH).mkdirs();
		List<CustomMovement> movements = WalkingManager.getMovements();
		try {
			FileOutputStream fout = new FileOutputStream(MOVEMENT_PATH + File.separator + name + ".cmov");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(movements);
			fout.flush();
			fout.close();
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadMovements(String name) {
		try {
			FileInputStream fis = new FileInputStream(MOVEMENT_PATH + File.separator + name + ".cmov");
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<CustomMovement> cmovements = (List<CustomMovement>) ois.readObject();
			WalkingManager.setMovements(cmovements);
			fis.close();
			ois.close();
		} catch (Exception e) {
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

	private String lootItemsToString(LootItem[] items) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < items.length; i++) {
			if (i < items.length - 1) {
				if (items[i].shouldAlch())
					b.append(items[i].getName() + "A" + ",");
				else
					b.append(items[i].getName() + ",");
			} else {
				if (items[i].shouldAlch())
					b.append(items[i].getName() + "A");
				else
					b.append(items[i].getName());
			}
		}
		return b.toString();
	}

	private RSTile getSafeSpotTile(Properties prop) {
		String text = prop.getProperty("safe_spot_tile");
		if (text.equalsIgnoreCase("null"))
			return null;
		String[] text_ar = text.split(",");
		if (text_ar.length > 2)
			return new RSTile(getInt(text_ar[0]), getInt(text_ar[1]), getInt(text_ar[2]));
		return null;
	}

	private RSTile getCannonTile(Properties prop) {
		String text = prop.getProperty("cannon_tile");
		if (text.equalsIgnoreCase("null"))
			return null;
		String[] text_ar = text.split(",");
		if (text_ar.length > 2)
			return new RSTile(getInt(text_ar[0]), getInt(text_ar[1]), getInt(text_ar[2]));
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
		int[] bank_ids = parseStringIntoIntegerArray(prop.getProperty("bank_item_ids"));
		int[] bank_amounts = parseStringIntoIntegerArray(prop.getProperty("bank_item_amounts"));
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
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			if (name.endsWith("A") && !Dispatcher.get().isLiteMode()) {
				table_loot.setValueAt(name.substring(0, name.length() - 1), i, 0);
				table_loot.setValueAt(true, i, 1);
			} else
				table_loot.setValueAt(name, i, 0);
		}

	}

	private Value<int[]> getMonsterIDs() {
		int size = model_selected_monsters.getSize();
		if (size == 0)
			return new Value<int[]>(new int[] { parse(model_selected_monsters.get(0)) });
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

	private void showPremiumMessageDialog(String feature_name) {
		JOptionPane.showMessageDialog(null, feature_name + " is only available on CombatAIO Premium");
	}
}
