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
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.CombatAIO.com.base.api.types.enums.MovementType;
import scripts.CombatAIO.com.base.api.walking.WalkingManager;
import scripts.CombatAIO.com.base.api.walking.custom.actions.DActionMaker;
import scripts.CombatAIO.com.base.api.walking.custom.conditions.DConditionMaker;
import scripts.CombatAIO.com.base.api.walking.custom.types.CustomMovement;
import scripts.CombatAIO.com.base.api.walking.custom.types.DAction;
import scripts.CombatAIO.com.base.api.walking.custom.types.DCondition;
import scripts.CombatAIO.com.base.api.walking.custom.types.DFullHolder;
import scripts.CombatAIO.com.base.api.walking.custom.types.DHolder;

public class CustomWalkingGUI extends JFrame {

	private static final long serialVersionUID = -6763519473522357384L;

	private JPanel contentPane;
	private MovementType type;

	private JComboBox<MovementType> combo_box_movement_type;
	private JSpinner spinner;

	private RSTile center_tile;
	private boolean changed = false;

	public CustomWalkingGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 604, 186);
		contentPane.add(scrollPane);

		final DefaultListModel<DHolder> complete_list_model = new DefaultListModel<DHolder>();
		final JList<DHolder> list_complete = new JList<DHolder>();
		scrollPane.setViewportView(list_complete);
		list_complete.setModel(complete_list_model);

		JLabel lblComplete = new JLabel("Complete");
		lblComplete.setBounds(10, 11, 70, 14);
		contentPane.add(lblComplete);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 256, 290, 153);
		contentPane.add(scrollPane_1);

		final DefaultListModel<DAction> action_list_model = new DefaultListModel<DAction>();
		final JList<DAction> list_actions = new JList<DAction>();
		scrollPane_1.setViewportView(list_actions);
		list_actions.setModel(action_list_model);

		JLabel lblNewLabel = new JLabel("Actions");
		lblNewLabel.setBounds(10, 231, 46, 14);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(324, 256, 290, 151);
		contentPane.add(scrollPane_2);

		final DefaultListModel<DCondition> condition_list_model = new DefaultListModel<DCondition>();
		final JList<DCondition> list_conditions = new JList<DCondition>();
		scrollPane_2.setViewportView(list_conditions);
		list_conditions.setModel(condition_list_model);

		JLabel lblConditions = new JLabel("Conditions");
		lblConditions.setBounds(324, 231, 77, 14);
		contentPane.add(lblConditions);
		// TODO
		final JComboBox<DConditionMaker> comboBoxConditions = new JComboBox<DConditionMaker>(
				DConditionMaker.values());
		comboBoxConditions.setBounds(324, 421, 191, 20);
		contentPane.add(comboBoxConditions);
		// TODO
		final JComboBox<DActionMaker> comboBoxActions = new JComboBox<DActionMaker>(
				DActionMaker.values());
		comboBoxActions.setBounds(10, 421, 191, 20);
		contentPane.add(comboBoxActions);

		JButton btnAddAction = new JButton("Add");
		btnAddAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DAction ac = ((DActionMaker) comboBoxActions.getSelectedItem())
						.make();
				action_list_model.addElement(ac);
			}
		});
		btnAddAction.setBounds(211, 420, 89, 23);
		contentPane.add(btnAddAction);

		JButton btnAddCondition = new JButton("Add");
		btnAddCondition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DCondition ac = ((DConditionMaker) comboBoxConditions
							.getSelectedItem()).make();
					condition_list_model.addElement(ac);
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		});
		btnAddCondition.setBounds(525, 420, 89, 23);
		contentPane.add(btnAddCondition);

		JButton btnAddLine = new JButton("Add Line");
		btnAddLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DAction[] actions = new DAction[action_list_model.size()];
					action_list_model.copyInto(actions);

					DCondition[] conditions = new DCondition[condition_list_model
							.size()];
					condition_list_model.copyInto(conditions);
					DHolder full = new DHolder(conditions, actions);
					complete_list_model.addElement(full);
					condition_list_model.clear();
					action_list_model.clear();
					changed = true;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddLine.setBounds(10, 490, 89, 23);
		contentPane.add(btnAddLine);

		JButton btnSave = new JButton("Done");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (center_tile == null) {
						JOptionPane.showMessageDialog(null,
								"You must set the center tile");
						return;
					}
					if (changed) {
						String name = JOptionPane
								.showInputDialog("Enter name for this custom action");
						if (name == null) {
							JOptionPane.showMessageDialog(null,
									"You must enter a name");
							return;
						}
						DHolder[] holders = new DHolder[complete_list_model
								.size()];
						complete_list_model.copyInto(holders);
						DFullHolder dfh = new DFullHolder(holders);

						type = (MovementType) combo_box_movement_type
								.getSelectedItem();
						String rad = spinner.getValue().toString();
						WalkingManager.addMovement(type, dfh, center_tile,
								name, rad);
					}
					setVisible(false);
					dispose();
				} catch (Exception r) {
					r.printStackTrace();
				}
			}
		});
		btnSave.setBounds(525, 517, 89, 23);
		contentPane.add(btnSave);

		JButton btnNewButton = new JButton("Remove");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list_actions.getSelectedIndex();
				if (index == -1)
					return;
				action_list_model.remove(index);
			}
		});
		btnNewButton.setBounds(211, 446, 89, 23);
		contentPane.add(btnNewButton);

		JButton button = new JButton("Remove");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list_conditions.getSelectedIndex();
				if (index == -1)
					return;
				condition_list_model.remove(index);
			}
		});
		button.setBounds(525, 446, 89, 23);
		contentPane.add(button);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list_complete.getSelectedIndex();
				if (index == -1)
					return;
				complete_list_model.remove(index);
				changed = true;
			}
		});
		btnRemove.setBounds(525, 222, 89, 23);
		contentPane.add(btnRemove);
		// TODO
		combo_box_movement_type = new JComboBox<MovementType>(
				MovementType.values());
		combo_box_movement_type.setBounds(385, 518, 130, 20);
		contentPane.add(combo_box_movement_type);

		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setBounds(521, 494, 46, 14);
		contentPane.add(lblRadius);

		spinner = new JSpinner();
		spinner.setBounds(577, 491, 37, 20);
		contentPane.add(spinner);
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				changed = true;
			}
		});
		// TODO
		final JComboBox<String> combo_box_movements = new JComboBox<String>(
				WalkingManager.getAllNames());
		combo_box_movements.setBounds(10, 518, 130, 20);
		contentPane.add(combo_box_movements);

		final JLabel lblCenterTile = new JLabel("Center tile: ");
		lblCenterTile.setBounds(379, 494, 136, 14);
		contentPane.add(lblCenterTile);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = (String) combo_box_movements.getSelectedItem();
				if (name == null)
					return;
				CustomMovement x = WalkingManager.getMovementForName(name);
				if (x == null)
					return;
				complete_list_model.clear();
				DFullHolder holder = x.getFullHolder();
				for (DHolder xy : holder.getHolders())
					complete_list_model.addElement(xy);
				spinner.setValue(x.getRadius());
				combo_box_movement_type.setSelectedItem(x.getMovementType());
				center_tile = x.getCenterTile();
				lblCenterTile.setText("Center tile: " + center_tile);
			}
		});
		btnLoad.setBounds(150, 517, 89, 23);
		contentPane.add(btnLoad);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				center_tile = Player.getPosition();
				lblCenterTile.setText("Center tile: " + center_tile);
			}
		});
		btnSet.setBounds(281, 490, 89, 23);
		contentPane.add(btnSet);
	}
}
