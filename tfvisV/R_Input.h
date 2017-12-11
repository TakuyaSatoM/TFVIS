
#ifndef _Input_h_
#define _Input_h_
#include "DXCommon.h"
#include <dinput.h>
#include "Common.h"

 

class RC_InputNest
{
friend class RC_NHLib;

LPDIRECTINPUT8 m_lpDI;
LPDIRECTINPUTDEVICE8 m_lpDIDevice;
LPDIRECTINPUTDEVICE8 m_KeyDevice;


int V_MouseWheel;



int MouseX,MouseY;


RC_InputNest(HINSTANCE hInstance,HWND hWnd);

public:


D3DXVECTOR3 World(int x,int y,int w,int h);

bool __IsMouseInBox(int x,int y,int w,int h);

inline int         MOUSEX(){return MouseX;}
inline int         MOUSEY(){return MouseY;}
inline int         V_MOUSEWHEEL(){return V_MouseWheel;}
inline void        MOUSESET(int x,int y){MouseX=x;MouseY=y;}



C_Input m_MouseR;
C_Input m_MouseM;
C_Input m_MouseL;


BYTE m_KeyNow[256];
BYTE m_KeyLast[256];

bool NowPush(int key);
bool Push(int key);
bool Up(int key);
bool LongPush(int key);

void Refresh();
~RC_InputNest();




};


extern bool m_DoubleClick;


#endif