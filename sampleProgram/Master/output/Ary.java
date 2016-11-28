public class Ary{
int num;
int maxSize;
int[] values;
  static final int TP_CLASSID = 1;
  static int TP_INSTANCEID = -1;

  public Ary(int isize){
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "isize",isize,"");
    num=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num",num,"");
    maxSize=isize;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "maxSize",maxSize,"isize");
    values=new int[maxSize];
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "values",values);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
  }

  public int setValue(int val){
   final int TP_METHODID = 2;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "val",val,"");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num,maxSize"  );
    if(num>=maxSize){
     {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
       }
    }
    num++;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "num",num,"num");
    values[num]=val;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "values["+num+"]",values[num],"num,val");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
    return 1;
  }

  public int getMin(){
   final int TP_METHODID = 3;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num"  );
    if(num<=0){
     {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
       }
    }
    int min=values[0];
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "min",min,"values["+0+"]");
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
     boolean tvisForInit0=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i");
    for(int i=1;i<num;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "i",i,"i")){
     if(tvisForInit0==false){tvisForInit0=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i",i,"");}
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "min,values["+i+"],i"  );
      if(min>values[i]){
       {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
        min=values[i];
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "min",min,"values["+i+"],i");
         }
      }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,5);
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,5);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "min"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10  );
    return min;
  }

  public int getMax(){
   final int TP_METHODID = 4;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num"  );
    if(num<=0){
     {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
       }
    }
    int max=values[0];
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "max",max,"values["+0+"]");
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
     boolean tvisForInit1=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i");
    for(int i=1;i<num;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "i",i,"i")){
     if(tvisForInit1==false){tvisForInit1=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i",i,"");}
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "max,values["+i+"],i"  );
      if(max<values[i]){
       {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
        max=values[i];
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "max",max,"values["+i+"],i");
         }
      }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,5);
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,5);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "max"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10  );
    return max;
  }

  public int getAve(){
   final int TP_METHODID = 5;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num"  );
    if(num<=0){
     {TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
       }
    }
    int sum=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "sum",sum,"");
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
     boolean tvisForInit2=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i");
    for(int i=0;i<num;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "i",i,"i")){
     if(tvisForInit2==false){tvisForInit2=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i",i,"");}
      sum+=values[i];
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "sum",sum,"sum,values["+i+"],i");
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7,5);
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,5);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
    int ave=sum/num;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "ave",ave,"sum,num");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "ave"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9  );
    return ave;
  }

}
