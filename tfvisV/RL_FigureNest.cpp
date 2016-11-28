#include "RL_FigureNest.h"
#include "R_LibCore.h"


RC_FigureNest::RC_FigureNest()
{
 m_List=NULL;
 m_Declare_Normal=NULL;
}
 

RC_FigureNest::~RC_FigureNest()
{
 SAFE_DELETE(m_List);
 SAFE_RELEASE(m_Declare_Normal);
 SAFE_RELEASE(m_FX_Square2D.Pointer);
 SAFE_RELEASE(m_FX_DownSampling.Pointer);
}

bool RC_FigureNest::DeviceLost_Release()
{
 SAFE_RELEASE(m_Declare_Normal);
 SAFE_RELEASE(m_Declare_Fish);
 SAFE_RELEASE(m_FX_Square2D.Pointer);
 SAFE_RELEASE(m_FX_DownSampling.Pointer);

 DeviceLost_TaskItemRelease(m_List);
 
 return 0;
}
bool RC_FigureNest::DeviceLost_Restore()
{
	if(SetFX()){return 1;}

 if(DeviceLost_TaskItemRestore(m_List)){return 1;}

 return 0;
}




bool RC_FigureNest::SetFX()
{
 LPD3DXBUFFER	errors = 0;
 LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();
 


 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 //plain
	 D3DXCreateEffectFromFile(dDevice, TEXT("Square2D.fx"), 0, 0, D3DXSHADER_DEBUG, 0, &m_FX_Square2D.Pointer, &errors);
	if( errors){
		 _ASSERT_EXPR( 0, TEXT("Can't open Square2D.fx") );
        return 1;		
	}
	
	// シェーダプログラムへテクニックへのハンドルの取得
	m_FX_Square2D.hTech		= m_FX_Square2D.Pointer->GetTechniqueByName("BasicTech");
	m_FX_Square2D.hTexture		= m_FX_Square2D.Pointer->GetParameterByName(0, "g_texture");



 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 //tex
	 D3DXCreateEffectFromFile(dDevice, TEXT("DownSampling.fx"), 0, 0, D3DXSHADER_DEBUG, 0, &m_FX_DownSampling.Pointer, &errors);
	if( errors){
		 _ASSERT_EXPR( 0, TEXT("Can't open DownSampling.fx") );
        return 1;		
	}
	
	// シェーダプログラムへテクニックへのハンドルの取得
	m_FX_DownSampling.hTech		= m_FX_DownSampling.Pointer->GetTechniqueByName("BasicTech");
	m_FX_DownSampling.hTexture		= m_FX_DownSampling.Pointer->GetParameterByName(0, "g_texture");
	m_FX_DownSampling.hOffsetX		= m_FX_DownSampling.Pointer->GetParameterByName(0, "g_OffsetX");
	m_FX_DownSampling.hOffsetY		= m_FX_DownSampling.Pointer->GetParameterByName(0, "g_OffsetY");

 

	
 
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 	D3DVERTEXELEMENT9 vertexElement_Plain[] = {
			   { 0, 0              , D3DDECLTYPE_FLOAT3  , D3DDECLMETHOD_DEFAULT, D3DDECLUSAGE_POSITION, 0 }, // 頂点座標
			   { 1, 0              ,   D3DDECLTYPE_FLOAT4  , D3DDECLMETHOD_DEFAULT, D3DDECLUSAGE_COLOR, 0 },
			   { 2, 0, D3DDECLTYPE_FLOAT2  , D3DDECLMETHOD_DEFAULT, D3DDECLUSAGE_TEXCOORD  , 0 }, // テクスチャUV座標
			   D3DDECL_END()
			};
	 dDevice->CreateVertexDeclaration( vertexElement_Plain, &m_Declare_Normal );

 	D3DVERTEXELEMENT9 vertexElement_Fish[] = {
			   { 0, 0              , D3DDECLTYPE_FLOAT3  , D3DDECLMETHOD_DEFAULT, D3DDECLUSAGE_POSITION, 0 }, // 頂点座標
			   { 1, 0, D3DDECLTYPE_FLOAT2  , D3DDECLMETHOD_DEFAULT, D3DDECLUSAGE_TEXCOORD  , 0 }, // テクスチャUV座標
			   D3DDECL_END()
			};
	 dDevice->CreateVertexDeclaration( vertexElement_Fish, &m_Declare_Fish );

 return 0;
}

bool RC_FigureNest::Create()
{
 

 m_List = new C_ListNest;
 

 if(m_List == NULL)
 {
   _ASSERT_EXPR( 0, TEXT("CDevice_R2D::Create \n リスト生成に失敗") );
   return 1;
 }

    

 if(SetFX())
 {
  return 1;
 }


 return 0;
}
