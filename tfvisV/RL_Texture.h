#ifndef _RL_Texture_h_
#define _RL_Texture_h_
#include "DXCommon.h"
#include "R_ResourceList.h"
#include <string>
using namespace std;
#include "Common.h"



 

class RC_Texture :public RC_ResourceList
{
 friend class RC_RenderingTexture;
 friend class RC_TexSquare;
 friend class RC_TextureNest;
 private:

 RC_TextureNest* m_MyNest;

 bool IsRenderingTexture;
 string      Name; 
 

 LPDIRECT3DTEXTURE9	hResource;
 
 D3DCOLOR ColorKey;

 float m_OffsetX;
 float m_OffsetY;
 D3DSURFACE_DESC    Desc;
 

 RC_Texture(C_ListNest* list,RC_TextureNest* myNest);
 ~RC_Texture();


  bool  _TextureCreate();

  virtual bool DeviceLost_Release();
  virtual bool DeviceLost_Restore();

public:

  LPDIRECT3DTEXTURE9	GetRes(){return hResource;}
  
  void Release();

  float BX(float x);
  float BY(float y);

  float SIZEW(){return Desc.Width;}
  float SIZEH(){return Desc.Height;}
  string NAME(){return Name;}

	bool Create(LPCSTR WhereFile,LPCSTR FileName,D3DCOLOR colorkey);
    bool Create(LPDIRECT3DTEXTURE9	texture);
	void ClearResource(){hResource=NULL;}
};



#endif