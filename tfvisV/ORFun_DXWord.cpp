#include "R_DXWord.h"
#include "R_LibCore.h"


RECT DWordNowSize()
{
	return NHLib->GetDXWordNest()->_DWordNowSize();
}

void DWordRefresh()
{
SAFE_RELEASE(NHLib->GetDXWordNest()->Sprite);
NHLib->GetDXWordNest()->Create();
} 

int DWordCreateFont(int size)
{
	return NHLib->GetDXWordNest()->CreateFont_(size);
}
void DWordDrawStart()
{
	NHLib->GetDXWordNest()->DrawStart_();
	
}

char* DWordBuffer()
{
	return NHLib->Text;
}

void DWordDrawEnd()
{
	NHLib->GetDXWordNest()->DrawEnd_();
}
void DWordColor(D3DCOLOR color)
{
	NHLib->GetDXWordNest()->SetColor_(color);
}
void DWordFormat(DWORD format)
{
	NHLib->GetDXWordNest()->SetFormat_(format);
}

void DWordArea(int top,int bottom,int left,int right)
{
NHLib->GetDXWordNest()->SetAreaTop(top);
NHLib->GetDXWordNest()->SetAreaBottom(bottom);
NHLib->GetDXWordNest()->SetAreaLeft(left);
NHLib->GetDXWordNest()->SetAreaRight(right);

}

RECT DWordGetRect(){return NHLib->GetDXWordNest()->GetRect();}

void DWordArea_W(int x,int y,int w,int h)
{
 DWordArea(y,y+h,x,x+w);
}

void DWordArea_W(int x,int y,int w,int h,int m)
{
  DWordArea_W(x*m,y*m,w*m,h*m);
}

void DWordArea_W(D3DXVECTOR4 v,int m)
{
  DWordArea_W(v.x*m,v.y*m,v.z*m,v.w*m);
}

void DWordAreaTop(int top)
{
	NHLib->GetDXWordNest()->SetAreaTop(top);
}
void DWordAreaBottom(int bottom)
{
	NHLib->GetDXWordNest()->SetAreaBottom(bottom);
}
void DWordAreaLeft(int left)
{
	NHLib->GetDXWordNest()->SetAreaLeft(left);
}
void DWordAreaRight(int right)
{
	NHLib->GetDXWordNest()->SetAreaRight(right);
}

void DWordAreaTopAdd(int top)
{
	NHLib->GetDXWordNest()->AddAreaTop(top);
}
void DWordAreaBottomAdd(int bottom)
{
	NHLib->GetDXWordNest()->AddAreaBottom(bottom);
}
void DWordAreaLeftAdd(int left)
{
	NHLib->GetDXWordNest()->AddAreaLeft(left);
}
void DWordAreaRightAdd(int right)
{
	NHLib->GetDXWordNest()->AddAreaRight(right);
}

void DWordDrawText(int font,LPCSTR pstring)
{
	NHLib->GetDXWordNest()->DrawText_(font,pstring);
}