#ifndef _Line_h_
#define _Line_h_
#include "GameDefine.h"


class Method;
class C_Line:public C_Set
{
public:

  C_Line()
  {
	m_ID=-1;
	m_EventType=0;
	m_BaseID=0;
	m_Indent=0;
	m_Top=true;
	m_EventNum;
  }
  C_Line* CHECK(){return (C_Line*)C_Set::CHECK();}
  C_Line* back(){return (C_Line*)C_Set::CHECK_BACK();}

  int      m_ID;
  int      m_BaseID;
  int      m_EventType;
  string   m_Target;
  int      m_Indent;
  bool     m_Top;
  int      m_EventNum;

  string   m_Text;

  C_String  m_Ref;

  
};


#endif 