#include "R_LibCore.h"
#include "ORStr_Window.h"
#include "R_Renderer.h"
#include "R_Window.h"
#include "R_Input.h"

 
UINT GetMaxVRAMSize()
{
	return NHLib->GetMaxVRAMSize__();
}
UINT GetNowVRAMSize()
{
	return NHLib->GetNowVRAMSize__();
}


char* GetExePath()
{
 
 return NHLib->APath;
}

//
bool CreateNHLibrary(RS_WindowSetData* windowSetData)
{
 NHLib = new RC_NHLib;

 if(!NHLib){return 1;}


 if(NHLib->Create(windowSetData)){return 1;}

 return 0;
}

int EndNHLibrary()
{
 int param= NHLib->GetWindowNest()->GetMessage__()->wParam;

 delete NHLib;


 return param;
}


bool DXSceneStart()
{
	WINDOWPLACEMENT wndpl;

	GetWindowPlacement(NHLib->GetWindowNest()->GetWindowHandle__(), &wndpl);	// ウインドウの状態を取得
	
	if((wndpl.showCmd != SW_HIDE) && (wndpl.showCmd != SW_MINIMIZE) &&
		(wndpl.showCmd != SW_SHOWMINIMIZED) &&(wndpl.showCmd != SW_SHOWMINNOACTIVE) && NHLib->GetWindowNest()->__IsWindowActive())
	{
	 NHLib->GetInputNest()->Refresh();

	 NHLib->GetRendererNest()->SceneStart();
	 return 0;
	}
	



	


return 1;
}

bool DXSceneEnd()
{

	NHLib->GetRendererNest()->SceneEnd();

	NHLib->FrameRefresh();

	return 0;
}

LPDIRECT3DDEVICE9	GetDirectXDevice()
{
 return	NHLib->GetDirectXDevice__();
}


bool IsDeviceLost()
{
	return NHLib->IsDeviceLost__();
}

void CallDeviceLost()
{
	NHLib->CallDeviceLost__();
}

void ClearDeviceLost()
{
	NHLib->ClearDeviceLost__();
}
	 

void  SetFrameRate(int n)
{
	NHLib->SetFrameRate__(n);
}

double GetLoopTime()
{


 if(NHLib->LOOPTIME() > NHLib->ONEFRAMETIME())
 {
  //return NHLib->ONEFRAMETIME();
 }

 return	NHLib->ONEFRAMETIME();
}
