public class instanceTest{
  static final int TP_CLASSID = 0;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   int TP_INSTANCEID = -1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args,false);
    Print print=new Print ();
     TProbe.Generate_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,print);
    Print print2=new Print ();
     TProbe.Generate_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,print2);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3);
    print.set("aaa");
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
    print2.set("bbb");
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
    print.set("ccc");
    System.out.println(print.str);
    System.out.println(print2.str);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
  }

}
