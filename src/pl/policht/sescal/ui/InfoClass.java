package pl.policht.sescal.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InfoClass extends JFrame{
	JPanel pane;
	public InfoClass(){
		setSize(240, 170);
		setTitle("Ostrzezenie");
		
		pane = new JPanel();
		JButton butYes = new JButton("Tak");
		JButton butNo = new JButton("Nie");
		pane.add(butYes);
		pane.add(butNo);
		
		add(pane);
		setVisible(true);
	}
}
