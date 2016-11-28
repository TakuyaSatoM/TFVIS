#include "R_Screen.h"
#include "NHLib.h"
#include "R_LibCore.h"

//////////////////////////////////////////////////////////////////////////////////////////////////////


//MeshDevice�R���X�g���N�^�E�������̂�
RC_ScreenNest::RC_ScreenNest()
{m_NowBuffer=NULL;}
 
//MeshDevice�f�X�g���N�^�E���X�g������
RC_ScreenNest::~RC_ScreenNest(){}

//���� (�O������̌Ăяo���s��(private�錾))
bool RC_ScreenNest::Create()
{
 GetDirectXDevice()->GetViewport(&m_pBackviewport);
 m_BackBuffer_D.SetSize(m_pBackviewport.Width,m_pBackviewport.Height);
 
 return 0;
}

 //-------------------------------------------------------------
// �o�b�N�o�b�t�@�ޔ�
//-------------------------------------------------------------
void RC_ScreenNest::HideBackBuffer_m()
{
 GetDirectXDevice()->GetViewport(&m_pBackviewport);
 GetDirectXDevice()->GetRenderTarget(0, &m_pBackbuffer);
 GetDirectXDevice()->GetDepthStencilSurface( &m_pBackZ); 
}
 //-------------------------------------------------------------
// �o�b�N�o�b�t�@���A
//-------------------------------------------------------------
void RC_ScreenNest::RevaivalBackBuffer_m()
{
 m_NowBuffer=NULL;

 GetDirectXDevice()->SetRenderTarget(0, m_pBackbuffer);
 GetDirectXDevice()->SetDepthStencilSurface(m_pBackZ);	
 GetDirectXDevice()->SetViewport(&m_pBackviewport);

 SAFE_RELEASE(m_pBackbuffer);
 SAFE_RELEASE(m_pBackZ);
}

	

RC_RenderingTexture* CheckNowActiveBuffer(){return NHLib->GetScreenNest()->NOWBUFFER();}


 //-------------------------------------------------------------
// �o�b�N�o�b�t�@�ޔ�
//-------------------------------------------------------------
void HideBackBuffer(){NHLib->GetScreenNest()->HideBackBuffer_m();}

//-------------------------------------------------------------
// �o�b�N�o�b�t�@���A
//-------------------------------------------------------------
void RevaivalBackBuffer(){NHLib->GetScreenNest()->RevaivalBackBuffer_m();}

RC_ScreenData* GetNowBufferData()
{
 RC_RenderingTexture* buffer=CheckNowActiveBuffer();
 if(buffer){return buffer->D();}
 return NHLib->GetScreenNest()->BACKBUFFER_DATA();
}