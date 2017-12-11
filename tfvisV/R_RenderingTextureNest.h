#ifndef _R_RenderingNest_h_
#define _R_RenderingNest_h_
#include <d3d9.h>
#include <d3dx9.h>
#include "R_ResourceList.h"
#include "R_ResourceNest.h"
#include "Common.h"
 

class RC_RenderingTexture;
class RC_RenderingTextureNest:public RC_ResourceNest
{
friend class RC_RenderingTexture;
friend class RC_NHLib;
protected:

C_ListNest *List_RenderingTexture;


public:
RC_RenderingTextureNest();
virtual bool Create();
~RC_RenderingTextureNest();


RC_RenderingTexture* NewRenderingTexture__(int sizeW,int sizeH);


virtual bool DeviceLost_Release();
virtual bool DeviceLost_Restore();

};




#endif