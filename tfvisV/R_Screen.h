

#ifndef _R_Screen_h_
#define _R_Screen_h_
#include "R_ScreenData.h"


class RC_RenderingTexture;
class RC_ScreenNest
{
	 

 friend class RC_NHLib;

 friend class RC_RenderingTexture;



 // �o�b�N�o�b�t�@�[�̑ޔ�p
 RC_RenderingTexture*    m_NowBuffer;
 D3DVIEWPORT9            m_pBackviewport;
 LPDIRECT3DSURFACE9      m_pBackbuffer;	// �o�b�N�o�b�t�@�[
 LPDIRECT3DSURFACE9		 m_pBackZ;		// �o�b�N�o�b�t�@�[�p�̐[�x�o�b�t�@
 RC_ScreenData           m_BackBuffer_D;

 RC_ScreenNest();
 ~RC_ScreenNest();

 bool Create();
 
public:
	RC_ScreenData* BACKBUFFER_DATA(){return &m_BackBuffer_D;}

 //�����_�����O�o�b�t�@
 void HideBackBuffer_m();
 void RevaivalBackBuffer_m();


 //�����_�����O���̐ݒ�
 
 RC_RenderingTexture* NOWBUFFER(){return m_NowBuffer;}
};

RC_ScreenData* GetNowBufferData();

#endif