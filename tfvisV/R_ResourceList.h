#ifndef _R_ResourceList_h_
#define _R_ResourceList_h_

#include "R_List.h"
 
class RC_ResourceList:public C_List
{

 public:

 virtual bool DeviceLost_Release()=0;
 virtual bool DeviceLost_Restore()=0;
 


 RC_ResourceList(C_ListNest* mother);
 
 

 

};


#endif