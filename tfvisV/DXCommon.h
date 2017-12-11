#ifndef _DXCommon_h_
#define _DXCommon_h_

#include <d3d9.h>
#include <d3dx9.h>
#define DIRECTINPUT_VERSION  0x0800

#include "Common.h"

class C_Input 
{
 friend class RC_InputNest;
 friend class RC_Button;
 friend class RS_Set_Button;

 bool m_Push_Now;
 bool m_Push_Before;
 bool m_Shutout;
 

 C_Input()
 {
  Init();
 }


 public:

 bool NowPush(){return m_Push_Now && !m_Push_Before && !m_Shutout; }
 bool Push(){return m_Push_Now && !m_Shutout; }
 bool UP(){return m_Push_Before && !m_Push_Now && !m_Shutout; }
 void Shutout(){m_Shutout=true;}
 


  void Init()
 {
  m_Push_Now=false;
  m_Push_Before=false;
  m_Shutout=false;
 }

 
};





#endif