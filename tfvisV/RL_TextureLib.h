#ifndef _RL_TextureLib_h_
#define _RL_TextureLib_h_

#include "RL_Texture.h"


RC_Texture* CreateNewTexture(LPDIRECT3DTEXTURE9	texture);
RC_Texture* CreateNewTexture(LPCSTR WhereFile,LPCSTR FileName,D3DCOLOR colorkey);
 
#endif