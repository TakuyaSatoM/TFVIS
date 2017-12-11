#ifndef _RL_2DPorigon_h_
#define _RL_2DPorigon_h_
#include "DXCommon.h"
#include "R_ResourceNest.h"
#include "R_List.h"

class RC_Texture;

 
//オブジェクトクラス
class RC_2DPorigon:public RC_ResourceList
{
 friend class RC_FigureNest;
protected:
 
 RC_FigureNest* m_MyNest;

 WORD m_VertexNum;//頂点数
 WORD m_IndexNum;//インデックス数
 bool  m_BuildEnd;


 D3DXVECTOR3* m_VertexArray;//頂点情報
 D3DXVECTOR2* m_TexUVArray;
 RC_Texture*  m_Texture;
 D3DXVECTOR4* m_ColorArray;//頂点カラー情報
 
 //各種バッファ
 LPDIRECT3DVERTEXBUFFER9 m_PositionBuf;
 LPDIRECT3DINDEXBUFFER9  m_IndexBuf;
 LPDIRECT3DVERTEXBUFFER9 m_ColorBuf;
 LPDIRECT3DVERTEXBUFFER9 m_TexUVBuf;

 bool m_DrawRefreshOn;
 DWORD m_Size;
 bool m_UseDepth;


 RC_2DPorigon(C_ListNest*,RC_FigureNest*);
 virtual ~RC_2DPorigon();

 bool DeviceLost_Release();
 bool DeviceLost_Restore();


 void DrawStanby();
 void Release();
public:
 bool Build(DWORD size,bool refresh,bool depth,RC_Texture*);
 bool Set_Arrow(D3DXVECTOR3 start,D3DXVECTOR3 end,float bold,float abold,float arrowSize,float space,D3DXVECTOR4 colorA,D3DXVECTOR4 colorB);
 bool Set_Arrow(D3DXVECTOR3 start,D3DXVECTOR3 end,float bold,float abold,float arrowSize,float space,D3DXVECTOR4 color);
 bool Set_Tri(D3DXVECTOR3 start,D3DXVECTOR2 size,bool reftway,D3DXVECTOR4 color);
 bool Set_Line(D3DXVECTOR3 start,D3DXVECTOR3 end,float bold,D3DXVECTOR4 color);
 bool Set();
 void Draw();
 void SetTexture(RC_Texture* tex){m_Texture=tex;}
 bool Set_Box(int x,int y,int w,int h,int bold);

 D3DXVECTOR3 f_Point;
 D3DXVECTOR2 f_Size;
 D3DXVECTOR4 f_Color;
 D3DXVECTOR4 f_TexUV;
 float       f_Rotate;
 bool        f_Inverse;
};




#endif