#ifndef _NHLib_h_
#define _NHLib_h_
#include <d3d9.h>
#include <d3dx9.h>
#include "Common.h"
#include "R_Set.h"
#include "ORStr_Window.h"
#include "R_ScreenData.h"

char* GetExePath();
//NHLib生成
bool CreateNHLibrary(RS_WindowSetData* windwoSetData);
//NHLib終了
int EndNHLibrary();

//描画処理初期化
bool DXSceneStart();
//描画反映
bool DXSceneEnd();

double GetLoopTime();
void  SetFrameRate(int n);

bool IsDeviceLost();

void CallDeviceLost();
void ClearDeviceLost();


//ループメッセージチェック
bool MessageCheck();


inline LPDIRECT3DDEVICE9	GetDirectXDevice();

//フルスクリーンなら1を返す
inline bool IsFullScreen();
//ウィンドウ横サイズを返す
inline int  GetWindowSizeW();
//ウィンドウ縦サイズを返す
inline int  GetWindowSizeH();
//ウィンドウハンドルを返す
inline HWND  GetWindowHandle();
inline HINSTANCE GetInstance();

void ShiftWindowActive();
void ShiftWindowPassive();
bool IsWindowActive();

inline RECT DWordGetRect();
inline int DWordCreateFont(int size);
inline void DWordDrawStart();
inline void DWordDrawEnd();
inline void DWordColor(D3DCOLOR color);
inline void DWordFormat(DWORD format);
inline void DWordArea(int top,int bottom,int left,int right);
inline void DWordArea_W(int x,int y,int w,int h);
inline void DWordArea_W(int x,int y,int w,int h,int mult);
inline void DWordArea_W(D3DXVECTOR4 v,int mult);
inline void DWordAreaTop(int top);
inline void DWordAreaBottom(int bottom);
inline void DWordAreaLeft(int left);
inline void DWordAreaRight(int right);
inline void DWordAreaTopAdd(int top);
inline void DWordAreaBottomAdd(int bottom);
inline void DWordAreaLeftAdd(int left);
inline void DWordAreaRightAdd(int right);
inline void DWordDrawText(int font,LPCSTR pstring);
inline char* DWordBuffer();
inline RECT DWordNowSize();
bool GetDXWordState();
void DWordRefresh();

#include "RL_TextureLib.h"



#include "R_RenderingTexture.h"
RC_RenderingTexture* CreateNewRenderingTexture(int sizeW,int sizeH);
RC_RenderingTexture* CheckNowActiveBuffer();
RC_ScreenData* GetNowBufferData();

void ZBufferClear();

UINT GetMaxVRAMSize();
UINT GetNowVRAMSize();

void HideBackBuffer();
void RevaivalBackBuffer();



#include "R_Input.h"
RC_InputNest* RL_INPUT();
bool IsMouseInBox(int x,int y,int w,int h);

#include "R_Strings.h"


#include "RL_FigureLib.h"


#include "R_MathVector.h"
#include "R_Math.h"

#endif