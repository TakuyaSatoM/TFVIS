public class Main{
  static final int TP_CLASSID = 0;
  static int TP_INSTANCEID = -1;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1);
    rec(0);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
  }

  static void rec(int y){
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "y",y,"");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "y"  );
    if(y>1){
     {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return ;
       }
    }
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
    rec(y+1);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
    rec(y+1);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
  }

}
