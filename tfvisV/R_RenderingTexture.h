#ifndef _R_RENDERINGTEXTURE_h_
#define _R_RENDERINGTEXTURE_h_

#include "R_ScreenData.h"
#include "Common.h"
#include "R_ResourceList.h"

#define RENDERINGTEXTURE_CLEAR (1)
#define RENDERINGTEXTURE_KEEP (0)
 

class RC_Texture;

class RC_RenderingTexture:public RC_ResourceList
{
friend class RC_RenderingTextureNest;
private:

RC_Texture*             m_TextureTask;		// テクスチャ
LPDIRECT3DSURFACE9      m_Surface;	// テクスチャから取得するサーフェース
LPDIRECT3DSURFACE9		m_ZBuffer;		// テクスチャ用の深度バッファー
D3DXCOLOR  m_Color;

RC_ScreenData          m_Data;

bool  m_DeviceLost;


RC_RenderingTexture(C_ListNest* list);
bool Build(DWORD sizeW,DWORD sizeH);


virtual bool DeviceLost_Release();
virtual bool DeviceLost_Restore();

public:
bool GetDeviceLost(){return m_DeviceLost;}

RC_Texture* GetTexture(){return m_TextureTask;}

~RC_RenderingTexture();

void Clear();
void BufferClear();
void BufferClear(DWORD flag);

void Release();
void Set(bool clear);
void SetColor(D3DXCOLOR* color){m_Color =*color;}

RC_ScreenData* D(){return &m_Data;}
};


#endif