package scripts.CombatAIO.com.base.api.general.walking.custom.background.bgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.tribot.util.Util;

import scripts.CombatAIO.com.base.api.general.walking.custom.background.DAction;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.DCondition;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.DFullHolder;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.DHolder;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.actions.DActionMaker;
import scripts.CombatAIO.com.base.api.general.walking.custom.background.conditions.DConditionMaker;


public class BMainGui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	private static final String PATH = Util.getAppDataDirectory()
			+ File.separator + "drennon_background" + File.separator + "NAME"
			+ ".dat";

	public BMainGui() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 568);
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
		scrollPane_1.setBounds(10, 279, 290, 153);
		contentPane.add(scrollPane_1);

		final DefaultListModel<DAction> action_list_model = new DefaultListModel<DAction>();
		final JList<DAction> list_actions = new JList<DAction>();
		scrollPane_1.setViewportView(list_actions);
		list_actions.setModel(action_list_model);

		JLabel lblNewLabel = new JLabel("Actions");
		lblNewLabel.setBounds(10, 254, 46, 14);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(324, 279, 290, 151);
		contentPane.add(scrollPane_2);

		final DefaultListModel<DCondition> condition_list_model = new DefaultListModel<DCondition>();
		final JList<DCondition> list_conditions = new JList<DCondition>();
		scrollPane_2.setViewportView(list_conditions);
		list_conditions.setModel(condition_list_model);

		JLabel lblConditions = new JLabel("Conditions");
		lblConditions.setBounds(324, 254, 77, 14);
		contentPane.add(lblConditions);
//TODO
		final JComboBox<DConditionMaker> comboBoxConditions = new JComboBox<DConditionMaker>(DConditionMaker.values());
		comboBoxConditions.setBounds(324, 443, 150, 20);
		contentPane.add(comboBoxConditions);
//TODO
		final JComboBox<DActionMaker> comboBoxActions = new JComboBox<DActionMaker>(DActionMaker.values());
		comboBoxActions.setBounds(10, 443, 150, 20);
		contentPane.add(comboBoxActions);

		JButton btnAddAction = new JButton("Add");
		btnAddAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DAction ac = (DAction) ((DActionMaker) comboBoxActions.getSelectedItem())
						.make();
				action_list_model.addElement(ac);
			}
		});
		btnAddAction.setBounds(170, 442, 130, 23);
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
		btnAddCondition.setBounds(484, 442, 130, 23);
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
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddLine.setBounds(10, 495, 89, 23);
		contentPane.add(btnAddLine);

		textField = new JTextField();
		textField.setBounds(464, 496, 150, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!new File(Util.getAppDataDirectory() + File.separator
						+ "drennon_background").exists())
					new File(Util.getAppDataDirectory() + File.separator
							+ "drennon_background").mkdirs();
				try {

					DHolder[] holders = new DHolder[complete_list_model.size()];
					complete_list_model.copyInto(holders);

					DFullHolder dfh = new DFullHolder(holders);

					FileOutputStream fos = new FileOutputStream(new File(PATH
							.replaceAll("NAME", textField.getText())));
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(dfh);
					fos.flush();
					fos.close();
					oos.flush();
					oos.close();
					complete_list_model.clear();
					action_list_model.clear();
					condition_list_model.clear();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnSave.setBounds(365, 495, 89, 23);
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
		btnNewButton.setBounds(170, 468, 130, 23);
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
		button.setBounds(484, 468, 130, 23);
		contentPane.add(button);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list_complete.getSelectedIndex();
				if (index == -1)
					return;
				complete_list_model.remove(index);
			}
		});
		btnRemove.setBounds(525, 222, 89, 23);
		contentPane.add(btnRemove);
	}
}
