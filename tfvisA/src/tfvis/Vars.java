package tfvis;


public class Vars implements tfvisConstants{

	String m_State;
	String m_Name;
	boolean m_Array;
	
	
	Vars(String state,String name){
		m_State=state;
		m_Name=name;
		m_Array=false;
		
	}
	Vars(String state,String name,boolean array){
		m_State=state;
		m_Name=name;
		m_Array=array;
		
	}	
	
	public int getUpdateTFVISID(){
		
		int tmp=0;
		if(m_Array==true){tmp=ArrayMode;}
		
		if(m_State.equals("int")){return Ev_IntUpdate+tmp;}
		if(m_State.equals("double")){return Ev_DoubleUpdate+tmp;}
		if(m_State.equals("String")){return Ev_StringUpdate+tmp;}
		
		return -1;		
		
	}
	
	static boolean checkVarsDec(String state){
		
		if(state.equals("int")){return true;}
		if(state.equals("double")){return true;}
		if(state.equals("String")){return true;}
		
		return false;
	}	
	
	
	
}
