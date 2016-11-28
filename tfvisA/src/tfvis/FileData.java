package tfvis;

import java.util.ArrayList;

//ファイルが持つクラスの情報
class FileData{
	
	ArrayList<StrClass> m_Classes;
	
	FileData(){
		m_Classes = new ArrayList<StrClass>();
	}
	
	public int getClassNum(){
		return m_Classes.size();
	}
	
	public String getClassName(){
		return m_Classes.get(0).getName();
	}
	
	public String getClassName(int i){
		return m_Classes.get(i).getName();
	}
	
	public ArrayList<StrClass> getClassData(){
		return m_Classes;
	}
	
	public StrClass getClassData(int i){
		return m_Classes.get(i);
	}
	
	public void add(StrClass classData){
		m_Classes.add(classData);
	}
	
	public void setSubClassData(StrClass classData){
		(m_Classes.get(m_Classes.size()-1)).setSubClass(classData);
	}
	
	public int getSubClassNum(){
		int count=0;
		for(StrClass classData:m_Classes){
			count +=classData.getSubClassNum();
		}
		return count;
	}
}