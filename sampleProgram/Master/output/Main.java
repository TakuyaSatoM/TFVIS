public class Main{
  static final int TP_CLASSID = 0;
  static int TP_INSTANCEID = -1;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args);
    Ary ary=new Ary (7);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2);
    ary.setValue(3);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3);
    ary.setValue(0);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
    ary.setValue(4);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
    ary.setValue(7);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
    ary.setValue(2);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
    int min=ary.getMin();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "min",min,"");
    System.out.println("Å¬’l>>"+min);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "min"  );
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
    int max=ary.getMax();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "max",max,"");
    System.out.println("Å‘å’l>>"+max);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "max"  );
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11);
    int ave=ary.getAve();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "ave",ave,"");
    System.out.println("•½‹Ï’l>>"+ave);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12, "ave"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13  );
  }

}
