#include "R_Screen.h"
#include "NHLib.h"
#include "R_LibCore.h"

//////////////////////////////////////////////////////////////////////////////////////////////////////


//MeshDeviceコンストラクタ・初期化のみ
RC_ScreenNest::RC_ScreenNest()
{m_NowBuffer=NULL;}
 
//MeshDeviceデストラクタ・リストを処分
RC_ScreenNest::~RC_ScreenNest(){}

//生成 (外部からの呼び出し不可(private宣言))
bool RC_ScreenNest::Create()
{
 GetDirectXDevice()->GetViewport(&m_pBackviewport);
 m_BackBuffer_D.SetSize(m_pBackviewport.Width,m_pBackviewport.Height);
 
 return 0;
}

 //-------------------------------------------------------------
// バックバッファ退避
//-------------------------------------------------------------
void RC_ScreenNest::HideBackBuffer_m()
{
 GetDirectXDevice()->GetViewport(&m_pBackviewport);
 GetDirectXDevice()->GetRenderTarget(0, &m_pBackbuffer);
 GetDirectXDevice()->GetDepthStencilSurface( &m_pBackZ); 
}
 //-------------------------------------------------------------
// バックバッファ復帰
//-------------------------------------------------------------
void RC_ScreenNest::RevaivalBackBuffer_m()
{
 m_NowBuffer=NULL;

 GetDirectXDevice()->SetRenderTarget(0, m_pBackbuffer);
 GetDirectXDevice()->SetDepthStencilSurface(m_pBackZ);	
 GetDirectXDevice()->SetViewport(&m_pBackviewport);

 SAFE_RELEASE(m_pBackbuffer);
 SAFE_RELEASE(m_pBackZ);
}

	

RC_RenderingTexture* CheckNowActiveBuffer(){return NHLib->GetScreenNest()->NOWBUFFER();}


 //-------------------------------------------------------------
// バックバッファ退避
//-------------------------------------------------------------
void HideBackBuffer(){NHLib->GetScreenNest()->HideBackBuffer_m();}

//-------------------------------------------------------------
// バックバッファ復帰
//-------------------------------------------------------------
void RevaivalBackBuffer(){NHLib->GetScreenNest()->RevaivalBackBuffer_m();}

RC_ScreenData* GetNowBufferData()
{
 RC_RenderingTexture* buffer=CheckNowActiveBuffer();
 if(buffer){return buffer->D();}
 return NHLib->GetScreenNest()->BACKBUFFER_DATA();
}