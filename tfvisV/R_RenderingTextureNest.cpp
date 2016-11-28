#include "R_RenderingTextureNest.h"
#include "R_RenderingTexture.h"


RC_RenderingTextureNest::RC_RenderingTextureNest()
{
 
 List_RenderingTexture=NULL;

}
 



RC_RenderingTextureNest::~RC_RenderingTextureNest()
{
 SAFE_DELETE(List_RenderingTexture);
 
}

RC_RenderingTexture* RC_RenderingTextureNest::NewRenderingTexture__(int sizeW,int sizeH)
{
RC_RenderingTexture* newRenderingTexture= new RC_RenderingTexture(List_RenderingTexture);

if(newRenderingTexture==NULL){return NULL;}

 if(newRenderingTexture->Build(sizeW,sizeH))
 {
	 newRenderingTexture->Delete();
  return NULL;
 }


 return newRenderingTexture;

}

bool RC_RenderingTextureNest::Create()
{


 List_RenderingTexture = new C_ListNest;
 

 if(List_RenderingTexture == NULL )
 {
   _ASSERT_EXPR( 0, TEXT("RCD_RenderingTexture::Create \n ÉäÉXÉgê∂ê¨Ç…é∏îs") );
   return 1;
 }




	return 0;
}

bool RC_RenderingTextureNest::DeviceLost_Release()
{
 if(DeviceLost_TaskItemRelease(List_RenderingTexture))
 {return 1;}

return 0;
}


bool RC_RenderingTextureNest::DeviceLost_Restore()
{
 if(DeviceLost_TaskItemRestore(List_RenderingTexture))
 {return 1;}

 return 0;
}