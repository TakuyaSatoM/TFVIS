package tfvis;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
public class Tools implements tfvisConstants{

	/**
	 * idがelementの属性を持つかを確認する
	 * @param id
	 * @param element
	 * @return
	 */
	static boolean getHasElement(int id,int element){
	
		
		int tmp=id;
		tmp/=10;
		
		id-=tmp*10;
		
		if(id==element){return true;}
		
		return false;
	}
	
	static boolean isUpdateEvent(int eventID) {
		if(Ev_UpdateRangeL <= eventID && eventID <= Ev_UpdateRangeH) {
			return true;
		}
		return false;
	}
	
	static boolean isPrimitiveUpdateEvent(int eventID) {
		if(Ev_UpdateRangeL <= eventID && eventID <= Ev_UpdateRangeH) {
			if (eventID % 2 == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  インデントの深さに応じた空白文字列を生成
	 *  @param indent インデントの深さ
	 */
	static public String getIndetStr(int indent){
		String add="";
		for(int ci=0;ci<indent;ci++){add+="  ";}	
		return add;
	}
	
	
	static public void copyProbeBaseFile(){
        try {
            //Fileオブジェクトを生成する
            FileInputStream fis = new FileInputStream("data\\TProbe.java");
            FileOutputStream fos = new FileOutputStream(outDir+"TProbe.java");

            //入力ファイルをそのまま出力ファイルに書き出す
            byte buf[] = new byte[256];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

            //終了処理
            fos.flush();
            fos.close();
            fis.close();

         //   System.out.println("コピーが完了しました。");

        } catch (IOException ex) {
            //例外時処理
        //    System.out.println("コピーに失敗しました。");
            ex.printStackTrace();
        }		
	}
	
	static public String getVarName(ArrayList<Token> tokens,int start){
		String out="";
		int now=start;
		out+=tokens.get(now++).m_State;
		
		if(tokens.get(now).m_State.equals("[")==false){return out;}
		out+="[";
		
		for(int i=now+1;;i++){
			if(tokens.get(i).m_State.equals("]")){break;}
			
			out+=tokens.get(i).m_State;
			
			
		}
		out+="]";
		return out;
	}
	
	
	/**
	 *  トークンの後ろにスペースが必要か
	 */
	static public boolean checkNeedSpace(ArrayList<Token> tokens,int now){
		
		if(now <0 || now+1 >=tokens.size()){return false;}
		
		boolean needSpace=false;
		
		int nowID=tokens.get(now).m_ID;
		
		//int*a; //*の場所に空白
		if(nowID >= 90 && nowID<=99){
				if(!Tools.getHasElement(tokens.get(now+1).m_ID, Lbracket) ){
					needSpace=true;
				}				
		}

		if(now>1){
			
			if(Tools.getHasElement(tokens.get(now-1).m_ID, Lbracket)  && Tools.getHasElement(tokens.get(now).m_ID, Rbracket) ){
				needSpace=true;
			}	
			//Ary*ary;*の場所に空白
			if(tokens.get(now).m_ID == CoiType1 + Identifier && !tokens.get(now+1).m_State.equals("[") ){needSpace=true;}
		}
		
		//final*int a; //*の場所に空白
		if(LocalVariable + Final==nowID){needSpace=true;}
		
		//return*0; //*の場所に空白
		if(tokens.get(now).m_State.equals("return")){needSpace=true;}
		
		//Ary ary=new*tmp; //*の場所に空白
		if(tokens.get(now).m_State.equals("new")){needSpace=true;}
		
		//public*class //*の場所に空白
		if(tokens.get(now).m_State.equals("public")){needSpace=true;}
		//protected*class //*の場所に空白
		if(tokens.get(now).m_State.equals("protected")){needSpace=true;}
		//private*class //*の場所に空白
		if(tokens.get(now).m_State.equals("private")){needSpace=true;}		

		//class*tmp //*の場所に空白
		if(tokens.get(now).m_State.equals("class")){needSpace=true;}		

		//static*int //*の場所に空白
		if(tokens.get(now).m_State.equals("static")){needSpace=true;}	
		
		//case* //*の場所に空白
		if(tokens.get(now).m_State.equals("case")){needSpace=true;}
		
		//else*if //*の場所に空白
		if(tokens.get(now).m_State.equals("else")){needSpace=true;}
		
		//import* //*の場所に空白
		if(tokens.get(now).m_State.equals("import")){needSpace=true;}
		
		//package* //*の場所に空白
		if(tokens.get(now).m_State.equals("package")){needSpace=true;}
		
		//throws*IOExeption //*の場所に空白
		if(tokens.get(now).m_State.equals("throws")){needSpace=true;}
		
		//extends* //*の場所に空白
		if(tokens.get(now).m_State.equals("extends")){needSpace=true;}
		
		//implements* //*の場所に空白
		if(tokens.get(now).m_State.equals("implements")){needSpace=true;}
		
		//final* //*の場所に空白
		if(tokens.get(now).m_State.equals("final")){needSpace=true;}
		return needSpace;
	}	
	
	/**
	 * フォルダ初期化(中身を全部消す)
	 * @param dirOrFile　ファイルパス(フォルダパス)
	 * @return none
	 */	
    public static void clearDir(File dirOrFile) {  
        if (dirOrFile.isDirectory()) {//ディレクトリの場合  
            String[] children = dirOrFile.list();//ディレクトリにあるすべてのファイルを処理する  
            for (int i=0; i<children.length; i++) {  
               deleteFile(new File(dirOrFile, children[i]));  
            }  
        }  
    } 		
	/**
	 * ファイル総削除用(指定したフォルダも消える)
	 * @param dirOrFile ファイルパス(フォルダパス)
	 * @return　成否
	 */
    public static boolean deleteFile(File dirOrFile) {  
        if (dirOrFile.isDirectory()) {//ディレクトリの場合  
            String[] children = dirOrFile.list();//ディレクトリにあるすべてのファイルを処理する  
            for (int i=0; i<children.length; i++) {  
                boolean success = deleteFile(new File(dirOrFile, children[i]));  
                if (!success) {  
                    return false;  
                }  
            }  
        }  
        // 削除  
        return dirOrFile.delete();  
    } 	
	
    /**
     * 指定したフォルダにあるファイルを取得
     * @param name フォルダパス
     * @return
     */
   public static String[] searchFile(String name){
	    File cdirectory = new File(name);
	    String filelist[] = cdirectory.list();
	    return filelist;
   }
   
   public static String changeProbeVars(String text){
	   String out="";
	   String ins="";
	   boolean insMode=false;
	   for(int i=0;i<text.length();i++){
		   if(text.charAt(i) == ']'){
			   break;
		   }		   
		   if(insMode==false){out+=text.charAt(i);}
		   else{ins+=text.charAt(i);}
		   
		   if(text.charAt(i) == '['){
			   insMode=true;
		   }
		   
	   }
	   if(insMode==false){return out;}
	   
	   
	   
	   return ""+out+"\"+"+ins+"+\"]";
   }
   
}
