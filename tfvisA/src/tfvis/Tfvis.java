package tfvis;
















import java.io.*;
import java.util.*;







public class Tfvis implements tfvisConstants{
	
	public static void main(String[] args)throws InterruptedException,IOException  {
		
		
		//�Â��t�@�C�����폜
		{
			Tools.clearDir(new File(outDir));	
		//	File newdir = new File(outDir);
		//	newdir.mkdir();	
		}
		//�\�[�X�t�@�C�����擾
		String[] sourceFiles=Tools.searchFile(sourceDir);
		
		//�\�[�X�R�[�h���
		Program pro;
		{
			pro=new Program(sourceDir,sourceFiles);	
			pro.outputStr();
			pro.outputProbe();
			Tools.copyProbeBaseFile();			
		}
		String mainClass=pro.getMainClassName();
		if(mainClass.length() <= 0){System.out.println("��̓G���[");System.exit(0);}
		
		
		{
			//���O�t�@�C������
		    File ssfile = new File("log.txt");
		    //�t�@�C����������  
		    if (ssfile.exists()){ ssfile.delete();}	      		
		    try{
			  ssfile.createNewFile();
		    }catch(IOException e){System.out.println(e);}  	
		    
		      
		    try{
			PrintWriter fout = new PrintWriter(new FileWriter(ssfile,true));
			//�v���[�u�R���p�C��
				{
					List<String> list = new ArrayList<String>();
					
					list.add("javac");
					for(String file:sourceFiles){
						list.add(outDir+file);
					}
					list.add(outDir+"TProbe.java");
					
				   ProcessBuilder pb = new ProcessBuilder(list);
				   Process process = pb.start();
				   

					//InputStream�̃X���b�h�J�n
					InputStreamThread it = new InputStreamThread(process.getInputStream());
					InputStreamThread et = new InputStreamThread(process.getErrorStream());
					it.start();
					et.start();
		
					//�v���Z�X�̏I���҂�
					process.waitFor();
					//InputStream�̃X���b�h�I���҂�
					it.join();
					et.join();

					if(process.exitValue()==1)
					{
						fout.println("ERROR------------------------------------------------\n");
						for (String s : et.getStringList()) {System.out.println(s);}
						fout.println("------------------------------------------------\n");			
						fout.println("�Ώۃt�@�C���̍\���`�F�b�N�G���[");
						System.exit(0);
					}
		
				}		
				//�v���[�u���s
				{
					System.out.println("�v���[�u���s");
					   ProcessBuilder pb = new ProcessBuilder("java","-classpath","tdata",mainClass);
					   Process process = pb.start();	
						//InputStream�̃X���b�h�J�n
						InputStreamThread it = new InputStreamThread(process.getInputStream());
						InputStreamThread et = new InputStreamThread(process.getErrorStream());
						InputListener listener = new InputListener(process.getOutputStream());
						listener.setDaemon(true);
						
						it.start();
						et.start();
						listener.start();
						
						
						//�v���Z�X�̏I���҂�
						process.waitFor();
						//InputStream�̃X���b�h�I���҂�
						it.join();
						et.join();
		
						if(process.exitValue()==1)
						{
							System.out.println("�v���[�u���s���s");
							fout.println("ERROR------------------------------------------------\n");
							for (String s : et.getStringList()) {System.out.println(s);;}
							fout.println("------------------------------------------------\n");
							fout.println("�v���[�u�t�@�C�����s�G���[\n");
							System.exit(0);
						}
						else
						{
							System.out.println();
							fout.println("CLEAR------------------------------------------------\n");
							for (String s : it.getStringList()) {fout.println(s);}
							fout.println("------------------------------------------------\n");
						}				
				}	
				fout.close();
			}
			catch(IOException e) {e.printStackTrace();}		
		}
	
	}
}