//=============================================================
// R_Renderer.h
// �����_���[�N���X�̒�`
//=============================================================

#ifndef _R_Renderer_h_
#define _R_Renderer_h_

#include <d3d9.h>
#include <d3dx9.h>

#include "Common.h"
 


//=============================================================
// Renderer
// �����_���[�N���X
//=============================================================
class RC_RendererNest
{
friend class RC_NHLib;

private:
	LPDIRECT3D9				m_pD3D;			// IDirect3D9�C���^�[�t�F�C�X�ւ̃|�C���^
	LPDIRECT3DDEVICE9		m_pD3DDevice;	// IDirect3DDevice9�C���^�[�t�F�C�X�ւ̃|�C���^
	D3DPRESENT_PARAMETERS	m_D3DPP;		// �f�o�C�X�̃v���[���e�[�V�����p�����[�^

public:
	
	RC_RendererNest();

	
	~RC_RendererNest();

	
	bool Init();

	void Release();

	bool SceneStart();
	bool SceneEnd();


};

#endif 