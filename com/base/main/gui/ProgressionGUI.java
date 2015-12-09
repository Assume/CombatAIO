package scripts.CombatAIO.com.base.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import scripts.CombatAIO.com.base.api.progression.CProgressionAction;
import scripts.CombatAIO.com.base.api.progression.CProgressionCondition;
import scripts.CombatAIO.com.base.api.progression.CProgressionFactory;
import scripts.CombatAIO.com.base.api.progression.CProgressionMove;
import scripts.CombatAIO.com.base.api.progression.conditions.CConditions;
import scripts.CombatAIO.com.base.api.progression.moves.CActions;
import scripts.CombatAIO.com.base.main.Dispatcher;

public class ProgressionGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList<CProgressionMove> list;

	private DefaultListModel<CProgressionMove> list_model = new DefaultListModel<CProgressionMove>();

	private final JComboBox<CConditions> combo_box_condition;
	private final JComboBox<CActions> combo_box_change;

	public ProgressionGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 626, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 590, 210);
		contentPane.add(scrollPane);

		list = new JList<CProgressionMove>();
		scrollPane.setViewportView(list);
		list.setModel(list_model);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CProgressionFactory factory = new CProgressionFactory();
				CProgressionCondition con = ((CConditions) combo_box_condition
						.getSelectedItem()).make();
				if (con == null) {
					JOptionPane
							.showMessageDialog(
									null,
									"You failed to enter a condition correctly, creation canceled. Please try again");
					return;
				}
				factory.setCondition(con);
				CProgressionAction action = ((CActions) combo_box_change
						.getSelectedItem()).make();
				if (action == null) {
					JOptionPane
							.showMessageDialog(null,
									"You failed to enter an action correctly, creation canceled. Please try again");
					return;
				}
				factory.setAction(action);
				Dispatcher.get().getProgressionHandler()
						.addProgressionMove(factory.make());
				updateList();
			}
		});
		btnAdd.setBounds(313, 282, 89, 23);
		contentPane.add(btnAdd);

		// TODO CActions.values()
		combo_box_change = new JComboBox<CActions>(CActions.values());
		combo_box_change.setBounds(445, 248, 155, 23);
		contentPane.add(combo_box_change);

		// TODO CConditions.values()
		combo_box_condition = new JComboBox<CConditions>(CConditions.values());
		combo_box_condition.setBounds(267, 248, 155, 23);
		contentPane.add(combo_box_condition);

		JLabel lblChange = new JLabel("Change");
		lblChange.setBounds(445, 231, 116, 14);
		contentPane.add(lblChange);

		JLabel lblCondition = new JLabel("Condition");
		lblCondition.setBounds(267, 231, 146, 14);
		contentPane.add(lblCondition);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dispatcher.get().getProgressionHandler().save();
			}
		});
		btnSave.setBounds(121, 282, 89, 23);
		contentPane.add(btnSave);

		// TODO
		JComboBox<String> combo_box_profiles = new JComboBox<String>();
		combo_box_profiles.setBounds(10, 248, 200, 23);
		contentPane.add(combo_box_profiles);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dispatcher.get().getProgressionHandler().load();
				updateList();
			}
		});
		btnLoad.setBounds(10, 282, 89, 23);
		contentPane.add(btnLoad);

		JLabel lblProfiles = new JLabel("Profiles");
		lblProfiles.setBounds(10, 231, 146, 14);
		contentPane.add(lblProfiles);

		JButton btnDone = new JButton("Done");
		btnDone.setBounds(511, 282, 89, 23);
		contentPane.add(btnDone);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(412, 282, 89, 23);
		contentPane.add(btnRemove);

		updateList();
	}

	private void updateList() {
		this.list_model.removeAllElements();
		for (CProgressionMove x : Dispatcher.get().getProgressionHandler()
				.getAllMoves())
			list_model.addElement(x);
	}
}
