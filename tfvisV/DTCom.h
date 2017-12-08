#ifndef _DTCom_h_
#define _DTCom_h_

#include "Method.h"

#include "Exe.h"

class DTCom:public C_Set
{
  friend class C_GameNest;
  void Delete()
  {
	  m_MethodExe->m_NowOpen=false;
	  C_Set::Delete();
  }
  ~DTCom(){}

public:


  DTCom()
  {
	m_MethodExe=NULL;
	m_DS=300*2;
	m_DragStart=false;
	m_BookDelete=false;
  }
  DTCom* CHECK(){return (DTCom*)C_Set::CHECK();}

  DTAItem m_DTA; 
  MethodExe* m_MethodExe;
  Method* getMethod(){return m_MethodExe->m_Method;}

  RC_RenderingTexture* m_Render;
  C_Box m_DrawArea;
  C_Box m_RealDrawArea;
  INT2  m_Size;
  INT2  m_TexXY;
  int   m_DS;
  D3DXVECTOR2 m_BeforeXY;
  bool m_DragStart;


  bool m_BookDelete;

  static DTCom* Open(DTCom* comTop,MethodExe* methodExe);
  static DTCom* whatMousePointerCovered(DTCom* comTop,bool mostFront,bool onlyTitle);
  static DTCom* transDTDiagram(DTCom* top,DTCom* oldOne,MethodExe* newOne_MethodExe);


  static void doBookingDelete(DTCom* com);
};


#endif 