
#include "R_Input.h"
#include "R_LibCore.h"
#include "R_Screen.h"
#include "R_MathVector.h"
#include "R_Window.h"
bool m_DoubleClick;
 
RC_InputNest::RC_InputNest(HINSTANCE hInstance,HWND hWnd)
{
 m_lpDI = NULL;
 m_lpDIDevice = NULL;

HRESULT hr = ::CoCreateInstance(CLSID_DirectInput8,
                                NULL,
                                CLSCTX_ALL,
                                IID_IDirectInput8,
                                (void **)&m_lpDI );
if (FAILED(hr)) {
  _ASSERT_EXPR( 0, TEXT("RC_Input::コンストラクタ \n DirectInputError") );return;
}
hr = m_lpDI->Initialize(hInstance, DIRECTINPUT_VERSION);
if (FAILED(hr)) {
  _ASSERT_EXPR( 0, TEXT("RC_Input::コンストラクタ \n DirectInputError") );return;
}


if(
  FAILED(m_lpDI->CreateDevice( GUID_SysMouse, &m_lpDIDevice, NULL ))
  ){_ASSERT_EXPR( 0, TEXT("InputDevice::コンストラクタ \n DirectInputError") );return;}


if(
  FAILED(m_lpDI->CreateDevice( GUID_SysKeyboard, &m_KeyDevice, NULL ))
  ){_ASSERT_EXPR( 0, TEXT("InputDevice::コンストラクタ \n DirectInputError") );return;}


DIOBJECTDATAFORMAT obj[7];
    DIDATAFORMAT format;
    format.dwSize       = sizeof(DIDATAFORMAT);
    format.dwObjSize    = sizeof(DIOBJECTDATAFORMAT);
    format.dwFlags      = DIDF_RELAXIS;
    format.dwDataSize   = 16;
    format.dwNumObjs    = 7;
    format.rgodf        = obj;

    obj[0].dwOfs        = 0;
    obj[0].pguid        = &GUID_XAxis;
    obj[0].dwType       = DIDFT_ANYINSTANCE | DIDFT_AXIS;
    obj[0].dwFlags      = 0;
    obj[1].dwOfs        = 4;
    obj[1].pguid        = &GUID_YAxis;
    obj[1].dwType       = DIDFT_ANYINSTANCE | DIDFT_AXIS;
    obj[1].dwFlags      = 0;
    obj[2].dwOfs        = 8;
    obj[2].pguid        = &GUID_ZAxis;
    obj[2].dwType       = DIDFT_ANYINSTANCE | DIDFT_OPTIONAL | DIDFT_AXIS;
    obj[2].dwFlags      = 0;
    obj[3].dwOfs        = 12;
    obj[3].pguid        = NULL;
    obj[3].dwType       = DIDFT_ANYINSTANCE | DIDFT_BUTTON;
    obj[3].dwFlags      = 0;
    obj[4].dwOfs        = 13;
    obj[4].pguid        = NULL;
    obj[4].dwType       = DIDFT_ANYINSTANCE | DIDFT_BUTTON;
    obj[4].dwFlags      = 0;
    obj[5].dwOfs        = 14;
    obj[5].pguid        = NULL;
    obj[5].dwType       = DIDFT_ANYINSTANCE | DIDFT_OPTIONAL | DIDFT_BUTTON;
    obj[5].dwFlags      = 0;
    obj[6].dwOfs        = 15;
    obj[6].pguid        = NULL;
    obj[6].dwType       = DIDFT_ANYINSTANCE | DIDFT_OPTIONAL | DIDFT_BUTTON;
    obj[6].dwFlags      = 0;

m_lpDIDevice->SetDataFormat( &format );
m_lpDIDevice->SetCooperativeLevel( hWnd, DISCL_FOREGROUND | DISCL_NONEXCLUSIVE );
m_lpDIDevice->SetCooperativeLevel( hWnd, DISCL_BACKGROUND | DISCL_NONEXCLUSIVE );
m_lpDIDevice->Acquire();

m_KeyDevice->SetDataFormat( &c_dfDIKeyboard );
m_KeyDevice->SetCooperativeLevel(hWnd,DISCL_NONEXCLUSIVE | DISCL_BACKGROUND); 
m_KeyDevice->Acquire();



m_MouseR.Init();
m_MouseM.Init();
m_MouseL.Init();

 MouseX = MouseY = 0;



 for(int i=0;i<256;i++)
 {
  m_KeyNow[i]=m_KeyLast[i]=0;
 }

}


bool RC_InputNest::__IsMouseInBox(int x,int y,int w,int h)
{
 if(MouseX >= x && MouseX < x+w && MouseY >= y && MouseY < y+h) 
 {return 1;}

 return 0;
}



RC_InputNest::~RC_InputNest()
{
 m_lpDIDevice->Unacquire();
 m_lpDIDevice->Release();
 m_KeyDevice->Unacquire();
 m_KeyDevice->Release();
 m_lpDI->Release();
}


void RC_InputNest::Refresh()
{

 POINT point;
 GetCursorPos(&point);
 ScreenToClient(NHLib->GetWindowNest()->GetWindowHandle__(), &point);	
 MouseX=point.x;MouseY=point.y;
	
DIMOUSESTATE dims;
 m_lpDIDevice->GetDeviceState( sizeof(DIMOUSESTATE), &dims );

V_MouseWheel = dims.lZ;

if(V_MouseWheel > 0){V_MouseWheel = 1;}
if(V_MouseWheel < 0){V_MouseWheel = -1;}

m_MouseL.m_Shutout=false;
m_MouseL.m_Push_Before=m_MouseL.m_Push_Now;
if(dims.rgbButtons[0]){m_MouseL.m_Push_Now=true;}
else{m_MouseL.m_Push_Now=false;}

m_MouseM.m_Shutout=false;
m_MouseM.m_Push_Before=m_MouseM.m_Push_Now;
if(dims.rgbButtons[2]){m_MouseM.m_Push_Now=true;}
else{m_MouseM.m_Push_Now=false;}

m_MouseR.m_Shutout=false;
m_MouseR.m_Push_Before=m_MouseR.m_Push_Now;
if(dims.rgbButtons[1]){m_MouseR.m_Push_Now=true;}
else{m_MouseR.m_Push_Now=false;}


 for(int i=0;i<256;i++)
 {
  m_KeyLast[i]=m_KeyNow[i];
 }
 m_KeyDevice->GetDeviceState(sizeof(m_KeyNow), &m_KeyNow);





}
bool  RC_InputNest::NowPush(int key)
{
 if( (m_KeyNow[key] & 0x80) && !(m_KeyLast[key] & 0x80)  )
 {
  return 1;
 }
 return 0;
}
bool  RC_InputNest::Up(int key)
{
 if( !(m_KeyNow[key] & 0x80) && (m_KeyLast[key] & 0x80)  )
 {
  return 1;
 }
 return 0;
}
bool  RC_InputNest::LongPush(int key)
{
 if( (m_KeyNow[key] & 0x80) && (m_KeyLast[key] & 0x80)  )
 {
  return 1;
 }
 return 0;
}
bool  RC_InputNest::Push(int key)
{
 if(m_KeyNow[key] & 0x80)
 {
  return 1;
 }
 return 0;
}
