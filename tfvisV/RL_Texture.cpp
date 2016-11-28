#include "RL_Texture.h"
#include "R_LibCore.h"


RC_Texture::RC_Texture(C_ListNest* list,RC_TextureNest* myNest)
:RC_ResourceList(list)
{
 m_MyNest=myNest;

 Name.clear(); 
  
 hResource=NULL;
 IsRenderingTexture=0;
 
}

RC_Texture::~RC_Texture()
{
	

 Release();
}


void RC_Texture::Release()
{
	SAFE_RELEASE(hResource);
}

float RC_Texture::BX(float x){return x/Desc.Width;}
float RC_Texture::BY(float y){return y/Desc.Height;}

bool  RC_Texture::_TextureCreate()
{
 LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();

	if(IsRenderingTexture){return 0;}
 if(
	D3DXCreateTextureFromFileExA(dDevice, 
								Name.c_str(),
								D3DX_DEFAULT_NONPOW2,D3DX_DEFAULT_NONPOW2,0,0,
								D3DFMT_A8R8G8B8,
                                D3DPOOL_MANAGED, 
								D3DX_FILTER_NONE, D3DX_FILTER_NONE,
                                ColorKey , 		// カラーキー
								NULL, 
								NULL, 
								&hResource))
	{
        _ASSERT_EXPR( 0, TEXT("CT_Texture::Create \n 画像ファイルが読み込めません") );
       return 1;
    }
  
   if (FAILED(hResource->GetLevelDesc(0,&Desc)))
	{
		 _ASSERT_EXPR( 0, TEXT("CT_Texture::Create \n テクスチャ情報を開けません") );
		return 1;
    } 
   m_OffsetX = 0.5/Desc.Width;
   m_OffsetY = 0.5/Desc.Height;

   return 0;
}

bool RC_Texture::Create(LPCSTR WhereFile,LPCSTR FileName,D3DCOLOR colorkey)
{

Name.clear();
Name=FileName;
ColorKey = colorkey;


_TextureCreate();




 
return 0;
}

bool RC_Texture::Create(LPDIRECT3DTEXTURE9 texture)
{
	if(texture ==NULL){return 0;}

_ASSERT(texture);
IsRenderingTexture=1;

 hResource = texture;

    if(FAILED(hResource->GetLevelDesc(0,&Desc)))
	{
		_ASSERT_EXPR( 0, TEXT("CT_Texture::Create \n テクスチャ情報を開けません") );
		return 1;
    } 
	m_OffsetX = 0.5/Desc.Width;
   m_OffsetY = 0.5/Desc.Height;

return 0;
}

bool  RC_Texture::DeviceLost_Release()
{


return 0;
}

bool  RC_Texture::DeviceLost_Restore()
{
 return 0;


}