
#ifndef _R_ScreenData_h_
#define _R_ScreenData_h_
#include <d3d9.h>
#include <d3dx9.h>


#define SCREEN_ANGLE (30)

class RC_ScreenData
{ 
 int m_SizeW,m_SizeH; 


 D3DXVECTOR3 m_ScreenPosition; //カメラ位置
 D3DXVECTOR3 m_ScreenWay;      //カメラ向き
 D3DXVECTOR3 m_ScreenSlope;    //カメラ傾き

 D3DXVECTOR4 m_LightDirection; // ライト
 D3DXVECTOR3 m_LightPosition;

 D3DXMATRIX  m_View;		// ビューイング行列
 D3DXMATRIX  m_Proj;		// 射影行列

 public:

	 int SIZEW(){return m_SizeW;}
	 int SIZEH(){return m_SizeH;}

  RC_ScreenData();
  void SetSize(int w,int h){m_SizeW=w;m_SizeH=h;Refresh_Proj();}

  void SetLight(float x,float y,float z);
  void SetLightPosition(D3DXVECTOR3* pos);
  D3DXVECTOR4 GetLight(D3DXVECTOR3* pos);
  D3DXVECTOR4* GetLight(){return &m_LightDirection;}

  void Refresh_View();
  void Refresh_Proj();
  D3DXMATRIX* VIEW(){return &m_View;}
  D3DXMATRIX* PROJ(){return &m_Proj;}

  void SetView(D3DXMATRIX* mat);
  void SaveView(D3DXMATRIX* mat);

  D3DXVECTOR3 GetScreenPosition();
  D3DXVECTOR3 GetScreenWay();
  float GetScreenRange();
  void SetScreenRange(float r);
  void SetScreenLocation(D3DXVECTOR3 P,D3DXVECTOR3 W);
  void ScreenMove(D3DXVECTOR3* move);
  void SetLight(D3DXVECTOR3 pos);
  D3DXVECTOR3 GetScreenLookatPosition();
  D3DXVECTOR3 GetScreenUpPosition();

  D3DXVECTOR2 WorldToScreen(D3DXVECTOR3* world);
  D3DXVECTOR3 ScreenToWorld(D3DXVECTOR2* screen);
};

#endif