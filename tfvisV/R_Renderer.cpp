#include "R_Renderer.h"
#include "NHLib.h"
#include "R_LibCore.h"

RC_RendererNest::RC_RendererNest()
 : m_pD3D(0), m_pD3DDevice(0)
{
} 

RC_RendererNest::~RC_RendererNest()
{
	Release();
}


bool RC_RendererNest::Init()
{

    D3DDISPLAYMODE d3ddm;
    
	// Direct3D9�I�u�W�F�N�g�̍쐬
	if((m_pD3D = ::Direct3DCreate9(D3D_SDK_VERSION)) == 0){
        return 1;	// �擾���s
    }
	
	// ���݂̃f�B�X�v���C���[�h���擾
    if(FAILED(m_pD3D->GetAdapterDisplayMode(D3DADAPTER_DEFAULT, &d3ddm))) {
		return 1;
	}

	// �f�o�C�X�̃v���[���e�[�V�����p�����[�^��������
	ZeroMemory(&m_D3DPP, sizeof(D3DPRESENT_PARAMETERS));
	m_D3DPP.BackBufferCount			= 1;
	m_D3DPP.PresentationInterval=D3DPRESENT_INTERVAL_DEFAULT;

	if(IsFullScreen()) { // �t���X�N���[���̏ꍇ
		m_D3DPP.Windowed				= FALSE;			// �t���X�N���[���\���̎w��
		m_D3DPP.BackBufferWidth			= GetWindowSizeW();		// �t���X�N���[�����̉���
		m_D3DPP.BackBufferHeight		= GetWindowSizeH();		// �t���X�N���[�����̏c��
	}
	else {
		m_D3DPP.Windowed				= TRUE;				// �E�C���h�E���\���̎w��
	}
	m_D3DPP.BackBufferFormat		= d3ddm.Format;			// �J���[���[�h�̎w��
	m_D3DPP.SwapEffect				= D3DSWAPEFFECT_DISCARD; 
	m_D3DPP.EnableAutoDepthStencil	= TRUE;
	m_D3DPP.AutoDepthStencilFormat	= D3DFMT_D24S8;
	
	// �f�B�X�v���C�A�_�v�^��\�����߂̃f�o�C�X���쐬
	// �`��ƒ��_�������n�[�h�E�F�A�ōs�Ȃ�
	if(FAILED(m_pD3D->CreateDevice(D3DADAPTER_DEFAULT, 
									D3DDEVTYPE_HAL, 
									GetWindowHandle(), 
									D3DCREATE_HARDWARE_VERTEXPROCESSING, 
									&m_D3DPP, &m_pD3DDevice))) {
		// ��L�̐ݒ肪���s������
		// �`����n�[�h�E�F�A�ōs���A���_������CPU�ōs�Ȃ�
		if(FAILED(m_pD3D->CreateDevice(D3DADAPTER_DEFAULT, 
										D3DDEVTYPE_HAL, 
										GetWindowHandle(), 
										D3DCREATE_SOFTWARE_VERTEXPROCESSING, 
										&m_D3DPP, &m_pD3DDevice))) {
			// ��L�̐ݒ肪���s������
			// �`��ƒ��_������CPU�ōs�Ȃ�
			if(FAILED(m_pD3D->CreateDevice(D3DADAPTER_DEFAULT, 
											D3DDEVTYPE_REF, GetWindowHandle(), 
											D3DCREATE_SOFTWARE_VERTEXPROCESSING, 
											&m_D3DPP, &m_pD3DDevice))) {
				// ���������s
				return 1;
			}
		}
	}


	return 0;
}

bool RC_RendererNest::SceneStart()
{
 if(IsDeviceLost())
 {
	 if (m_pD3DDevice->TestCooperativeLevel() != D3DERR_DEVICENOTRESET ){return 1;}


	 if(m_pD3DDevice->Reset(&m_D3DPP) != S_OK)
	 {
	  CheckBox("�f�o�C�X���A���s");
	  PostQuitMessage(0);
	 }
	 if(NHLib->DeviceLost_Restore())
	 {
	  CheckBox("�č\�z���s");
	  PostQuitMessage(0);
	 }

	 ClearDeviceLost();

 }



 if(FAILED(m_pD3DDevice->Clear(0,NULL, 				// ����������̈�͑S��
					D3DCLEAR_TARGET |					// �o�b�N�o�b�t�@���w��
					D3DCLEAR_ZBUFFER, 					// �[�x�o�b�t�@�iZ�o�b�t�@�j���w��
					D3DXCOLOR(200./255,200./255,200./255, 1.0f), 	// ����������F
					1.0f, 								// ����������[�x�o�b�t�@�iZ�o�b�t�@�j�̒l
					0))) {								// ����������X�e���V���o�b�t�@�̒l
		return 1;
	}

 GetDirectXDevice()->BeginScene();


 return 0;
}

bool RC_RendererNest::SceneEnd()
{
 if(IsDeviceLost())
 {return 0;}

	// �`��I���錾
	m_pD3DDevice->EndScene();
	

	// �`�挋�ʂ̓]��
	if(FAILED(m_pD3DDevice->Present( 0, 0, 0, 0 ))) {
		// �f�o�C�X�������畜�A
		CallDeviceLost();

		NHLib->DeviceLost_Release();
		//m_pD3DDevice->Reset(&m_D3DPP);
	}


	return 0;
}



void RC_RendererNest::Release()
{
	// �f�o�C�X�I�u�W�F�N�g�̉��
	SAFE_RELEASE(m_pD3DDevice);

	// DirectXGraphics�̉��
	SAFE_RELEASE(m_pD3D);
}