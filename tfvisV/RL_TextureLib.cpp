#include "R_LibCore.h"
#include "RL_Texture.h"
#include "RL_TextureNest.h"

RC_Texture* CreateNewTexture(LPDIRECT3DTEXTURE9	texture)
{
 RC_Texture* r=NHLib->GetTextureNest()->CreateObject<RC_Texture>();
 
 if(r->Create(texture))
 {return NULL;}

  
	return r;
}

RC_Texture* CreateNewTexture(LPCSTR WhereFile,LPCSTR FileName,D3DCOLOR colorkey)
{
 RC_Texture* r=NHLib->GetTextureNest()->CreateObject<RC_Texture>();
 
 if(r->Create(WhereFile,FileName,colorkey))
 {return NULL;}


	return r;
}
