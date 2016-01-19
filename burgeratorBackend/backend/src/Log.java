//package burgeratorBackend;

import java.util.*;

public class Log {
	ArrayList<String> log;
	private static Log logInstance = new Log();
	
	private Log(){
		log = new ArrayList<>();
	}
	public static Log getInstance (){
		return logInstance;
	}
	
	public void addLog(String _log){
		this.log.add(_log);
	}
	public void logConsoleDump(){
		
		Iterator itr = log.iterator();
		
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		
	}
}
