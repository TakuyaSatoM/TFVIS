#ifndef _DataBase_h_
#define _DataBase_h_
#include "Class.h"



namespace db{

 //���\�b�h����
 Method* searchMethod(int globalId);

 //�N���X����
 C_Class* searchClass(int globalId);
 C_Class* searchClass(string name);

 //���\�b�h���s����
 MethodExe* searchMethodExe(Method* method,int exeNum);
 MethodExe* searchMethodExe(int globalId);

 //���s����
 Exe* searchExe(int type,int id);

 //�s����
 C_Line* searchLine(Method* method,int id);

 //�֌W���̓o�^
 void setRelation_MethodExe_andExe();

 //�f�[�^�J�ڐ}�̍쐬
 int createDTTable();

 //�f�[�^�Ǎ�
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