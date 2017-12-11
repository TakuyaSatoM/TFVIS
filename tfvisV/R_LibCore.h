#ifndef _RLibCore_h_
#define _RLibCore_h_
#include <d3d9.h>
#include <d3dx9.h>
#include "R_ResourceNest.h"

//-------------------------------------------------------------
// NHLibクラス
// 単一のグローバル関数とする
//	
//		 
//-------------------------------------------------------------
class RC_WindowNest;
class RC_RendererNest;
class RC_DXWordNest;
class RC_RenderingTextureNest;
class RC_ScreenNest;
class RC_InputNest;
class RC_ChipBoardNest;

struct RS_WindowSetData;

class RC_FigureNest;
class RC_TextureNest;

class RC_NHLib :public RC_ResourceNest
{
 friend bool CreateNHLibrary(RS_WindowSetData* windwoSetData);
 friend int EndNHLibrary();
 friend char* GetExePath();


 DWORD  m_LeastFrameTime;
 double m_LoopTime;
 double m_Speed;
 UINT   m_MaxVRAMSize;
 int    m_FrameRate;
 double m_OneFrameTime;

 bool m_IsDeviceLost;

 RC_WindowNest* m_WindowNest;
 RC_RendererNest* m_RendererNest;
 RC_DXWordNest* m_DXWordNest;
 RC_RenderingTextureNest*  m_RenderingTextureNest;
 RC_ScreenNest* m_ScreenNest;
 RC_InputNest* m_InputNest;



 RC_FigureNest* m_FigureNest;
 RC_TextureNest* m_TextureNest;

  RC_NHLib();
  ~ RC_NHLib();


  bool Create(RS_WindowSetData* windwoSetData);
  void Release();

 public:
	  char Text[512];
	  char APath[256];

	 virtual bool DeviceLost_Release();
     virtual bool DeviceLost_Restore();


	 RC_WindowNest* GetWindowNest(){return m_WindowNest;}
	 RC_RendererNest* GetRendererNest(){return m_RendererNest;}
	 RC_DXWordNest* GetDXWordNest(){return m_DXWordNest;}
	
	 RC_RenderingTextureNest*  GetRenderingTextureNest(){return m_RenderingTextureNest;}
	 RC_ScreenNest* GetScreenNest(){return m_ScreenNest;}
	 RC_InputNest* GetInputNest(){return m_InputNest;}


	 RC_FigureNest* GetFigureNest(){return m_FigureNest;}
	 RC_TextureNest* GetTextureNest(){return m_TextureNest;}

	 void CallDeviceLost__(){m_IsDeviceLost=1;}
	 void ClearDeviceLost__(){m_IsDeviceLost=0;}
	 UINT GetMaxVRAMSize__(){return m_MaxVRAMSize;}
	 UINT GetNowVRAMSize__();
	 double ONEFRAMETIME(){return m_OneFrameTime;}
	 void   SetFrameRate__(int n)
	 {
	  m_FrameRate=n;
	  m_OneFrameTime = 1./m_FrameRate;
	 }
	 
	 bool IsDeviceLost__(){return m_IsDeviceLost;}

	 void FrameRefresh();
	 inline double LOOPTIME(){return m_LoopTime/**m_Speed*/;}

	 LPDIRECT3DDEVICE9	GetDirectXDevice__();

};


extern RC_NHLib* NHLib;

#endif