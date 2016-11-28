#include "R_LibCore.h"
#include "R_Window.h"
#include "R_Renderer.h"
#include "R_DXWord.h"
#include "R_RenderingTextureNest.h"
#include "R_Screen.h"
#include "R_Input.h"
#include "ORStr_Window.h"

#include "RL_FigureNest.h"
#include "RL_TextureNest.h"
#include "R_Math.h"

RC_NHLib* NHLib=NULL;

 

///コンストラクタ
RC_NHLib::RC_NHLib()
{
 m_WindowNest=NULL;
 m_RendererNest =NULL;
 m_DXWordNest=NULL;
 m_TextureNest=NULL;
 m_ScreenNest=NULL;
 m_IsDeviceLost=0;
 SetFrameRate__(1);

 m_FigureNest=NULL;

 m_Speed=1.0f;
 timeBeginPeriod(1);
 m_LeastFrameTime = timeGetTime();
 m_LoopTime = 0;

}

void RC_NHLib::FrameRefresh()
{
 DWORD time = timeGetTime();

 DWORD past = time -m_LeastFrameTime;

 m_LoopTime = past/1000.0 ;


 if(past < 1000./m_FrameRate)
 {Sleep(1000./m_FrameRate-past);}

 m_LeastFrameTime = time;




}

RC_NHLib::~RC_NHLib()
{
 Release();


}

UINT RC_NHLib::GetNowVRAMSize__()
{
 return GetDirectXDevice__()->GetAvailableTextureMem();
}

//ライブラリ生成
bool RC_NHLib::Create(RS_WindowSetData* windowSetData)
{
 m_WindowNest = new RC_WindowNest;
 
 if(m_WindowNest->Create(windowSetData))
 {
  CheckBox("ウィンドウ生成処理に失敗");
  return 1;
 }

 char full[256];
 char name[256];

 GetModuleFileNameA(m_WindowNest->_GetInstance(),full,256);
 FileNameAnalysis(full,APath,name);


 m_RendererNest = new RC_RendererNest;

 if(m_RendererNest->Init())
 {
  CheckBox("レンダラー生成処理に失敗");
  return 1;
 }
 m_MaxVRAMSize=GetNowVRAMSize__();

 m_ScreenNest = new  RC_ScreenNest;
 if(m_ScreenNest->Create() )
 {
  CheckBox("スクリーン生成処理に失敗");
  return 1;
 }
 


 m_DXWordNest = new RC_DXWordNest;

 if(m_DXWordNest->Create())
 {
  CheckBox("DXWord生成処理に失敗");
  return 1;
 }


 m_FigureNest = new RC_FigureNest;
 if(m_FigureNest->Create())
 {
  CheckBox("FigureNest生成処理に失敗");
  return 1;
 }


 m_TextureNest = new RC_TextureNest;

 if(m_TextureNest->Create())
 {
  CheckBox("texturenest生成処理に失敗");
  return 1;
 }

 


 m_RenderingTextureNest = new RC_RenderingTextureNest;

 if(m_RenderingTextureNest->Create())
 {
  CheckBox("RenderingNest生成処理に失敗");
  return 1;
 }



 m_InputNest = new RC_InputNest(windowSetData->m_hInstance,m_WindowNest->GetWindowHandle__());




 //システムチェック
 {
	D3DCAPS9  d3dCaps;     
	GetDirectXDevice__()->GetDeviceCaps(&d3dCaps);
   

	if(d3dCaps.MaxTextureWidth < 1024 || d3dCaps.MaxTextureHeight < 1024)
	{
	 CheckBox("ビデオカードが対応していません(テクスチャサイズ)");
	 return 1;
	}
    
 }



 return 0;
}


void RC_NHLib::Release()
{

 SAFE_DELETE(m_InputNest);
 SAFE_DELETE(m_RenderingTextureNest);
 
 SAFE_DELETE(m_TextureNest);
 SAFE_DELETE(m_FigureNest);
 SAFE_DELETE(m_DXWordNest);
 SAFE_DELETE(m_ScreenNest);
 SAFE_DELETE(m_RendererNest);
 SAFE_DELETE(m_WindowNest);
 
}

LPDIRECT3DDEVICE9	RC_NHLib::GetDirectXDevice__()
{
 return	m_RendererNest->m_pD3DDevice;
}




bool RC_NHLib::DeviceLost_Release()
{
	m_DXWordNest->DeviceLost_Release();
	m_FigureNest->DeviceLost_Release();
	m_TextureNest->DeviceLost_Release();
	
	m_RenderingTextureNest->DeviceLost_Release();
 return 0;
}

bool RC_NHLib::DeviceLost_Restore()
{


	if(m_DXWordNest->DeviceLost_Restore())
	{return 1;}

	if(m_FigureNest->DeviceLost_Restore())
	{return 1;}

	if(m_TextureNest->DeviceLost_Restore())
	{return 1;}

	

	if(m_RenderingTextureNest->DeviceLost_Restore())
	{return 1;}



 return 0;
}