package tfvis;

public class Debugs {

	private static boolean debugMode=true;
	
	public static void probe(String text){
		
		if(debugMode==false){return;}
		System.out.println(text);
	}
	
	
}
