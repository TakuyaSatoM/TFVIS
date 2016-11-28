//=============================================================
// R_Renderer.h
// レンダラークラスの定義
//=============================================================

#ifndef _R_Renderer_h_
#define _R_Renderer_h_

#include <d3d9.h>
#include <d3dx9.h>

#include "Common.h"
 


//=============================================================
// Renderer
// レンダラークラス
//=============================================================
class RC_RendererNest
{
friend class RC_NHLib;

private:
	LPDIRECT3D9				m_pD3D;			// IDirect3D9インターフェイスへのポインタ
	LPDIRECT3DDEVICE9		m_pD3DDevice;	// IDirect3DDevice9インターフェイスへのポインタ
	D3DPRESENT_PARAMETERS	m_D3DPP;		// デバイスのプレゼンテーションパラメータ

public:
	
	RC_RendererNest();

	
	~RC_RendererNest();

	
	bool Init();

	void Release();

	bool SceneStart();
	bool SceneEnd();


};

#endif 