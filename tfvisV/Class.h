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

 string m_Name;//�N���X��
 Method m_Method;//�N���X���ێ����郁�\�b�h
 int m_ClassID;//�Ǘ��ԍ�

};




#endif 