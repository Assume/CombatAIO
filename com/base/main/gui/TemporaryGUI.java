package scripts.CombatAIO.com.base.main.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TemporaryGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(66, 11, 142, 26);
		contentPane.add(comboBox);
		
		JLabel lblFood = new JLabel("Food");
		lblFood.setBounds(10, 17, 46, 14);
		contentPane.add(lblFood);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(66, 119, 142, 265);
		contentPane.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JCheckBox chckbxFlicker = new JCheckBox("Flicker");
		chckbxFlicker.setBounds(304, 109, 97, 23);
		contentPane.add(chckbxFlicker);
		
		JCheckBox chckbxLootInCombat = new JCheckBox("Loot in Combat");
		chckbxLootInCombat.setBounds(304, 135, 113, 23);
		contentPane.add(chckbxLootInCombat);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Wait for loot");
		chckbxNewCheckBox.setBounds(304, 161, 113, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(240, 206, 184, 179);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
	}
}
