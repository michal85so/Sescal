package pl.policht.sescal.main;

import javax.swing.JMenuItem;

public class Factory {
	private Factory(){}
	
	public static JMenuItem newJMenuItem(String name){
		return new JMenuItem(name);
	}
}
