#include "RL_TextureNest.h"
#include "RL_Texture.h"
#include "R_LibCore.h"


RC_TextureNest::RC_TextureNest()
{
 List_Object=NULL;

} 


RC_TextureNest::~RC_TextureNest()
{
 SAFE_DELETE(List_Object);

}

bool RC_TextureNest::DeviceLost_Release()
{
 
 DeviceLost_TaskItemRelease(List_Object);
 
 return 0;
}
bool RC_TextureNest::DeviceLost_Restore()
{

 if(DeviceLost_TaskItemRestore(List_Object))
 {return 1;}

 return 0;
}





bool RC_TextureNest::Create()
{
 

 List_Object = new C_ListNest;
 

 if(List_Object == NULL)
 {
   _ASSERT_EXPR( 0, TEXT("CDevice_R2D::Create \n ÉäÉXÉgê∂ê¨Ç…é∏îs") );
   return 1;
 }

    
 return 0;
}
