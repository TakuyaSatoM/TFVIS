import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class TProbe {

	static File m_MethodFile;
	
	static final int START_CLASS =0;
	static final int END_CLASS =1;	
	static final int START_METHOD =100;
	static final int END_METHOD =101;
	
	static final int RETURN =150;	

	static final int ROUTE =170;	
	
	static final int UPDATE_INT=200;
	static final int UPDATE_INTARRAY=201;	
	
	static final int UPDATE_DOUBLE=210;
	static final int UPDATE_DOUBLEARRAY=211;	
	
	static final int UPDATE_STRING=220;
	static final int UPDATE_STRINGARRAY=221;
	
	static final int CALL_METHOD=250;
	
	static final int LOOP =300;	
	static final int LOOPNEXT =301;	
	static final int LOOPEND =302;	
	
	static final int DATAREAD=500;	
	
	static final int LIFELIMIT =680;	
	
	static final int OTHER =999;

	static final String DELIM ="#";
	static int m_ExeCount=0;
	static int m_InstanceCount=0;
	
	static public int GetExe()
	{return m_ExeCount++;}
	
	static public int GetInstance(int i)
	{
		if(i>=0){return i;}
		return m_InstanceCount++;
	}	
	
	static public void Init()
	{
	      m_MethodFile = new File("tData\\Method.txt");
		  //�t�@�C����������  
	      if (m_MethodFile.exists()){ m_MethodFile.delete();}	      		
	      try{
	    	  m_MethodFile.createNewFile();
	        }catch(IOException e){System.out.println(e);}   		
	}
	
	//���\�b�h�̊J�n
	static private void input_common(int type,int insID,int methodid,int methodexe,int line)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(type+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	

		
	static public void Input_Route(int insID,int methodid,int methodexe,int line)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(ROUTE+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	
	
	//���\�b�h�̊J�n
	static public void Input_StartMethod(int insID,int methodid,int methodexe,int line)
	{
		input_common(START_METHOD,insID,methodid,methodexe,line); 
	}
	//���\�b�h�̏I��
	static public void Input_EndMethod(int insID,int methodid,int methodexe,int line)
	{
		input_common(END_METHOD,insID,methodid,methodexe,line);  
	}
	static public void Input_MethodCall(int insID,int methodid,int methodexe,int line)
	{
		input_common(CALL_METHOD,insID,methodid,methodexe,line); 
	}	
	
	static public void Input_DataRead(int insID,int methodid,int methodexe,int line)
	{
		input_common(DATAREAD,insID,methodid,methodexe,line); 
	}	
	
	//�X�V-int�z��
	static public void Input_Update(int insID,int methodid,int methodexe,int line,String name,int[] array)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.print(UPDATE_INTARRAY+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM);
		      pw.print(line+DELIM+name+","+array.length);
		      for(int i=0;i<array.length;i++)
		      {
		    	  pw.print(","+array[i]);
		      }
		      pw.println(","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}
	//�X�V-String�z��
	static public void Input_Update(int insID,int methodid,int methodexe,int line,String name,String[] array)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.print(UPDATE_STRINGARRAY+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM);
		      pw.print(line+DELIM+name+","+array.length);
		      for(int i=0;i<array.length;i++){pw.print(","+array[i]);}
		      pw.println(","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	
	//�X�V-double�z��
	static public void Input_Update(int insID,int methodid,int methodexe,int line,String name,double[] array)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.print(UPDATE_DOUBLEARRAY+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM);
		      pw.print(line+DELIM+name+","+array.length);
		      for(int i=0;i<array.length;i++){pw.print(","+array[i]);}
		      pw.println(","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}		
	
	//�X�V-int�P��
	static public void Input_Update(int insID,int methodid,int methodexe,int line,String name,int var,String infs)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(UPDATE_INT+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM+name+","+var+","+infs+","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	
	//�X�V-String�P��
	static public void Input_Update(int insID,int methodid,int methodexe,int line,String name,String var,String infs)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(UPDATE_STRING+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM+name+","+var+","+infs+","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	
	//�X�V-double�P��
	static public void Input_Update(int insID,int methodid,int methodexe,int line,String name,double var,String infs)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(UPDATE_DOUBLE+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM+name+","+var+","+infs+","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}		
	

	//�������E-int
	static public void Input_LifeLimit(int insID,int methodid,int methodexe,int line,String name)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(LIFELIMIT+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM+name+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}		
	
	//�X�V-int�P��
	static public void Input_Read(int insID,int methodid,int methodexe,int line,String infs)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(DATAREAD+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM+infs+","+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	
	
	//���̑�
	static public void Input_Other(int insID,int methodid,int methodexe,int line)
	{
		input_common(OTHER,insID,methodid,methodexe,line); 
	}	
	//���̑�
	static public void Input_Loop(int insID,int methodid,int methodexe,int line)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(LOOP+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}		
	static public void Input_LoopNext(int insID,int methodid,int methodexe,int line,int object)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(LOOPNEXT+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM+object+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}	
	static public void Input_LoopEnd(int insID,int methodid,int methodexe,int line)
	{
		 try
		 {
		  	  PrintWriter pw = new PrintWriter( new FileWriter(m_MethodFile, true) );    
		      pw.println(LOOPEND+DELIM+insID+DELIM+methodid+DELIM+methodexe+DELIM+line+DELIM);
		      pw.close();	  	
		 }catch(IOException e){System.out.println(e);}   
	}		
}