public class Sample{
  static final int TP_CLASSID = 0;

  public static void main(String[] args){
   final int TP_METHODID = 0;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Init();
   int TP_INSTANCEID = TP_CLASSID*-1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "args",args,false);
    Item item=new Item ();
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"item",item);
    Item newItem=item;
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"newItem",newItem);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
  }

}
