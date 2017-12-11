#ifndef _ORStr_Window_h_
#define _ORStr_Window_h_
#include <d3d9.h>
#include <d3dx9.h>
#include "Common.h"

struct RS_WindowSetData
{
 HINSTANCE m_hInstance;
 int m_WindowSizeW;
 int m_WindowSizeH;
 bool m_IsFullScreen;
 TCHAR*     m_WindowName;

};
 


#endif