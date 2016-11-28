#ifndef _R_DXWord_h_
#define _R_DXword_h_
#include <d3d9.h>
#include <d3dx9.h>
#include "Common.h"
#include <string>
#include "R_ResourceNest.h"
using namespace std;
#define MAXFONTNUM (50)

 
class RC_DXWordNest:public RC_ResourceNest
{friend void DWordRefresh();
protected:
int UseNow;
LPD3DXSPRITE Sprite;
LPD3DXFONT   FontArray[MAXFONTNUM];

RECT DrawArea;
D3DCOLOR Color;
DWORD Format;

public:
RC_DXWordNest();
virtual bool Create();
~RC_DXWordNest();

virtual bool DeviceLost_Release();
virtual bool DeviceLost_Restore();

RECT _DWordNowSize(){return DrawArea;}
void DrawText_(int font,LPCSTR pstring);
int CreateFont_(int size);
void DrawStart_();
void DrawEnd_();
void SetColor_(D3DCOLOR color){Color=color;}
void SetFormat_(DWORD format){Format=format;}
void SetAreaTop(int top){DrawArea.top=top;}
void SetAreaBottom(int bottom){DrawArea.bottom=bottom;}
void SetAreaLeft(int left){DrawArea.left=left;}
void SetAreaRight(int right){DrawArea.right=right;}
RECT GetRect(){return DrawArea;}
void AddAreaTop(int top){DrawArea.top+=top;}
void AddAreaBottom(int bottom){DrawArea.bottom+=bottom;}
void AddAreaLeft(int left){DrawArea.left+=left;}
void AddAreaRight(int right){DrawArea.right+=right;}
};




#endif