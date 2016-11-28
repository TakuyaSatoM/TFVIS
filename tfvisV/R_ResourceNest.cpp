#include "R_ResourceNest.h"


bool RC_ResourceNest::DeviceLost_TaskItemRelease(C_ListNest* list)
{
 C_List* Object_O = NULL;
 RC_ResourceList* Object=NULL;

  
 
  for(;;)
  {
   Object_O = list->NextList(Object_O);
   if(Object_O == NULL){break;}
   Object = (RC_ResourceList*)Object_O;

   if(Object->DeviceLost_Release())
   {return 1;}
  }
 


  return 0;
}

 bool RC_ResourceNest::DeviceLost_TaskItemRestore(C_ListNest* list)
 {
  C_List* Object_O = NULL;
 RC_ResourceList* Object=NULL;


 
  for(;;)
  {
   Object_O = list->NextList(Object_O);
   if(Object_O == NULL){break;}
   Object = (RC_ResourceList*)Object_O;

   if(Object->DeviceLost_Restore())
   {return 1;}
  }
 


  return 0;
 
 }