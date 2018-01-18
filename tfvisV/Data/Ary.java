public class Ary{
int num;
int maxSize;
int[] values;
  static final int TP_CLASSID = 1;

  public Ary(int isize){
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "isize",isize,"",false);
    num=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num",num,"",false);
    maxSize=isize;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "maxSize",maxSize,"isize",false);
    values=new int[maxSize];
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"values",values,false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
  }

  public int setValue(int val){
   final int TP_METHODID = 2;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "val",val,"",false);
    if(num>=maxSize){
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num,maxSize"  );
     TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
    }
    num++;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "num",num,"num",false);
    values[num]=val;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "values["+num+"]",values[num],"num,val",false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
    return 1;
  }

  public int getMin(){
   final int TP_METHODID = 3;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    if(num<=1){
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num"  );
     TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
    }
    int min=values[0];
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "min",min,"values["+0+"]",false);
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
     boolean tvisLoopInit0=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i");
    for(int i=0;i<num;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "i",i,"i",false)){
     isLoop=true;
     if(tvisLoopInit0==false){tvisLoopInit0=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i",i,"",false);}
      if(min>values[i]){
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "min,values["+i+"],i"  );
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
        min=values[i];
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "min",min,"values["+i+"],i",false);
      }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,5);
     isLoop=false;
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
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    if(num<=0){
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "num"  );
     TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
      return 0;
    }
    int max=values[0];
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "max",max,"values["+0+"]",false);
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
     boolean tvisLoopInit1=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i");
    for(int i=0;i<num;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "i",i,"i",false)){
     isLoop=true;
     if(tvisLoopInit1==false){tvisLoopInit1=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,"i",i,"",false);}
      if(max<values[i]){
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "max,values["+i+"],i"  );
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
        max=values[i];
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "max",max,"values["+i+"],i",false);
      }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,5);
     isLoop=false;
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
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    int ave;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "ave"  );
    if(num<=0){
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "num"  );
     TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
      return 0;
    }
    int sum=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "sum",sum,"",false);
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
     boolean tvisLoopInit2=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"i");
    for(int i=0;i<num;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "i",i,"i",false)){
     isLoop=true;
     if(tvisLoopInit2==false){tvisLoopInit2=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"i",i,"",false);}
      sum+=values[i];
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "sum",sum,"sum,values["+i+"],i",false);
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8,6);
     isLoop=false;
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,6);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8);
    ave=sum/num;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "ave",ave,"sum,num",false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "ave"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10  );
    return ave;
  }

}
