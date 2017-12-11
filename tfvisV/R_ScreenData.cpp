#include "R_Screen.h"
#include "NHLib.h"

RC_ScreenData::RC_ScreenData()
{
 m_ScreenPosition = D3DXVECTOR3(0.0f,70.0f,35.0f);
 m_ScreenWay      = D3DXVECTOR3(0.0f,0.0f,0.0f);
 m_ScreenSlope    = D3DXVECTOR3(0.0f,1.0f,0.0f);
 Refresh_View();
  
 SetLightPosition(&D3DXVECTOR3(0,0,0));
 SetLight(0,1.0,0.5);

 SetSize(100,100);
}

void RC_ScreenData::SetLightPosition(D3DXVECTOR3* pos)
{
 m_LightPosition = *pos;
}

void RC_ScreenData::SetLight(float x,float y,float z)
{
	m_LightDirection.x =x;
	m_LightDirection.y = y;
	m_LightDirection.z = z;
	m_LightDirection.w = 0;
	D3DXVec4Normalize(&m_LightDirection, &m_LightDirection);
}

D3DXVECTOR4 RC_ScreenData::GetLight(D3DXVECTOR3* pos)
{
 D3DXVECTOR3 re;
 re = *pos - m_LightPosition;
 re*=-1;

return D3DXVECTOR4(re.x,re.y,re.z,0);
}

//プロジェクション行列の更新
void RC_ScreenData::Refresh_Proj()
{
	D3DXMatrixIdentity(&m_Proj);
	D3DXMatrixPerspectiveFovLH(&m_Proj, D3DXToRadian(SCREEN_ANGLE), (float)m_SizeW / (float)m_SizeH, 1.0f, 2000.0f);
}
//ビュー行列の更新
void RC_ScreenData::Refresh_View()
{
  D3DXMatrixIdentity(&m_View);
  D3DXMatrixLookAtLH(&m_View, &m_ScreenPosition, &m_ScreenWay,&m_ScreenSlope);
}

void RC_ScreenData::SetView(D3DXMATRIX* mat)
{
 m_ScreenPosition.x = mat->_11;
 m_ScreenPosition.y = mat->_12;
 m_ScreenPosition.z = mat->_13;
 m_ScreenWay.x = mat->_21;
 m_ScreenWay.y = mat->_22;
 m_ScreenWay.z = mat->_23;
 m_ScreenSlope.x = mat->_31;
 m_ScreenSlope.y = mat->_32;
 m_ScreenSlope.z = mat->_33;

 D3DXMatrixIdentity(&m_View);
 Refresh_View();
}

void RC_ScreenData::SaveView(D3DXMATRIX* mat)
{
 mat->_11=m_ScreenPosition.x;
 mat->_12=m_ScreenPosition.y;
 mat->_13=m_ScreenPosition.z;

 mat->_21=m_ScreenWay.x;
 mat->_22=m_ScreenWay.y;
 mat->_23=m_ScreenWay.z;

 mat->_31=m_ScreenSlope.x;
 mat->_32=m_ScreenSlope.y;
 mat->_33=m_ScreenSlope.z;
}


D3DXVECTOR3 RC_ScreenData::GetScreenPosition(){return m_ScreenPosition;}
D3DXVECTOR3 RC_ScreenData::GetScreenWay(){return m_ScreenWay;}
float RC_ScreenData::GetScreenRange(){return D3DXVec3Length(&(m_ScreenPosition - m_ScreenWay));}

void RC_ScreenData::SetScreenRange(float r)
{
 D3DXVECTOR3 vec = m_ScreenPosition - m_ScreenWay;
 D3DXVec3Normalize( &vec, &vec);
 SetScreenLocation(m_ScreenWay+vec*r,m_ScreenWay);
}

void  RC_ScreenData::SetScreenLocation(D3DXVECTOR3 P,D3DXVECTOR3 W)
{
  m_ScreenPosition = P;m_ScreenWay=W;
  Refresh_View();
}

void  RC_ScreenData::ScreenMove(D3DXVECTOR3* move)
{
 D3DXVECTOR3 posd  = m_ScreenPosition -m_ScreenWay;

 m_ScreenPosition=*move+posd;
 m_ScreenWay=*move;
 Refresh_View();
}

void RC_ScreenData::SetLight(D3DXVECTOR3 pos){SetLight(pos.x,pos.y,pos.z);}
D3DXVECTOR3 RC_ScreenData::GetScreenLookatPosition(){return m_ScreenWay;}
D3DXVECTOR3 RC_ScreenData::GetScreenUpPosition(){return m_ScreenSlope;}

