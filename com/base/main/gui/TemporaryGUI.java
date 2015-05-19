package scripts.CombatAIO.com.base.main.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.tribot.api2007.types.RSNPC;

import scripts.CombatAIO.com.base.api.threading.Dispatcher;
import scripts.CombatAIO.com.base.api.threading.types.Value;
import scripts.CombatAIO.com.base.api.threading.types.ValueType;
import scripts.CombatAIO.com.base.api.types.enums.Food;

public class TemporaryGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TemporaryGUI frame = new TemporaryGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TemporaryGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JComboBox<Food> comboBoxFood = new JComboBox<Food>();
		comboBoxFood.setBounds(66, 11, 142, 26);
		contentPane.add(comboBoxFood);

		JLabel lblFood = new JLabel("Food");
		lblFood.setBounds(10, 17, 46, 14);
		contentPane.add(lblFood);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 119, 184, 265);
		contentPane.add(scrollPane);

		final JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);

		final JCheckBox chckbxFlicker = new JCheckBox("Flicker");
		chckbxFlicker.setBounds(304, 109, 97, 23);
		contentPane.add(chckbxFlicker);

		final JCheckBox chckbxLootInCombat = new JCheckBox("Loot in Combat");
		chckbxLootInCombat.setBounds(304, 135, 113, 23);
		contentPane.add(chckbxLootInCombat);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Wait for loot");
		chckbxNewCheckBox.setBounds(304, 161, 113, 23);
		contentPane.add(chckbxNewCheckBox);

		JButton btnBankSetup = new JButton("Bank setup");
		btnBankSetup.setBounds(312, 191, 89, 23);
		contentPane.add(btnBankSetup);

		JButton btnLootSetup = new JButton("Loot setup");
		btnLootSetup.setBounds(312, 225, 89, 23);
		contentPane.add(btnLootSetup);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dispatcher.get().set(ValueType.FOOD,
						new Value<Food>((Food) comboBoxFood.getSelectedItem()));
				Dispatcher.get().set(ValueType.FLICKER_PRAYER,
						new Value<Boolean>(chckbxFlicker.isSelected()));
				Dispatcher.get().set(ValueType.WAIT_FOR_LOOT,
						new Value<Boolean>(chckbxLootInCombat.isSelected()));
				Dispatcher.get().set(ValueType.LOOT_IN_COMBAT,
						new Value<Boolean>(chckbxLootInCombat.isSelected()));
				Dispatcher.get()
						.set(ValueType.MONSTER_NAMES, getMonsterNames());
			}

			private Value<String[]> getMonsterNames() {
				List<String> temp = list.getSelectedValuesList();
				return new Value<String[]>(
						temp.toArray(new String[temp.size()]));
			}
		});
		btnStart.setBounds(335, 422, 89, 23);
		contentPane.add(btnStart);
	}
}
