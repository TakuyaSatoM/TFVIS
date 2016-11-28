#include "R_DXWord.h"
#include "NHLib.h"

bool dxwordSystemNow=false;


bool GetDXWordState(){return dxwordSystemNow;}

RC_DXWordNest::RC_DXWordNest()
{
 Sprite=NULL;
 UseNow=0;
  
 for(int ct_f=0;ct_f<MAXFONTNUM;ct_f++)
 {
  FontArray[ct_f]=NULL;
 }

}

RC_DXWordNest::~RC_DXWordNest()
{

 SAFE_RELEASE(Sprite);

 for(int ct_f=0;ct_f<MAXFONTNUM;ct_f++)
 {
 SAFE_RELEASE(FontArray[ct_f]);
 }

}

bool RC_DXWordNest::Create()
{
 if(FAILED(D3DXCreateSprite(GetDirectXDevice(),&Sprite)))
 {return 1;}

 return 0;
}
int RC_DXWordNest::CreateFont_(int size)
{ 
 if(FAILED(D3DXCreateFont(GetDirectXDevice() ,size,0,500,1,FALSE,DEFAULT_CHARSET,OUT_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_DONTCARE,TEXT("ARIAL"),&FontArray[UseNow])))
 {return -1;}
 UseNow++;

 return (UseNow-1);
}

void RC_DXWordNest::DrawStart_()
{
	if(IsDeviceLost()){return;}
	 dxwordSystemNow=true;
	Sprite->Begin(D3DXSPRITE_ALPHABLEND | D3DXSPRITE_SORT_TEXTURE);
}

void RC_DXWordNest::DrawEnd_()
{
	 dxwordSystemNow=false;
    Sprite->End();
}

void RC_DXWordNest::DrawText_(int font,LPCSTR pstring)
{


	FontArray[font]->DrawTextA(Sprite,pstring,-1,&DrawArea,Format,Color);

}

bool RC_DXWordNest::DeviceLost_Release()
{
 SAFE_RELEASE(Sprite);

 return 0;
}

bool RC_DXWordNest::DeviceLost_Restore()
{
 return Create();
}