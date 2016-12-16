package tfvis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import avis.Parser.Parser;

public class Program implements tfvisConstants{

	public ArrayList<Vars> m_Vars;
	public ArrayList<FileData> m_Files;
	
	public int getMethodID(String methodName, String className){
		
		for(FileData file:m_Files){
			for(StrClass index:file.getClassData()){
				int out;
				if( (out=index.getMethodID(methodName)) >= 0 && index.getName().equals(className)){
					return out; 
				}
				for(StrClass subClass:index.getSubClass() ){
					if( (out=subClass.getMethodID(methodName) ) >= 0 && subClass.getName().equals(className)){
						return out; 
					}
				}
			}
		}
		
		return -1;
	}
	
	public String getMainClassName(){
		
		for(FileData file:m_Files){
			for(StrClass index:file.getClassData()){
				if(index.doesHasMainMethod()){
					return index.getName();
				}
			}
		}
		
		return "";
	}
	
	public void setVar(Vars var){m_Vars.add(var);}

	void setMethodID(){
	
		int count=0;
		for(FileData file:m_Files){
			for(StrClass index:file.getClassData()){
				if(index.hasSubClass()){
					for(StrClass subClass:index.getSubClass()){
						if(subClass.doesHasMainMethod()){
							subClass.setMethodID(count);
							count+=subClass.getHasMethodNum();
						}
					}
				}
				if(index.doesHasMainMethod()){
					index.setMethodID(count);
					count+=index.getHasMethodNum();
				}
			}
		}
		for (FileData file : m_Files) {
			for (StrClass index : file.getClassData()) {
				for (StrClass subClass : index.getSubClass()) {
					if (!subClass.doesHasMainMethod()) {
						subClass.setMethodID(count);
						count += subClass.getHasMethodNum();
					}
				}
				if (!index.doesHasMainMethod()) {
					index.setMethodID(count);
					count += index.getHasMethodNum();
				}
			}
		}

	}
	
	/**
	 * int、double等の構造解析トークンIDから、それらの型の変数を更新するイベントの記号をとる
	 * @param id トークンID
	 * @param state トークンテキスト
	 * @param array 配列要素か
	 * @return
	 */
	public int trans_AvisIDtoTFVISID(int id,String state,boolean array){
		
		int arrayOffset=0;
		if(array){arrayOffset=ArrayMode;}
		
		if(id==Int){return  Ev_IntUpdate+arrayOffset;  }
		if(id==Double){return  Ev_DoubleUpdate+arrayOffset;  }
		if(state.equals("String")){return  Ev_StringUpdate+arrayOffset;  }		
		
		return -1;
	}
	
	/**
	 * 指定した条件に該当する変数を宣言しているか
	 * @param que 条件
	 * @return 失敗時-1
	 */
	public int checkVarsUpdate_TFVISID(VarsQue que){
	
		
		for(Vars var: m_Vars){
			
			if(var.m_Name.equals(que.m_Name)){
				return var.getUpdateTFVISID();
			}
		}
		
		
		return -1;
	}
	
	
	Program(String dir,String[] files){
		m_Vars=new ArrayList<Vars>();
		m_Files = new ArrayList<FileData>();
		 
		//解析データの抽出＆分割
		for(int i=0;i<files.length;i++){
			AnalysisData data = (new Parser(dir+files[i])).getAnalysisData();
			splitByClass(data);
		}
		setMethodID();
		
		for(FileData file: m_Files){
			for(StrClass index: file.getClassData()){
				for(StrClass subClass:index.getSubClass()){
					subClass.outputLineFile(this);
				}
				index.outputLineFile(this);	
			}
		}
		
	}
	
	/**
	 * 構造情報ファイルを出力
	 */
	void outputStr(){
	    File ssfile = new File(outDir+"SystemStr"+ Exten_Str);
	    //ファイルを初期化  
	    if (ssfile.exists()){ ssfile.delete();}	      		
	    try{
		  ssfile.createNewFile();
	    }catch(IOException e){System.out.println(e);}   	
		
	    int classCount=0;
		//mainクラスから出力
	    for(FileData file:m_Files){
	    	for(StrClass classData:file.getClassData()){
	    		for(StrClass subClassData:classData.getSubClass()){
	    			if(subClassData.doesHasMainMethod()){
		    			subClassData.setMyID(classCount);
		    			subClassData.outputStr(ssfile);
					
		    			classCount++;
		    		}
	    		}
	    		if(classData.doesHasMainMethod()){
	    			classData.setMyID(classCount);
	    			classData.outputStr(ssfile);
				
	    			classCount++;
	    		}
	    	}
	    }
	    
		//非mainクラスの出力
	    for(FileData file:m_Files){
	    	for(StrClass classData:file.getClassData()){
	    		for(StrClass subClassData:classData.getSubClass()){
	    			if(!subClassData.doesHasMainMethod()){
		    			subClassData.setMyID(classCount);
		    			subClassData.outputStr(ssfile);
					
		    			classCount++;
		    		}
	    		}
	    		if(!classData.doesHasMainMethod()){
	    			classData.setMyID(classCount);
	    			classData.outputStr(ssfile);
				
	    			classCount++;
	    		}
	    	}		
	    }
		
	}
	
	/**
	 * プローブファイルを出力
	 */
	void outputProbe(){
		
		try {
			// 非mainクラスの出力
			for (FileData file : m_Files) {

				File ssfile = new File(outDir + file.getClassName() + Exten_Probe);
				// ファイルを初期化
				if (ssfile.exists()) {
					ssfile.delete();
				}
				try {
					ssfile.createNewFile();
				} catch (IOException e) {
					System.out.println(e);
				}

				PrintWriter fout = new PrintWriter(new FileWriter(ssfile, true));

				for (int i = 0; i < file.getClassNum(); i++) {
					file.getClassData(i).fileOutputProbe(fout);
				}
				fout.close();
			}
		} catch (IOException e) {

		}
	}
	
	//解析データをクラスごとに分割、m_Classesに格納
	void splitByClass(AnalysisData data){
		AnalysisData splitData = new AnalysisData();
		AnalysisData subSplitData = new AnalysisData();
		ArrayList<StrClass> subClassList = new ArrayList<StrClass>();
		FileData file = new FileData();
		int classCount=0;
		
		for(int i=0; i<data.size(); i++){
			if(data.getId(i) == Ev_ClassStart){classCount++;}
			if(classCount > 1){
				subSplitData.add(data.getId(i), data.getState(i));
			}else{
				splitData.add(data.getId(i), data.getState(i));
			}
			
			if(data.getId(i) == Ev_ClassEnd && classCount > 1){
				subClassList.add(new StrClass(subSplitData));
				subSplitData.clear();
			}
			
			if(data.getId(i) == Ev_ClassEnd && classCount == 1){
				file.add(new StrClass(splitData));
				if(!subClassList.isEmpty()){
					for(int j=0; j<subClassList.size(); j++){
					file.setSubClassData(subClassList.get(j));
					}
				}
				splitData.clear();
			}
			if(data.getId(i) == Ev_ClassEnd){classCount--;}
		}
		m_Files.add(file);
		return;
	}
	
}
