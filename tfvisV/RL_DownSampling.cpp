#include "R_LibCore.h"
#include "R_Screen.h"
#include "RL_DownSampling.h"
#include "RL_FigureNest.h"
#include "R_MathVector.h"
#include "R_Math.h"
#include "RL_Texture.h"

 
RC_DownSampling::RC_DownSampling(C_ListNest* list,RC_FigureNest* myNest)
:RC_ResourceList(list)
{
 
 m_MyNest  = myNest;
 m_VertexNum=0;
 m_IndexNum=0;
 m_VertexArray=0;
 m_ColorArray=0;
 m_PositionBuf=NULL;
 m_IndexBuf=NULL;
 m_ColorBuf=NULL;
 m_BuildEnd=0;

 //情報配列生成
 m_VertexArray =NULL;
 m_ColorArray = NULL;
 m_TexUVArray=NULL;
 m_Texture=NULL;

 m_DrawRefreshOn=0;
 m_Size=0;
 m_UseDepth=0;
 m_Texture=NULL;

 f_Size=D3DXVECTOR2(0,0);
 f_Color=D3DXVECTOR4(1,1,1,1);


}

void RC_DownSampling::Release()
{
 SAFE_RELEASE(m_PositionBuf);
 SAFE_RELEASE(m_ColorBuf);
 SAFE_RELEASE(m_TexUVBuf);
 SAFE_RELEASE(m_IndexBuf);

 if(m_ColorArray)delete[] m_ColorArray;
 if(m_TexUVArray)delete[] m_TexUVArray;
 if(m_VertexArray)delete[] m_VertexArray;
}

RC_DownSampling::~RC_DownSampling(){Release();}

bool RC_DownSampling::DeviceLost_Release()
{
 Release();return 0;
}

bool RC_DownSampling::DeviceLost_Restore(){return Build(m_Size,m_DrawRefreshOn,m_UseDepth,m_Texture);}


bool RC_DownSampling::Build(DWORD size,bool refresh,bool depth,RC_Texture* texture)
{
 m_DrawRefreshOn=refresh;
 m_Size=size;
 m_UseDepth=depth;
 m_Texture=texture; 


 int vertexNum=m_Size*4;
 int indexNum=m_Size*6;

 m_BuildEnd=0;

   m_VertexArray = new D3DXVECTOR3[vertexNum];
   m_ColorArray = new D3DXVECTOR4[vertexNum];
   m_TexUVArray = new D3DXVECTOR2[vertexNum];

 LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__(); 
  
 //頂点バッファ
  dDevice->CreateVertexBuffer(
		vertexNum*sizeof(D3DXVECTOR3),
		D3DUSAGE_WRITEONLY | D3DUSAGE_DYNAMIC,
		0,
		D3DPOOL_DEFAULT,
		&m_PositionBuf,
		NULL);

  //頂点カラーバッファ
  dDevice->CreateVertexBuffer(
		vertexNum*sizeof(D3DXVECTOR4),
		D3DUSAGE_WRITEONLY | D3DUSAGE_DYNAMIC,
		0,
		D3DPOOL_DEFAULT,
		&m_ColorBuf,
		NULL);

   dDevice->CreateVertexBuffer(
		vertexNum*sizeof(D3DXVECTOR2),
		D3DUSAGE_WRITEONLY | D3DUSAGE_DYNAMIC,
		0,
		D3DPOOL_DEFAULT,
		&m_TexUVBuf,
		NULL);

  //インデックスバッファ

  dDevice->CreateIndexBuffer(
			indexNum*sizeof(WORD),
			D3DUSAGE_WRITEONLY ,
			D3DFMT_INDEX16,
			D3DPOOL_DEFAULT,
			&m_IndexBuf,
			NULL);

  WORD* bufferIndex;
  m_IndexBuf->Lock( 0, 0, (void**)&bufferIndex, 0  );


	  for(DWORD i=0;i<m_Size;i++)
	  {

	   bufferIndex[i*6]   = i*4;
	   bufferIndex[i*6+1] = i*4+1;
	   bufferIndex[i*6+2] = i*4+2;

	   bufferIndex[i*6+3] = i*4+2;
	   bufferIndex[i*6+4] = i*4+1;
	   bufferIndex[i*6+5] = i*4+3;

	  }  
 m_IndexBuf->Unlock();
 return 0;
}


