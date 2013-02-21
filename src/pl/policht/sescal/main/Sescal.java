package pl.policht.sescal.main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pl.policht.sescal.ui.InfoClass;
import pl.policht.sescal.ui.MainFrame;

public class Sescal {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				final MainFrame mf = MainFrame.getMainFrame();
				//mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mf.setVisible(true);
				mf.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent eve){
						int selection = JOptionPane.showConfirmDialog(null, "Czy napewno chcesz wyjść z aplikacji Sescal?", "Uwaga", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (selection == JOptionPane.OK_OPTION)
							System.exit(0);
						else
							mf.setVisible(true);
					}
				});
			}
		});

	}

}
