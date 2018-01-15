public class InstanceItem{
static int staticID;
int dynamicID;
  static final int TP_CLASSID = 1;

  public InstanceItem(){
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    staticID=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "staticID",staticID,"",false);
    dynamicID=this.hashCode();
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "dynamicID",dynamicID,"",false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
  }

  public void setID(int num){
   final int TP_METHODID = 2;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "num",num,"",false);
    dynamicID=num;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "dynamicID",dynamicID,"num",false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
  }

  int getDynamicID(){
   final int TP_METHODID = 3;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "dynamicID"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
    return dynamicID;
  }

  static int getStaticID(){
   final int TP_METHODID = 4;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = TP_CLASSID*-1;
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "staticID"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
    return staticID;
  }

}