bool RC_DownSampling::Set()
{
	//容量オーバー
	if(m_VertexNum/4 >= m_Size){return 1;}


    LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();


	{
			D3DVIEWPORT9    vp;

			int WinW;
			int WinH;

			if(FAILED(dDevice->GetViewport(&vp))) {return 1;}

			WinW = vp.Width;
			WinH = vp.Height; 

		 float x,y,w,h;

		 x=f_Point.x; 
		 y=f_Point.y; 
		 w=f_Size.x;
		 h=f_Size.y;

		 m_VertexArray[m_VertexNum]=D3DXVECTOR3(x, y, 0);
		 m_VertexArray[m_VertexNum+1]= D3DXVECTOR3(x+w,y, 0);
		 m_VertexArray[m_VertexNum+2]=D3DXVECTOR3(x,y+h, 0);
		 m_VertexArray[m_VertexNum+3]=D3DXVECTOR3(x+w, y+h, 0);


		  for (int i=0 ; i < 4 ; i++ ) 
		  {
			 m_VertexArray[m_VertexNum+i].x =-1.0 +m_VertexArray[m_VertexNum+i].x/(WinW/2.);
			 m_VertexArray[m_VertexNum+i].y = 1.0 -m_VertexArray[m_VertexNum+i].y/(WinH/2.);
			 m_VertexArray[m_VertexNum+i].z =f_Point.z;
		  }


	}

	{///////////////////////////////////////////////////////////////////////////////////
	   m_ColorArray[m_VertexNum] =   f_Color;
       m_ColorArray[m_VertexNum+1] = f_Color;
       m_ColorArray[m_VertexNum+2] = f_Color;
       m_ColorArray[m_VertexNum+3] = f_Color;

	}
	{////////////////////////////////////////////////////////////////////////////////////

			D3DXVECTOR2 offset;

			offset.x = 0.5/m_Texture->SIZEW();
			offset.y = 0.5/m_Texture->SIZEH();

			float w,h;
			w=m_Texture->SIZEW();
			h=m_Texture->SIZEH();
			   m_TexUVArray[m_VertexNum]= offset+D3DXVECTOR2( f_TexUV.x/w,  f_TexUV.y/h);
			   m_TexUVArray[m_VertexNum+1] = offset+D3DXVECTOR2( (f_TexUV.x+f_TexUV.z)/w,f_TexUV.y/h);
			   m_TexUVArray[m_VertexNum+2] = offset+D3DXVECTOR2( f_TexUV.x/w, (f_TexUV.y+f_TexUV.w)/h);
			   m_TexUVArray[m_VertexNum+3] = offset+D3DXVECTOR2( (f_TexUV.x+f_TexUV.z)/w, (f_TexUV.y+f_TexUV.w)/h);
	}


 m_VertexNum+=4;
 m_IndexNum+=6;

	return 0;
}



void RC_DownSampling::DrawStanby()
{
   if(m_BuildEnd){return;}
 
   m_BuildEnd=1;
   D3DXVECTOR3* bufferPosition;
   m_PositionBuf->Lock( 0, 0, (void**)&bufferPosition, D3DLOCK_DISCARD  );
   for(WORD i=0;i<m_VertexNum;i++){bufferPosition[i] = m_VertexArray[i];}
   m_PositionBuf->Unlock();

   D3DXVECTOR4* bufferColor;
   m_ColorBuf->Lock( 0, 0, (void**)&bufferColor, D3DLOCK_DISCARD  );
   for(WORD i=0;i<m_VertexNum;i++){bufferColor[i] = m_ColorArray[i];}
   m_ColorBuf->Unlock();

   if(m_TexUVArray)
   {
	   D3DXVECTOR2* bufferTex;
	   m_TexUVBuf->Lock( 0, 0, (void**)&bufferTex, D3DLOCK_DISCARD  );
	   for(WORD i=0;i<m_VertexNum;i++){bufferTex[i] = m_TexUVArray[i];}
	   m_TexUVBuf->Unlock();
   }

}
void RC_DownSampling::Draw()
{
 if(m_VertexNum==0){return;}
 DrawStanby();
 if(m_BuildEnd==0){return;}

 LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();


 dDevice->SetVertexDeclaration( m_MyNest->m_Declare_Normal);

 m_MyNest->m_FX_DownSampling.Pointer->SetTechnique(m_MyNest->m_FX_DownSampling.hTech);
  m_MyNest->m_FX_DownSampling.Pointer->Begin(0, 0);

 

  dDevice->SetIndices(m_IndexBuf);
  dDevice->SetStreamSource( 0, m_PositionBuf, 0, sizeof( D3DXVECTOR3 ) );
  dDevice->SetStreamSource( 1, m_ColorBuf, 0, sizeof( D3DXVECTOR4 ));
  dDevice->SetStreamSource( 2, m_TexUVBuf, 0, sizeof( D3DXVECTOR2 ) );

  m_MyNest->m_FX_DownSampling.Pointer->SetFloat(m_MyNest->m_FX_DownSampling.hOffsetX, 0.5/m_Texture->SIZEW());
  m_MyNest->m_FX_DownSampling.Pointer->SetFloat(m_MyNest->m_FX_DownSampling.hOffsetY, 0.5/m_Texture->SIZEH());

   m_MyNest->m_FX_DownSampling.Pointer->SetTexture(m_MyNest->m_FX_Square2D.hTexture, (m_Texture->GetRes()));
  

  	
  m_MyNest->m_FX_DownSampling.Pointer->CommitChanges();

  if(m_ColorArray[0].w >= 1.0){m_MyNest->m_FX_DownSampling.Pointer->BeginPass(0);}
  else{m_MyNest->m_FX_DownSampling.Pointer->BeginPass(1);}
	    
  dDevice->DrawIndexedPrimitive(D3DPT_TRIANGLELIST, 0, 0, m_VertexNum,0,m_IndexNum/3);

  m_MyNest->m_FX_DownSampling.Pointer->EndPass();
  m_MyNest->m_FX_DownSampling.Pointer->End();		

  if(m_DrawRefreshOn)
  {
   m_BuildEnd=0;
   m_VertexNum=0;
   m_IndexNum=0;
  }
}