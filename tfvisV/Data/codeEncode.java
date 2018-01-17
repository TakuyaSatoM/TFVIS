public class codeEncode{
  static final int TP_CLASSID = 0;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   int TP_INSTANCEID = TP_CLASSID*-1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0,"args",args,false);
    String[] input={"1 qwertyuiopasdfghjklzxcvbnm","hqomq gfsoft iqeaqzigf"};
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"input",input,false);
    String[] line=input[0].split(" ");
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"line",line,false);
    int encodeNum=Integer.parseInt(line[0]);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "encodeNum",encodeNum,"line["+0+"]",false);
    char[] state=line[1].toCharArray();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,"state",state,false);
    char[] ciphertext=input[1].toCharArray();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"ciphertext",ciphertext,false);
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
     boolean tvisLoopInit0=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"i");
    for(int i=0;i<encodeNum;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "i",i,"i",false)){
     isLoop=true;
     if(tvisLoopInit0==false){tvisLoopInit0=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"i",i,"",false);}
       TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
       boolean tvisLoopInit1=false;
       TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7,"j");
      for(int j=0;j<ciphertext.length;j++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "j",j,"j",false)){
       isLoop=true;
       if(tvisLoopInit1==false){tvisLoopInit1=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7,"j",j,"",false);}
        if(ciphertext[j]==' '){
         TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "ciphertext["+j+"],j"  );
         TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
          continue;
        }
        int index=String.valueOf(state).indexOf(String.valueOf(ciphertext[j]));
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "index",index,".,state,ciphertext["+j+"],j",false);
        char start='a';
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12, "start",start,"",false);
        char convertedText=Character.valueOf((char )(start+index));
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13, "convertedText",convertedText,".,),start,index",false);
        ciphertext[j]=convertedText;
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,14, "ciphertext["+j+"]",ciphertext[j],"j,convertedText",false);
       TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15,7);
       isLoop=false;
      }
       TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7,7);
       TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15);
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16,6);
     isLoop=false;
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,6);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16);
    System.out.println(ciphertext);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,17, ".,ciphertext,)"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,18  );
  }

}
