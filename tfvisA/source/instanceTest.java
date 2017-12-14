
import java.util.*;

public class instanceTest{
	public static void main(String[] args) {
		
		List ary = new ArrayList();
		
		Print print = new Print();
		try{			
		print.set("aaa");
		print.str = "bbb";
		System.out.println(print.str);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println(e);
		}
		
	}
	
	
}