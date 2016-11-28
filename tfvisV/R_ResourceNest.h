#ifndef _R_ResourceNest_h_
#define _R_ResourceNest_h_
#include "Common.h"
#include "R_ResourceList.h"


class RC_ResourceNest
{
public:
	 

virtual bool DeviceLost_Release() = 0;
virtual bool DeviceLost_Restore() = 0;

 virtual bool DeviceLost_TaskItemRelease(C_ListNest* list);
 virtual bool DeviceLost_TaskItemRestore(C_ListNest* list);

};


#endif