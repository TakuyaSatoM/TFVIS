public class InstanceTest{
  static final int TP_CLASSID = 0;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   int TP_INSTANCEID = -1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args,false);
    InstanceItem item=new InstanceItem ();
     TProbe.Generate_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"item",item);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "item"  );
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2);
    item.setID(1);
    InstanceItem newItem=item;
     TProbe.Generate_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"newItem",newItem);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
    int id=newItem.getID();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "id",id,"newItem",false);
    System.out.println(id);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "id"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
  }

}
