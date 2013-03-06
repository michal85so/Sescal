package pl.policht.sescal.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private Properties mySettings;

	public Configuration() {
		mySettings = new Properties();
	}

	public void setThemeSettings(String nameOfTheme) {
		mySettings.put("theme", nameOfTheme);
	}

	public String getThemeSettings() {
		String sth = (String) mySettings.getProperty("theme");
		return sth;
	}

	public void saveSettingsFile() {
		try {
			FileOutputStream out = new FileOutputStream("sescal.cfg");
			mySettings.store(out, "Ustawienia aplikacji Sescal");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadSettingsFile() {
		try {
			FileInputStream in = new FileInputStream("sescal.cfg");
			mySettings.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
