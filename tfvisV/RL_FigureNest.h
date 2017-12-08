#ifndef _RL_FigureNest_h_
#define _RL_FigureNest_h_
#include "DXCommon.h"
#include "R_ResourceNest.h"
#include "R_List.h"

 

struct RS_FigureFX_Square2D
{
 LPD3DXEFFECT            Pointer; 
 D3DXHANDLE              hTech;
 D3DXHANDLE              hTexture;

 RS_FigureFX_Square2D(){Pointer=NULL;}
};



struct RS_FigureFX_DownSampling
{
 LPD3DXEFFECT            Pointer; 
 D3DXHANDLE              hTech;
 D3DXHANDLE              hTexture;
 D3DXHANDLE              hOffsetX;
 D3DXHANDLE              hOffsetY;

 RS_FigureFX_DownSampling(){Pointer=NULL;}
};



class RC_FigureNest:public RC_ResourceNest
{
 friend class RC_2DPorigon;
 friend class RC_DownSampling;
  friend class RC_Fish;

protected:

 C_ListNest *m_List;
 
 //2D

 RS_FigureFX_Square2D  m_FX_Square2D;
 RS_FigureFX_DownSampling  m_FX_DownSampling;

  IDirect3DVertexDeclaration9* m_Declare_Normal;
  IDirect3DVertexDeclaration9* m_Declare_Fish;
bool SetFX();

public:
RC_FigureNest();
virtual bool Create();
~RC_FigureNest();

C_ListNest* LIST(){return m_List;}


 virtual bool DeviceLost_Release();
 virtual bool DeviceLost_Restore();


 template <class Type> Type* CreateObject(){return new Type(m_List,this);}

};




#endif