import java.lang.reflect.Field;
public class Print{
static String str;
  static final int TP_CLASSID = 1;

  public Print(){
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    str="";
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "str",str,"",false);
    System.out.println(this.hashCode());
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
  }

  public void set(String newStr){
   final int TP_METHODID = 2;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "newStr",newStr,"",false);
    str=newStr;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "str",str,"newStr",false);
    System.out.println(str);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "str"  );
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
    return ;
  }

}
