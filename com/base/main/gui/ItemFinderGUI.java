package scripts.CombatAIO.com.base.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import scripts.CombatAIO.com.base.main.gui.elements.SearchBox;
import scripts.CombatAIO.com.base.main.utils.DumpItem;
import scripts.CombatAIO.com.base.main.utils.Items07;

public class ItemFinderGUI extends JFrame {

	private JPanel contentPane;

	private SearchBox<DumpItem> combo_box_items;

	public ItemFinderGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 169, 112);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DumpItem[] items = Items07.getDumpItemData();
		combo_box_items = new SearchBox<DumpItem>("Items");
		combo_box_items.setBounds(10, 11, 130, 20);
		contentPane.add(combo_box_items);
		combo_box_items.setSearchList(true,
				new ArrayList<>(Arrays.asList(items)));

		JButton btnNewButton = new JButton("Done");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(51, 42, 89, 23);
		contentPane.add(btnNewButton);
	}

	public DumpItem get() {
		DumpItem x = (DumpItem) combo_box_items.getSelectedItem();
		dispose();
		return x;
	}
}
