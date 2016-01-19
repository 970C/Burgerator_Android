//package burgeratorBackend;

import java.util.*;

public class BurgerOptions {
	
	String[] cheese = {"American", "Cheddar", "Pepperjack"};
	String[] burgToBun = {"Just Right", "Needs More Bun", "Just Right"};
	String[] prep = {"Perfect", "Not Perfect"};
	int c;
	int btb;
	int p;

	public BurgerOptions(){
		
	}
	public BurgerOptions(int _c, int _btb, int _p){
		this.c = _c;
		this.btb = _btb;
		this.p = _p;
	}
	public String[] getOpts(){
		String[] a = {cheese[c], burgToBun[btb], prep[p]};
		return a;
	}
	
	public String[] getCheeses(){
		return cheese;
	}
	public String[] getBurgToBun(){
		return cheese;
	}
	public String[] getPrep(){
		return cheese;
	}
	
	public void setCheese(int _c){
		this.c = _c;
	}
	public void setBtB(int _c){
		this.c = _c;
	}
	public void setPrep(int _c){
		this.c = _c;
	}
	
	public String[] getOptionData(){
		String[] a = {Integer.toString(c), cheese[c], Integer.toString(btb), burgToBun[btb],Integer.toString(p), prep[p]};
		return a;
	}
}
