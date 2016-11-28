package tfvis;

import java.util.ArrayList;

public class AnalysisData {
	private ArrayList<Integer> idList;
	private ArrayList<String>  stateList;
	
	public AnalysisData() {
		idList = new ArrayList<Integer>();
		stateList = new ArrayList<String>();
	}
	
	public void add(int id, String statement) {
		idList.add(id);
		stateList.add(statement);
	}
	
	public void add(int loc, int id, String statement) {
		idList.add(loc, id);
		stateList.add(loc, statement);
	}
	
	public void addAll(AnalysisData data) {
		idList.addAll(data.getId());
		stateList.addAll(data.getState());
	}
	
	public void addAll(int loc, AnalysisData data) {
		idList.addAll(loc, data.getId());
		stateList.addAll(loc, data.getState());
	}
	
	public void clear() {
		idList.clear();
		stateList.clear();
	}
	
	public int size() {
		return idList.size();
	}
	
	public boolean isEmpty() {
		return (idList.isEmpty() && stateList.isEmpty());
	}
	
	public void remove(int index){
		idList.remove(index);
		stateList.remove(index);
	}
	
	/*
	 * Getter
	 */
	public ArrayList<Integer> getId() {
		return idList;
	}
	
	public int getId(int loc) {
		return idList.get(loc);
	}
	
	public ArrayList<String> getState() {
		return stateList;
	}
	
	public String getState(int loc) {
		return stateList.get(loc);
	}
	
	public boolean contains(int event){
		return idList.contains(event);
	}
	
	public int indexOf(int event){
		return idList.indexOf(event);
	}
}
