public class InstanceTest{
  static final int TP_CLASSID = 0;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   int TP_INSTANCEID = TP_CLASSID*-1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args,false);
    InstanceItem item=new InstanceItem ();
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"item",item);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2);
    System.out.println(item.getDynamicID());
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3);
    item.setID(1);
    InstanceItem newItem=item;
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,"newItem",newItem);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
    int staticID=newItem.getStaticID();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "staticID",staticID,"",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
    int dynamicID=newItem.getDynamicID();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "dynamicID",dynamicID,"",false);
    System.out.println(staticID+","+dynamicID);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "staticID,dynamicID"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
  }

}
