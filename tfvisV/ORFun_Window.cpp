#include "R_LibCore.h"
#include "R_Window.h"

bool IsFullScreen()
{
 return	NHLib->GetWindowNest()->IsFullScreen__();
} 


void ShiftWindowActive(){NHLib->GetWindowNest()->__ShiftWindowActive();  }
void ShiftWindowPassive(){NHLib->GetWindowNest()->__ShiftWindowPassive();  }
bool IsWindowActive(){return NHLib->GetWindowNest()->__IsWindowActive(); }
 

int  GetWindowSizeW(){return	NHLib->GetWindowNest()->GetWindowSizeW__();}

int  GetWindowSizeH(){return	NHLib->GetWindowNest()->GetWindowSizeH__();}

HWND  GetWindowHandle(){return NHLib->GetWindowNest()->GetWindowHandle__();}

HINSTANCE GetInstance(){return NHLib->GetWindowNest()->_GetInstance();}

bool MessageCheck()
{

 return 	NHLib->GetWindowNest()->MessageCheck__();

}

