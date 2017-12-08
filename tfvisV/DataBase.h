#ifndef _DataBase_h_
#define _DataBase_h_
#include "Class.h"



namespace db{

 //メソッド検索
 Method* searchMethod(int globalId);

 //クラス検索
 C_Class* searchClass(int globalId);
 C_Class* searchClass(string name);

 //メソッド実行検索
 MethodExe* searchMethodExe(Method* method,int exeNum);
 MethodExe* searchMethodExe(int globalId);

 //実行検索
 Exe* searchExe(int type,int id);

 //行検索
 C_Line* searchLine(Method* method,int id);

 //関係性の登録
 void setRelation_MethodExe_andExe();

 //データ遷移図の作成
 int createDTTable();

 //データ読込
 int load();

  C_Class* getClass();
  Exe* getExe();
  MethodExe* getMethodExe();
  
}

namespace ot{

   D3DXVECTOR4 colorPattern(int id);
   void drawWindowFrame(C_Box mainArea,float bold);
   void printTexture(RC_Texture* texture,C_Box area,INT2 tex,float z);

}

#endif 