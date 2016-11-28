#ifndef _Class_h_
#define _Class_h_
#include "Method.h"


class C_Class:public C_Set
{
public:

  C_Class(){
	m_Name="";
	m_ClassID=1;
  }

  C_Class* next(){return (C_Class*)C_Set::CHECK();}

 string m_Name;//クラス名
 Method m_Method;//クラスが保持するメソッド
 int m_ClassID;//管理番号

};




#endif 