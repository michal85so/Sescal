package pl.policht.sescal.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pl.policht.sescal.main.Factory;

public class MainFrame extends JFrame {
	private static MainFrame mf;
	
	private MainFrame() {
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
			muSubmenu.add(Factory.newJMenuItem(infos.getName()));
		muSubmenu.getItem(1).setActionCommand(themes[1].getClassName().toString());
		JMenu menuPomoc = new JMenu("Pomoc");
		
		menuBar.add(menuPlik);
		menuBar.add(menuUstawienia);
		menuBar.add(menuPomoc);
		
		setJMenuBar(menuBar);
		muSubmenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(e.getActionCommand());
					System.out.println(e.getSource());
					SwingUtilities.updateComponentTreeUI(MainFrame.this);
				} catch (Exception ex) {
				}
			}
		});
	}

	public static MainFrame getMainFrame() {
		if (mf == null)
			mf = new MainFrame();
		return mf;
	}
}
