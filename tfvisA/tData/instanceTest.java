import java.util.*;
public class instanceTest{
  static final int TP_CLASSID = 0;
  static int TP_INSTANCEID = -1;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args,false);
    List ary=new ArrayList ();
    Print print=new Print ();
try{
 TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
       TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
      print.set("aaa");
       TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
      print.str="bbb";
       TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
      System.out.println(print.str);
       TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7  );
    }
    catch(IndexOutOfBoundsException e){
     TProbe.Input_Catch(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
      System.out.println(e);
     if(isLoop){
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0,0);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);}
    }
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11  );
  }

}
