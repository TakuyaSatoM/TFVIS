#include "R_RenderingTextureNest.h"
#include "R_RenderingTexture.h"
#include "R_LibCore.h"

RC_RenderingTexture* CreateNewRenderingTexture(int sizeW,int sizeH)
{
	return NHLib->GetRenderingTextureNest()->NewRenderingTexture__(sizeW,sizeH);
} 