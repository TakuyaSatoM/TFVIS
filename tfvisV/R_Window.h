#ifndef _RWindow_h_
#define _RWindow_h_
#include <Windows.h>

//-------------------------------------------------------------
// WindowNestƒNƒ‰ƒX
// 		
//-------------------------------------------------------------
struct RS_WindowSetData;
 
class RC_WindowNest
{
friend class RC_NHLib;
 bool      m_FullScreen;
 int       m_WindowSizeW;
 int       m_WindowSizeH;
 int       m_WindowSleepTime;
 HWND      m_HandleWindow;
 MSG       m_Msg;
 HINSTANCE m_HandleInstance;
 TCHAR     m_WindowName[256];
 bool      m_WindowActive;

 RC_WindowNest();
 bool Create(RS_WindowSetData* windowSetData);

 public:

	 bool IsFullScreen__(){return m_FullScreen;}
	 int  GetWindowSizeW__(){return m_WindowSizeW;}
	 int  GetWindowSizeH__(){return m_WindowSizeH;}
	 HWND  GetWindowHandle__(){return m_HandleWindow;}
	 MSG* GetMessage__(){return &m_Msg;}
	 bool __IsWindowActive(){return m_WindowActive;}
	 void __ShiftWindowPassive(){m_WindowActive=0;}
	 void __ShiftWindowActive(){m_WindowActive=1;}
	 HINSTANCE _GetInstance(){return m_HandleInstance;}

	 bool MessageCheck__();

};



#endif