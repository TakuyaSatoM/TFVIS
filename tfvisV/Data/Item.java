public class Item{
private int id;
private String name;
private int totalWeight;
private int totalPrice;
  static final int TP_CLASSID = 1;

  public Item(int id,String name,int weight,int price){
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "id",id,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "name",name,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "weight",weight,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "price",price,"",false);
    this.id=id;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "id",id,"id",false);
    this.name=name;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "name",name,"name",false);
    this.totalWeight=weight;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "totalWeight",totalWeight,"weight",false);
    this.totalPrice=price;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "totalPrice",totalPrice,"price",false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
  }

  public int getID(){
   final int TP_METHODID = 2;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "id"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
    return id;
  }

  public String getName(){
   final int TP_METHODID = 3;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "name"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
    return name;
  }

  public int getWeight(){
   final int TP_METHODID = 4;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "totalWeight"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
    return totalWeight;
  }

  public int getTotalPrice(){
   final int TP_METHODID = 5;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "totalPrice"  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
    return totalPrice;
  }

  public void addWeight(int weight){
   final int TP_METHODID = 6;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "weight",weight,"",false);
    this.totalWeight=weight;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "totalWeight",totalWeight,"weight",false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
  }

  public void addPrice(int price){
   final int TP_METHODID = 7;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "price",price,"",false);
    this.totalPrice+=price;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "totalPrice",totalPrice,"totalPrice,price",false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2  );
  }

  public void print(){
   final int TP_METHODID = 8;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    System.out.println("ID:"+this.id);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "id"  );
    System.out.println("Name:"+this.name);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "name"  );
    System.out.println("Stock:"+this.totalWeight);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "totalWeight"  );
    System.out.println("Unit Price:"+this.totalPrice);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "totalPrice"  );
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
    return ;
  }

}
