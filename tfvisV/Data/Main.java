public class Main{
  static final int TP_CLASSID = 0;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   int TP_INSTANCEID = TP_CLASSID*-1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0,"args",args,false);
    Ary ary=new Ary (7);
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"ary",ary);
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
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "min",min,"",false);
    System.out.println("Å¬>>"+min);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "min"  );
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
    int max=ary.getMax();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "max",max,"",false);
    System.out.println("Å‘å>>"+max);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "max"  );
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11);
    int ave=ary.getAve();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "ave",ave,"",false);
    System.out.println("•½‹Ï>>"+ave);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12, "ave"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13  );
  }

}
