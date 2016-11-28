public class Main{
  static final int TP_CLASSID = 0;
  static int TP_INSTANCEID = -1;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args);
    int kki=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "kki",kki,"");
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2);
     boolean tvisLoopInit0=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"x");
    for(int x=0;x<3;x++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "x",x,"x")){
     if(tvisLoopInit0==false){tvisLoopInit0=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"x",x,"");}
       TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3);
       boolean tvisLoopInit1=false;
       TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"y");
      for(int y=0;y<3;y++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "y",y,"y")){
       if(tvisLoopInit1==false){tvisLoopInit1=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"y",y,"");}
        kki=x+y;
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "kki",kki,"x,y");
       TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,3);
      }
       TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,3);
       TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,2);
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,2);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
    System.out.println(kki);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "kki"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
  }

}
