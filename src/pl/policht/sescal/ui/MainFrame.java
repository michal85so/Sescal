package pl.policht.sescal.ui;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
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

import pl.policht.sescal.main.ListObject;


public class MainFrame extends JFrame {
	private static MainFrame mf;
	
	private JPanel layNorth;
	private JPanel layWest;
	private JPanel layCenter;
	private JPanel laySouth;
	
	private List myList;
	private JTextField myJTextF;
	private JTextField firExam;
	private JTextField secExam;
	private JButton butNew;
	private JButton butAdd;
	private JButton butRem;
	private JButton butSav;
	private JLabel labMyJTestF;
	private JLabel labFirExam;
	private JLabel labSecExam;
	
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
		layCenter.setLayout(new GridLayout(3, 2));
		
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		int x = dim.width;
		int y = dim.height;

		setSize(x / 2, y / 2);
		setTitle("Sescal");

		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuPlik = new JMenu("Plik");
		JMenu menuUstawienia = new JMenu("Ustawienia");
		JMenu muSubmenu = new JMenu("Style");
		menuUstawienia.add(muSubmenu);
		UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo infos : themes)
			muSubmenu.add(createJMenuItem(infos.getName(), infos.getClassName()));
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
					if (tempstr.equals(ol.getName())){
						myJTextF.setText(ol.getName());
						firExam.setText(ol.getFirEx());
						secExam.setText(ol.getSecEx());
					}
			}
		});
		myJTextF = new JTextField(40);
		labMyJTestF = new JLabel("Nazwa Przedmiotu", SwingConstants.RIGHT);
		firExam = new JTextField(40);
		labFirExam = new JLabel("Pierwszy examin", SwingConstants.RIGHT);
		secExam = new JTextField(40);
		labSecExam = new JLabel("Drugi examin", JLabel.RIGHT);
		
		butNew = new JButton("Nowy");
		butNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				myJTextF.setText(null);
				firExam.setText(null);
				secExam.setText(null);
			}
		});
		butAdd = new JButton("Dodaj");
		butAdd.addActionListener(new AddButtonListener());
		butRem = new JButton("Usun");
		butRem.addActionListener(new RemButtonListener());
		butSav = new JButton("Zapisz");
		butSav.addActionListener(new SavButtonListener());
		
		layWest.add(myList);
		add(layWest, BorderLayout.WEST);
		layNorth.add(butNew);
		layNorth.add(butAdd);
		layNorth.add(butRem);
		layNorth.add(butSav);
		add(layNorth, BorderLayout.NORTH);
		layCenter.add(myJTextF);
		layCenter.add(labMyJTestF);
		layCenter.add(firExam);
		layCenter.add(labFirExam);
		layCenter.add(secExam);
		layCenter.add(labSecExam);
		add(layCenter, BorderLayout.CENTER);
		
		objectsList = getListFromFile();
		for (ListObject e : objectsList)
			myList.add(e.getName());
	}

	public static MainFrame getMainFrame() {
		if (mf == null)
			mf = new MainFrame();
		return mf;
	}
	
	private JMenuItem createJMenuItem(String name, String actionCommand){
		JMenuItem jmi = new JMenuItem(name);
		jmi.setActionCommand(actionCommand);
		jmi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					UIManager.setLookAndFeel(arg0.getActionCommand());
					SwingUtilities.updateComponentTreeUI(MainFrame.this);
				}catch(Exception ex){}
				
			}
		});
		return jmi;
	}
	
	private ArrayList<ListObject> getListFromFile(){
		file = new File("listobject.dat");
		ArrayList<ListObject> tempObjectsList = new ArrayList<ListObject>();
		try{
			if (!file.exists()) file.createNewFile();
			ois = new ObjectInputStream(new FileInputStream(file));
			ListObject[] tempList = (ListObject[]) ois.readObject();
			ois.close();
			for (ListObject e : tempList)
				tempObjectsList.add(e);
		}catch(Exception e){}
		return tempObjectsList;
	}
	
	private void sendListToFile(ArrayList<ListObject> tempObjectsList){
		try{
			file = new File("listobject.dat");
			oos = new ObjectOutputStream(new FileOutputStream(file));
			ListObject[] tempList = new ListObject[tempObjectsList.size()];
			
			for (int i = 0; i < tempObjectsList.size(); i++ )
				tempList[i] = (ListObject) tempObjectsList.get(i);
			oos.writeObject(tempList);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class AddButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (myJTextF == null) return;
			ListObject newLObject = new ListObject();
			newLObject.setName(myJTextF.getText());
			newLObject.setFirEx(firExam.getText());
			newLObject.setSecEx(secExam.getText());
			objectsList.add(newLObject);
			myList.add(myJTextF.getText());
		}
	}
	
	private class RemButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myList.getSelectedItem() == null) return;
			String itemToRemove = myList.getSelectedItem();
			myList.remove(myList.getSelectedItem());
			Iterator<ListObject> it = objectsList.iterator();
			while (it.hasNext()){
				ListObject obj = it.next();
				obj.getName();
				if (itemToRemove.equals(obj.getName()))
					it.remove();
			}
		}
	}
	
	private class SavButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myList.getSelectedItem() != null){
				int objIndex = 0;
				String objToMod = myList.getSelectedItem();
				for (ListObject lo : objectsList){
					if (objToMod.equals(lo.getName()))
						objIndex = objectsList.indexOf(lo);		
				}
				ListObject modLObject = new ListObject();
				modLObject.setName(myJTextF.getText());
				modLObject.setFirEx(firExam.getText());
				modLObject.setSecEx(secExam.getText());
				objectsList.set(objIndex, modLObject);
			}
			sendListToFile(objectsList);
		}
	}
}
