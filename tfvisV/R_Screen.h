

#ifndef _R_Screen_h_
#define _R_Screen_h_
#include "R_ScreenData.h"


class RC_RenderingTexture;
class RC_ScreenNest
{
	 

 friend class RC_NHLib;

 friend class RC_RenderingTexture;



 // バックバッファーの退避用
 RC_RenderingTexture*    m_NowBuffer;
 D3DVIEWPORT9            m_pBackviewport;
 LPDIRECT3DSURFACE9      m_pBackbuffer;	// バックバッファー
 LPDIRECT3DSURFACE9		 m_pBackZ;		// バックバッファー用の深度バッファ
 RC_ScreenData           m_BackBuffer_D;

 RC_ScreenNest();
 ~RC_ScreenNest();

 bool Create();
 
public:
	RC_ScreenData* BACKBUFFER_DATA(){return &m_BackBuffer_D;}

 //レンダリングバッファ
 void HideBackBuffer_m();
 void RevaivalBackBuffer_m();


 //レンダリング情報の設定
 
 RC_RenderingTexture* NOWBUFFER(){return m_NowBuffer;}
};

RC_ScreenData* GetNowBufferData();

#endif