package pl.policht.sescal.main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import pl.policht.sescal.ui.InfoClass;
import pl.policht.sescal.ui.MainFrame;

public class Sescal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MainFrame mf = MainFrame.getMainFrame();
				mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mf.setVisible(true);
				mf.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent eve){
						new InfoClass();
					}
				});
			}
		});

	}

}
