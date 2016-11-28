package tfvis;
















import java.io.*;
import java.util.*;







public class Tfvis implements tfvisConstants{
	
	public static void main(String[] args)throws InterruptedException,IOException  {
		
		
		//古いファイルを削除
		{
			Tools.clearDir(new File(outDir));	
		//	File newdir = new File(outDir);
		//	newdir.mkdir();	
		}
		//ソースファイルを取得
		String[] sourceFiles=Tools.searchFile(sourceDir);
		
		//ソースコード解析
		Program pro;
		{
			pro=new Program(sourceDir,sourceFiles);	
			pro.outputStr();
			pro.outputProbe();
			Tools.copyProbeBaseFile();			
		}
		String mainClass=pro.getMainClassName();
		if(mainClass.length() <= 0){System.out.println("解析エラー");System.exit(0);}
		
		
		{
			//ログファイル準備
		    File ssfile = new File("log.txt");
		    //ファイルを初期化  
		    if (ssfile.exists()){ ssfile.delete();}	      		
		    try{
			  ssfile.createNewFile();
		    }catch(IOException e){System.out.println(e);}  	
		    
		      
		    try{
			PrintWriter fout = new PrintWriter(new FileWriter(ssfile,true));
			//プローブコンパイル
				{
					List<String> list = new ArrayList<String>();
					
					list.add("javac");
					for(String file:sourceFiles){
						list.add(outDir+file);
					}
					list.add(outDir+"TProbe.java");
					
				   ProcessBuilder pb = new ProcessBuilder(list);
				   Process process = pb.start();
				   

					//InputStreamのスレッド開始
					InputStreamThread it = new InputStreamThread(process.getInputStream());
					InputStreamThread et = new InputStreamThread(process.getErrorStream());
					it.start();
					et.start();
		
					//プロセスの終了待ち
					process.waitFor();
					//InputStreamのスレッド終了待ち
					it.join();
					et.join();

					if(process.exitValue()==1)
					{
						fout.println("ERROR------------------------------------------------\n");
						for (String s : et.getStringList()) {System.out.println(s);}
						fout.println("------------------------------------------------\n");			
						fout.println("対象ファイルの構文チェックエラー");
						System.exit(0);
					}
		
				}		
				//プローブ実行
				{
					System.out.println("プローブ実行");
					   ProcessBuilder pb = new ProcessBuilder("java","-classpath","tdata",mainClass);
					   Process process = pb.start();	
						//InputStreamのスレッド開始
						InputStreamThread it = new InputStreamThread(process.getInputStream());
						InputStreamThread et = new InputStreamThread(process.getErrorStream());
						InputListener listener = new InputListener(process.getOutputStream());
						listener.setDaemon(true);
						
						it.start();
						et.start();
						listener.start();
						
						
						//プロセスの終了待ち
						process.waitFor();
						//InputStreamのスレッド終了待ち
						it.join();
						et.join();
		
						if(process.exitValue()==1)
						{
							System.out.println("プローブ実行失敗");
							fout.println("ERROR------------------------------------------------\n");
							for (String s : et.getStringList()) {System.out.println(s);;}
							fout.println("------------------------------------------------\n");
							fout.println("プローブファイル実行エラー\n");
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