package pl.policht.sescal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import pl.policht.sescal.main.Configuration;
import pl.policht.sescal.main.Factory;
import pl.policht.sescal.main.ListObject;

public class MainFrame extends JFrame {
	private static MainFrame mf;
	private Configuration myConf = new Configuration();

	private JPanel layNorth;
	private JPanel layWest;
	private JPanel layCenter;
	private JPanel laySouth;

	private List myList;
	private JTextField myJTextF;
	private JTextField firExam;
	private JTextField secExam;
	private JTextField thiExam;
	private JButton butNew;
	private JButton butAdd;
	private JButton butRem;
	private JButton butSav;
	private JLabel labMyJTestF;
	private JLabel labFirExam;
	private JLabel labSecExam;
	private JLabel labThiExam;
	private JLabel labInfo;
	private JComboBox comAvr;
	private JComboBox comEcts;

	private ArrayList<ListObject> objectsList;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private File file;

	private MainFrame() {
		setLayout(new BorderLayout());
		layNorth = new JPanel();
		layWest = new JPanel();
		layCenter = new JPanel();
		laySouth = new JPanel();
		layCenter.setLayout(new GridLayout(5, 2));

		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		int x = dim.width;
		int y = dim.height;

		setSize(x / 2, y / 2);
		setTitle("Sescal");

		myConf.loadSettingsFile();
		String theme = myConf.getThemeSettings();
		try {
			UIManager.setLookAndFeel(theme);
			SwingUtilities.updateComponentTreeUI(MainFrame.this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JMenuBar menuBar = new JMenuBar();

		JMenu menuPlik = new JMenu("Plik");
		JMenu menuUstawienia = new JMenu("Ustawienia");
		JMenu muSubmenu = new JMenu("Style");
		menuUstawienia.add(muSubmenu);
		UIManager.LookAndFeelInfo[] themes = UIManager
				.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo infos : themes)
			muSubmenu
					.add(createJMenuItem(infos.getName(), infos.getClassName()));
		JMenu menuPomoc = new JMenu("Pomoc");

		menuBar.add(menuPlik);
		menuBar.add(menuUstawienia);
		menuBar.add(menuPomoc);

		setJMenuBar(menuBar);

		myList = new List(15);
		myList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tempstr = myList.getSelectedItem();
				for (ListObject ol : objectsList)
					if (tempstr.equals(ol.getName())) {
						myJTextF.setText(ol.getName());
						firExam.setText(ol.getFirEx());
						secExam.setText(ol.getSecEx());
						thiExam.setText(ol.getThiEx());
						labInfo.setText(Factory.saveAfterEditInfo);
						comAvr.setSelectedIndex(ol.getAvr());
						comEcts.setSelectedIndex(ol.getEctsIm());
					}
			}
		});

		myJTextF = new JTextField(40);
		labMyJTestF = new JLabel("Nazwa Przedmiotu", SwingConstants.RIGHT);
		firExam = new JTextField(40);
		labFirExam = new JLabel("Pierwszy termin examinu", SwingConstants.RIGHT);
		secExam = new JTextField(40);
		labSecExam = new JLabel("Drugi drugi termin examinu", JLabel.RIGHT);
		thiExam = new JTextField(40);
		labThiExam = new JLabel("Examin w sesji poprawkowej", JLabel.RIGHT);

		butNew = new JButton("Nowy");
		butNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myJTextF.setText(null);
				firExam.setText(null);
				secExam.setText(null);
				thiExam.setText(null);
				comAvr.setSelectedIndex(1);
				comEcts.setSelectedIndex(1);
			}
		});
		butAdd = new JButton("Dodaj");
		butAdd.addActionListener(new AddButtonListener());
		butRem = new JButton("Usun");
		butRem.addActionListener(new RemButtonListener());
		butSav = new JButton("Zapisz");
		butSav.addActionListener(new SavButtonListener());

		labInfo = new JLabel("Witaj w Sescal :)");

		layWest.add(myList);
		createSwingBorder(layWest, "Lista przedmiotów");
		add(layWest, BorderLayout.WEST);
		layNorth.add(butNew);
		layNorth.add(butAdd);
		layNorth.add(butRem);
		layNorth.add(butSav);
		createSwingBorder(layNorth, "Menu");
		add(layNorth, BorderLayout.NORTH);
		layCenter.add(myJTextF);
		layCenter.add(labMyJTestF);
		layCenter.add(firExam);
		layCenter.add(labFirExam);
		layCenter.add(secExam);
		layCenter.add(labSecExam);
		layCenter.add(thiExam);
		layCenter.add(labThiExam);
		JPanel comAvrPan = new JPanel();
		comAvrPan.setLayout(new GridLayout(1, 2));
		JPanel comAvrPanIn = new JPanel();
		JPanel comEctsIn = new JPanel();
		comAvr = new JComboBox();
		comAvr.setEditable(false);
		comAvr.addItem("2.0");
		comAvr.addItem("2.5");
		comAvr.addItem("3.0");
		comAvr.addItem("3.5");
		comAvr.addItem("4.0");
		comAvr.addItem("4.5");
		comAvr.addItem("5.0");
		createSwingBorder(comAvrPanIn, "Ocena");
		comAvrPanIn.add(comAvr);
		comEcts = new JComboBox();
		comEcts.setEditable(false);
		comEcts.addItem("1");
		comEcts.addItem("2");
		comEcts.addItem("3");
		comEcts.addItem("4");
		comEcts.addItem("5");
		comEcts.addItem("6");
		comEcts.addItem("7");
		createSwingBorder(comEctsIn, "Punkty ECTS");
		comEctsIn.add(comEcts);
		comAvrPan.add(comAvrPanIn);
		comAvrPan.add(comEctsIn);
		layCenter.add(comAvrPan);
		createSwingBorder(layCenter, "Opis przedmiotu");
		add(layCenter, BorderLayout.CENTER);
		laySouth.add(labInfo);
		createSwingBorder(laySouth, "Informacja");
		add(laySouth, BorderLayout.SOUTH);
		pack();

		objectsList = getListFromFile();
		for (ListObject e : objectsList)
			myList.add(e.getName());
	}

	public static MainFrame getMainFrame() {
		if (mf == null)
			mf = new MainFrame();
		return mf;
	}

	private JMenuItem createJMenuItem(String name, String actionCommand) {
		JMenuItem jmi = new JMenuItem(name);
		jmi.setActionCommand(actionCommand);
		jmi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UIManager.setLookAndFeel(arg0.getActionCommand());
					SwingUtilities.updateComponentTreeUI(MainFrame.this);
					myConf.setThemeSettings(arg0.getActionCommand());
					myConf.saveSettingsFile();
				} catch (Exception ex) {
				}

			}
		});
		return jmi;
	}

	private ArrayList<ListObject> getListFromFile() {
		file = new File("listobject.dat");
		ArrayList<ListObject> tempObjectsList = new ArrayList<ListObject>();
		try {
			if (!file.exists())
				file.createNewFile();
			ois = new ObjectInputStream(new FileInputStream(file));
			ListObject[] tempList = (ListObject[]) ois.readObject();
			ois.close();
			for (ListObject e : tempList)
				tempObjectsList.add(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempObjectsList;
	}

	private void sendListToFile(ArrayList<ListObject> tempObjectsList) {
		try {
			file = new File("listobject.dat");
			oos = new ObjectOutputStream(new FileOutputStream(file));
			ListObject[] tempList = new ListObject[tempObjectsList.size()];

			for (int i = 0; i < tempObjectsList.size(); i++)
				tempList[i] = (ListObject) tempObjectsList.get(i);
			oos.writeObject(tempList);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class AddButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (myJTextF == null)
				return;
			for (ListObject lo : objectsList)
				if (myJTextF.getText().equals(lo.getName()))
					return;
			ListObject newLObject = new ListObject();
			newLObject.setName(myJTextF.getText());
			newLObject.setFirEx(firExam.getText());
			newLObject.setSecEx(secExam.getText());
			newLObject.setThiEx(thiExam.getText());
			newLObject.setAvr(comAvr.getSelectedIndex());
			newLObject.setEctsIm(comEcts.getSelectedIndex());
			objectsList.add(newLObject);
			myList.add(myJTextF.getText());
			sendListToFile(objectsList);
		}
	}

	private class RemButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myList.getSelectedItem() == null)
				return;
			String itemToRemove = myList.getSelectedItem();
			myList.remove(myList.getSelectedItem());
			Iterator<ListObject> it = objectsList.iterator();
			while (it.hasNext()) {
				ListObject obj = it.next();
				obj.getName();
				if (itemToRemove.equals(obj.getName()))
					it.remove();
			}
		}
	}

	private class SavButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myList.getSelectedItem() != null) {
				int objIndex = 0;
				String objToMod = myList.getSelectedItem();
				for (ListObject lo : objectsList) {
					if (objToMod.equals(lo.getName()))
						objIndex = objectsList.indexOf(lo);
				}
				ListObject modLObject = new ListObject();
				modLObject.setName(myJTextF.getText());
				modLObject.setFirEx(firExam.getText());
				modLObject.setSecEx(secExam.getText());
				modLObject.setThiEx(thiExam.getText());
				modLObject.setAvr(comAvr.getSelectedIndex());
				modLObject.setEctsIm(comEcts.getSelectedIndex());
				objectsList.set(objIndex, modLObject);
			}
			sendListToFile(objectsList);
		}
	}

	private void createSwingBorder(JPanel panel, String text) {
		// Border newBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border newTitledBorder = BorderFactory.createTitledBorder(text);
		panel.setBorder(newTitledBorder);
	}
}
