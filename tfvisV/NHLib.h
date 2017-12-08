#ifndef _NHLib_h_
#define _NHLib_h_
#include <d3d9.h>
#include <d3dx9.h>
#include "Common.h"
#include "R_Set.h"
#include "ORStr_Window.h"
#include "R_ScreenData.h"

char* GetExePath();
//NHLib����
bool CreateNHLibrary(RS_WindowSetData* windwoSetData);
//NHLib�I��
int EndNHLibrary();

//�`�揈��������
bool DXSceneStart();
//�`�攽�f
bool DXSceneEnd();

double GetLoopTime();
void  SetFrameRate(int n);

bool IsDeviceLost();

void CallDeviceLost();
void ClearDeviceLost();


//���[�v���b�Z�[�W�`�F�b�N
bool MessageCheck();


inline LPDIRECT3DDEVICE9	GetDirectXDevice();

//�t���X�N���[���Ȃ�1��Ԃ�
inline bool IsFullScreen();
//�E�B���h�E���T�C�Y��Ԃ�
inline int  GetWindowSizeW();
//�E�B���h�E�c�T�C�Y��Ԃ�
inline int  GetWindowSizeH();
//�E�B���h�E�n���h����Ԃ�
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