#ifndef _Method_h_
#define _Method_h_
#include "GameDefine.h"
#include "Line.h"
#include "Exe.h"

class C_Class;
class Method:public C_Set
{
public:

  Method(){

	m_Name="";
	m_MethodID=-1;
	m_EDsXPos=-1;
	m_CallNum=0;
	m_UseYArea=20;
	m_Recursion=0;
  }
  Method* next(){return (Method*)C_Set::CHECK();}

  string m_Name;
  string m_FullName;

  int		m_MethodID;
  C_Class*  m_MyClass;
  int		m_CallNum;
  int         m_Recursion;
  int m_UseYArea;

  int m_EDsXPos;

  C_Line m_Line;
  void loadLine();

};




class LooptMemo:public C_Set
{
public:

  LooptMemo(){}
  LooptMemo(Exe* start,int x)
  {
    m_Start=start;
	m_StartX=x;
  }
   LooptMemo* next(){return ( LooptMemo*)C_Set::CHECK();}
   LooptMemo* back(){return ( LooptMemo*)C_Set::CHECK_BACK();}

  Exe* m_Start;
  int m_StartX;

};


class MethodExe:public C_Set
{
public:

	static int ms_ExistNum;

	bool        m_NowOpen;
	int         m_ID;
	Method*   m_Method;
	int         m_MethodExeNum;
	LooptMemo     m_LtMemory;
	int         m_XWide;
	D3DXVECTOR4 m_EDsRect;
	float       m_CenterXPos;

	Exe*        m_StartExe;
	Exe*        m_EndExe;

	MethodExe()
	{
		m_NowOpen=false;
		m_XWide=0;
		m_ID=0;
		m_Method=NULL;
		m_MethodExeNum=0;
		 m_CenterXPos=0;
		 m_StartExe=m_EndExe=NULL;
	}
	MethodExe* next(){return (MethodExe*)C_Set::CHECK();}
	
};



#endif 