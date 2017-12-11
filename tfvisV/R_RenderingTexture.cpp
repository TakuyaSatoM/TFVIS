#include "R_RenderingTexture.h"
#include "NHLib.h"
#include "RL_Texture.h"
#include "R_LibCore.h"
#include "R_Screen.h"

RC_RenderingTexture::RC_RenderingTexture(C_ListNest* list)
:RC_ResourceList(list)
{	
 m_Surface= NULL;	
 m_ZBuffer = NULL;	
 Clear();
  
 m_TextureTask =  CreateNewTexture(NULL);
 m_Color = D3DXCOLOR(0.2f, 0.2f, 0.4f, 1.0f);

 m_DeviceLost=0;
}

RC_RenderingTexture::~RC_RenderingTexture()
{
 Release();
 m_TextureTask->Delete();
}



void RC_RenderingTexture::Clear()
{
Release();

m_Surface= NULL;	
m_ZBuffer = NULL;	



}

void RC_RenderingTexture::Release()
{
    SAFE_RELEASE(m_Surface);
	
	SAFE_RELEASE(m_ZBuffer);
}

bool RC_RenderingTexture::Build(DWORD sizeW,DWORD sizeH)
{
	m_Data.SetSize(sizeW,sizeH);

 if(GetDirectXDevice() == NULL)
 {
  _ASSERT_EXPR( 0, TEXT("RC_RenderingTexture::pD3DDevice���s���ł�") );
 }

 LPDIRECT3DTEXTURE9	hResource;

 if( FAILED(GetDirectXDevice()->CreateTexture(
						sizeW,sizeH,				// �e�N�X�`���̃T�C�Y 
						1,						// �e�N�X�`�����x���̐��i�{���ł͏�ɂP�ɐݒ�j
         				D3DUSAGE_RENDERTARGET,	// �����_�����O�^�[�Q�b�g�Ƃ��ė��p���邱�Ƃ𖾎�
						D3DFMT_A8R8G8B8,		// �e�N�X�`���t�H�[�}�b�g�iRGBA�́`���[�h��ݒ�j
         				D3DPOOL_DEFAULT,		// �e�N�X�`���������̊m�ە��@�iD3DPOOL_DEFAULT������j
         				&hResource,			// �e�N�X�`���̊i�[��������|�C���^�̃A�h���X
						NULL))) {				// ���NULL���w��
		_ASSERT_EXPR( 0, TEXT("RenderingTexture_Class::�e�N�X�`���������s") );
							return 1;
	}


	// �e�N�X�`�����烌���_�����O�^�[�Q�b�g�ɂ���T�[�t�F�[�X�̎擾
	if( FAILED(hResource->GetSurfaceLevel(0, &m_Surface))){
		CheckBox(("RenderingTexture_Class::�T�[�t�F�[�X�������s"));
		return E_FAIL;
	}

	// �e�N�X�`���ւ̃����_�����O�Ɏg���[�x�o�b�t�@�[�̍쐬
	if (FAILED(GetDirectXDevice()->CreateDepthStencilSurface(
						sizeW,sizeH,				// �[�x�o�b�t�@�̃T�C�Y
						D3DFMT_D24X8,			// �[�x�o�b�t�@�̃t�H�[�}�b�g�i24�r�b�g�j
						D3DMULTISAMPLE_NONE,	// ���x�ȕ�ԕ��@�̐ݒ�i�{���ł͏��D3DMULTISAMPLE_NONE�j
						0,						// �摜�̕i�����x���̐ݒ�i�{���ł͏��0�j
						TRUE,					// �[�x�o�b�t�@�̐�����@�̎w��i���TRUE�ɂ��Ă����j
						&m_ZBuffer,				// �[�x�o�b�t�@�̊i�[��������|�C���^�̃A�h���X
						NULL))){				// ���NULL���w��
        CheckBox(("RenderingTexture_Class::�[�x�o�b�t�@�[�������s"));
        return E_FAIL;
	}



	m_TextureTask->Create(hResource);

	


return 0;
}

void RC_RenderingTexture::BufferClear()
{
 BufferClear(D3DCLEAR_TARGET |D3DCLEAR_ZBUFFER);
}

void RC_RenderingTexture::BufferClear(DWORD flag)
{
// �����_�����O�^�[�Q�b�g��������
	if(FAILED(GetDirectXDevice()->Clear(0,NULL, 
								flag, 
								 m_Color, // �w�i�𔒂ɐݒ�
								1.0f, 0))) {
									CheckBox(("RenderingTexture_Class::�����_�C�����OCLass ���������s"));
		return;
	}

}

void RC_RenderingTexture::Set(bool clear)
{
	NHLib->GetScreenNest()->m_NowBuffer=this;
 // �e�N�X�`���������_�����O�^�[�Q�b�g�ɐݒ�
	GetDirectXDevice()->SetRenderTarget(0, m_Surface);

	// �e�N�X�`���p�̐[�x�o�b�t�@��ݒ�
	GetDirectXDevice()->SetDepthStencilSurface(m_ZBuffer);

	if(clear)BufferClear();

	// �r���[�|�[�g���e�N�X�`���摜�̃T�C�Y�ɐݒ�
}

bool RC_RenderingTexture::DeviceLost_Release()
{
 SAFE_RELEASE(m_TextureTask->hResource);
 SAFE_RELEASE(m_Surface);
 SAFE_RELEASE(m_ZBuffer);
 m_DeviceLost=1;
 return 0;
}
bool RC_RenderingTexture::DeviceLost_Restore()
{
 m_DeviceLost=0;


 if(Build(D()->SIZEW(),D()->SIZEH()))
 {return 1;}

 BufferClear();
 return 0;
}
