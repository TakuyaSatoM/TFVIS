#ifndef _RL_TextureNest_h_
#define _RL_TextureNest_h_
#include "DXCommon.h"
#include "R_ResourceNest.h"
#include "R_List.h"

class RC_TextureNest:public RC_ResourceNest
{
 friend class RC_Texture;
protected:
	 
 C_ListNest *List_Object;

public:
RC_TextureNest();
virtual bool Create();
~RC_TextureNest();

 virtual bool DeviceLost_Release();
 virtual bool DeviceLost_Restore();


 template <class Type> Type* CreateObject()
 {
  return new Type(List_Object,this);
 }




};




#endif