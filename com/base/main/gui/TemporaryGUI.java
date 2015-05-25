package scripts.CombatAIO.com.base.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import javax.swing.border.EmptyBorder;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;

public class TemporaryGUI extends JFrame {

	private JPanel contentPane;

	private JList<String> monster_list;
	private JComboBox<Food> combo_box_food;
	private JCheckBox check_box_loot_in_combat;
	private JCheckBox check_box_wait_for_loot;

	public TemporaryGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		combo_box_food = new JComboBox<Food>(Food.values());
		combo_box_food.setBounds(66, 11, 142, 26);
		contentPane.add(combo_box_food);

		JLabel lblFood = new JLabel("Food");
		lblFood.setBounds(10, 17, 46, 14);
		contentPane.add(lblFood);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 119, 184, 265);
		contentPane.add(scrollPane);
		final DefaultListModel<String> monster_list_model = new DefaultListModel<String>();
		monster_list = new JList<String>();
		monster_list.setModel(monster_list_model);
		scrollPane.setViewportView(monster_list);

		check_box_loot_in_combat = new JCheckBox("Loot in Combat");
		check_box_loot_in_combat.setBounds(304, 135, 113, 23);
		contentPane.add(check_box_loot_in_combat);

		check_box_wait_for_loot = new JCheckBox("Wait for loot");
		check_box_wait_for_loot.setBounds(304, 161, 113, 23);
		contentPane.add(check_box_wait_for_loot);

		JButton btnBankSetup = new JButton("Bank setup");
		btnBankSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Currently too lazy to finish this gui, just want to get it out. It will withdraw food automatically, don't worry.");
				// DumpItem x = new ItemFinderGUI().get();
			}
		});
		btnBankSetup.setBounds(312, 191, 89, 23);
		contentPane.add(btnBankSetup);

		JButton btnLootSetup = new JButton("Loot setup");
		btnLootSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Currently too lazy to finish this gui, just want to get it out. It will loot clue scrolls");
			}
		});
		btnLootSetup.setBounds(312, 225, 89, 23);
		contentPane.add(btnLootSetup);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				set();
				setVisible(false);
				dispose();
			}

		});
		btnStart.setBounds(335, 422, 89, 23);
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
		btnRefresh.setBounds(119, 389, 89, 23);
		contentPane.add(btnRefresh);
	}

	public void set() {
		Dispatcher.get().set(ValueType.FOOD,
				new Value<Food>((Food) combo_box_food.getSelectedItem()));
		Dispatcher.get().set(ValueType.WAIT_FOR_LOOT,
				new Value<Boolean>(check_box_wait_for_loot.isSelected()));
		Dispatcher.get().set(ValueType.LOOT_IN_COMBAT,
				new Value<Boolean>(check_box_wait_for_loot.isSelected()));
		Dispatcher.get().set(ValueType.MONSTER_NAMES, getMonsterNames());
	}

	private Value<String[]> getMonsterNames() {
		List<String> temp = monster_list.getSelectedValuesList();
		return new Value<String[]>(temp.toArray(new String[temp.size()]));
	}
}
