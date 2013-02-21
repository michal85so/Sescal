package pl.policht.sescal.main;

import java.io.Serializable;

public class ListObject implements Serializable{
	private String name;
	private String firEx;
	private String secEx;
	private String thiEx;
	private int avr;
	private int ectsIm;
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setFirEx(String firEx){
		this.firEx = firEx;
	}
	public String getFirEx(){
		return firEx;
	}
	public void setSecEx(String secEx){
		this.secEx = secEx;
	}
	public String getSecEx(){
		return secEx;
	}
	public void setThiEx(String thiEx){
		this.thiEx = thiEx;
	}
	public String getThiEx(){
		return thiEx;
	}
	public void setAvr(int avr){
		this.avr = avr;
	}
	public int getAvr(){
		return avr;
	}
	public void setEctsIm(int ectsIm){
		this.ectsIm = ectsIm;
	}
	public int getEctsIm(){
		return ectsIm;
	}
	
	public String toString(){
		return ("nazwa: " + name + "\npierwszy egzamin: " + firEx + "\ndrugi egzamin: " + secEx);
	}
}
