#ifndef _S_Base_h_
#define _S_Base_h_
#include "GameDefine.h"

class DTCom;
class S_Base
{
public:

  S_Base();
 ~S_Base();

 virtual char _Task();


  void Menu();

  void SelectList();
  void Output();



  void Con();

  bool m_ArrowsEndOK,m_ArrowsStartOK;
  D3DXVECTOR3 m_ArrowsStart,m_ArrowsEnd;

};



#endif 