import java.util.ArrayList;
public class StockControler{
private static ArrayList <Item >ItemList;
private int ItemID;
  static final int TP_CLASSID = 2;

  public StockControler(){
   final int TP_METHODID = 9;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    ItemList=new ArrayList <Item >();
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"ItemList",ItemList);
    ItemID=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "ItemID",ItemID,"",false);
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
  }

  public void set(String name,int weight,int price){
   final int TP_METHODID = 10;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "name",name,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "weight",weight,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "price",price,"",false);
    Item item=new Item (ItemID,name,weight,price);
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"item",item);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "ItemID,name,weight,price"  );
    ItemList.add(item);
    ItemID++;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "ItemID",ItemID,"ItemID",false);
    System.out.println("ç›å…"+name+"Çìoò^ÇµÇ‹ÇµÇΩ");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "name"  );
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
    return ;
  }

  public void addStock(String name,int weight,int Amount){
   final int TP_METHODID = 11;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "name",name,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "weight",weight,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "Amount",Amount,"",false);
    Item item=null;
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"item",item);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2);
    item=searchItem(name);
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"item",item);
    if(item==null){
     TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
      System.out.println("äYìñÇ∑ÇÈç›å…Ç™ë∂ç›ÇµÇ‹ÇπÇÒÅB");
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, ""  );
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
      return ;
    }
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
    item.addWeight(weight);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8);
    item.addPrice(Amount);
    System.out.println(name+"Ç"+weight+"kgí«â¡ÇµÇ‹ÇµÇΩÅB");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "name,weight"  );
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10  );
  }

  public Item searchItem(String SearchName){
   final int TP_METHODID = 12;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "SearchName",SearchName,"",false);
    int i=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "i",i,"",false);
    Item item=null;
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"item",item);
    String name="";
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "name",name,"",false);
    Item result=null;
     TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,"result",result);
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
    while(i<ItemList.size()){
     isLoop=true;
      item=ItemList.get(i);
       TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"item",item);
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "i"  );
       TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
      name=item.getName();
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "name",name,"",false);
      if(name.equals(SearchName)){
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "name,SearchName"  );
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
        result=item;
         TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,"result",result);
      }
      i++;
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "i",i,"i",false);
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12,5);
     isLoop=false;
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5,5);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13  );
    return result;
  }

  public void searchInfo(int id){
   final int TP_METHODID = 13;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "id",id,"",false);
    Item item;
try{
 TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
      item=ItemList.get(id);
       TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"item",item);
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "id"  );
       TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
       TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
      item.print();
       TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
    }
    catch(IndexOutOfBoundsException e){
     TProbe.Input_Catch(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
      System.out.println("äYìñÇ∑ÇÈç›å…Ç™ë∂ç›ÇµÇ‹ÇπÇÒÅB");
     if(isLoop){
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0,0);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);}
    }
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9  );
    return ;
  }

  public void calculateUnitPrice(){
   final int TP_METHODID = 14;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    Item item;
    int weight=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "weight",weight,"",false);
    int totalPrice=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "totalPrice",totalPrice,"",false);
    int unitPrice=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "unitPrice",unitPrice,"",false);
    String name;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "name"  );
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
     boolean tvisLoopInit0=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"i");
    for(int i=0;i<ItemList.size();i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "i",i,"i",false)){
     isLoop=true;
     if(tvisLoopInit0==false){tvisLoopInit0=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,"i",i,"",false);}
try{
 TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
        item=ItemList.get(i);
         TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8,"item",item);
         TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "i"  );
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9  );
         TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
        name=item.getName();
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "name",name,"",false);
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10  );
         TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10);
        weight=item.getWeight();
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "weight",weight,"",false);
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11  );
         TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11);
        totalPrice=item.getTotalPrice();
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "totalPrice",totalPrice,"",false);
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12  );
        unitPrice=totalPrice/weight;
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12, "unitPrice",unitPrice,"totalPrice,weight",false);
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13  );
        System.out.println(weight);
         TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13, "weight"  );
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,14  );
        System.out.println(name+":"+unitPrice);
         TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,14, "name,unitPrice"  );
         TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15  );
      }
      catch(ArithmeticException e){
       TProbe.Input_Catch(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16  );
        System.out.println("ç›å…Ç»Çµ");
      }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,19,6);
     isLoop=false;
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,6);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,19);
    System.out.println();
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,21, ""  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,21  );
    return ;
  }

  public void addStockUnderWeight(int baseWeight,int addWeight){
   final int TP_METHODID = 15;
   int TP_METHODEXE=TProbe.GetExe();	
   int TP_INSTANCEID = this.hashCode();
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "baseWeight",baseWeight,"",false);
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "addWeight",addWeight,"",false);
     TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1);
     boolean tvisLoopInit1=false;
     TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"i");
    for(int i=0;i<ItemList.size();i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "i",i,"i",false)){
     isLoop=true;
     if(tvisLoopInit1==false){tvisLoopInit1=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,"i",i,"",false);}
      Item item=ItemList.get(i);
       TProbe.Update_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2,"item",item);
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "i"  );
       TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3);
      int itemWeight=item.getWeight();
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "itemWeight",itemWeight,"",false);
      if(baseWeight>itemWeight){
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "baseWeight,itemWeight"  );
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
         TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
         TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5);
        item.addWeight(addWeight);
      }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7,1);
     isLoop=false;
    }
     TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1,1);
     TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8);
    System.out.println(baseWeight+"à»â∫ÇÃç›å…Ç"+addWeight+"kgí«â¡ÇµÇ‹ÇµÇΩÅB");
   TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9  );
  }

}
