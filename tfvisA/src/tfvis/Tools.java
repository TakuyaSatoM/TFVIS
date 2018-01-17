package tfvis;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
public class Tools implements tfvisConstants{

	/**
	 * id��element�̑������������m�F����
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
	 *  �C���f���g�̐[���ɉ������󔒕�����𐶐�
	 *  @param indent �C���f���g�̐[��
	 */
	static public String getIndetStr(int indent){
		String add="";
		for(int ci=0;ci<indent;ci++){add+="  ";}	
		return add;
	}
	
	
	static public void copyProbeBaseFile(){
        try {
            //File�I�u�W�F�N�g�𐶐�����
            FileInputStream fis = new FileInputStream("data\\TProbe.java");
            FileOutputStream fos = new FileOutputStream(outDir+"TProbe.java");

            //���̓t�@�C�������̂܂܏o�̓t�@�C���ɏ����o��
            byte buf[] = new byte[256];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

            //�I������
            fos.flush();
            fos.close();
            fis.close();

         //   System.out.println("�R�s�[���������܂����B");

        } catch (IOException ex) {
            //��O������
        //    System.out.println("�R�s�[�Ɏ��s���܂����B");
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
	 *  �g�[�N���̌��ɃX�y�[�X���K�v��
	 */
	static public boolean checkNeedSpace(ArrayList<Token> tokens,int now){
		
		if(now <0 || now+1 >=tokens.size()){return false;}
		
		boolean needSpace=false;
		
		int nowID=tokens.get(now).m_ID;
		
		//int*a; //*�̏ꏊ�ɋ�
		if(nowID >= 90 && nowID<=99){
				if(!Tools.getHasElement(tokens.get(now+1).m_ID, Lbracket) ){
					needSpace=true;
				}				
		}

		if(now>1){
			
			if(Tools.getHasElement(tokens.get(now-1).m_ID, Lbracket)  && Tools.getHasElement(tokens.get(now).m_ID, Rbracket) ){
				needSpace=true;
			}	
			//Ary*ary;*�̏ꏊ�ɋ�
			if(tokens.get(now).m_ID == CoiType1 + Identifier && !tokens.get(now+1).m_State.equals("[") ){needSpace=true;}
		}
		
		//final*int a; //*�̏ꏊ�ɋ�
		if(LocalVariable + Final==nowID){needSpace=true;}
		
		//return*0; //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("return")){needSpace=true;}
		
		//Ary ary=new*tmp; //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("new")){needSpace=true;}
		
		//public*class //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("public")){needSpace=true;}
		//protected*class //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("protected")){needSpace=true;}
		//private*class //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("private")){needSpace=true;}		

		//class*tmp //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("class")){needSpace=true;}		

		//static*int //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("static")){needSpace=true;}	
		
		//case* //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("case")){needSpace=true;}
		
		//else*if //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("else")){needSpace=true;}
		
		//import* //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("import")){needSpace=true;}
		
		//package* //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("package")){needSpace=true;}
		
		//throws*IOExeption //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("throws")){needSpace=true;}
		
		//extends* //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("extends")){needSpace=true;}
		
		//implements* //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("implements")){needSpace=true;}
		
		//final* //*�̏ꏊ�ɋ�
		if(tokens.get(now).m_State.equals("final")){needSpace=true;}
		return needSpace;
	}	
	
	/**
	 * �t�H���_������(���g��S������)
	 * @param dirOrFile�@�t�@�C���p�X(�t�H���_�p�X)
	 * @return none
	 */	
    public static void clearDir(File dirOrFile) {  
        if (dirOrFile.isDirectory()) {//�f�B���N�g���̏ꍇ  
            String[] children = dirOrFile.list();//�f�B���N�g���ɂ��邷�ׂẴt�@�C������������  
            for (int i=0; i<children.length; i++) {  
               deleteFile(new File(dirOrFile, children[i]));  
            }  
        }  
    } 		
	/**
	 * �t�@�C�����폜�p(�w�肵���t�H���_��������)
	 * @param dirOrFile �t�@�C���p�X(�t�H���_�p�X)
	 * @return�@����
	 */
    public static boolean deleteFile(File dirOrFile) {  
        if (dirOrFile.isDirectory()) {//�f�B���N�g���̏ꍇ  
            String[] children = dirOrFile.list();//�f�B���N�g���ɂ��邷�ׂẴt�@�C������������  
            for (int i=0; i<children.length; i++) {  
                boolean success = deleteFile(new File(dirOrFile, children[i]));  
                if (!success) {  
                    return false;  
                }  
            }  
        }  
        // �폜  
        return dirOrFile.delete();  
    } 	
	
    /**
     * �w�肵���t�H���_�ɂ���t�@�C�����擾
     * @param name �t�H���_�p�X
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
