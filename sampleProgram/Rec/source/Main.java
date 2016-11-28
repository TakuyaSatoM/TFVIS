


public class Main {


	
	public static void main(String[] args) {
		
		rec(0);
	}
	
	
	static void rec(int y){
		
		if(y>1){return;}
		
		rec(y+1);
		rec(y+1);
	}

}