#include "R_LibCore.h"
#include "R_Screen.h"
#include "RL_2DPorigon.h"
#include "RL_FigureNest.h"
#include "R_MathVector.h"
#include "R_Math.h"
#include "RL_Texture.h"

 
RC_2DPorigon::RC_2DPorigon(C_ListNest* list,RC_FigureNest* myNest)
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
 f_TexUV=D3DXVECTOR4(0,0,1,1);
 f_Rotate=0;
 f_Inverse=0;

}

void RC_2DPorigon::Release()
{
 SAFE_RELEASE(m_PositionBuf);
 SAFE_RELEASE(m_ColorBuf);
 SAFE_RELEASE(m_TexUVBuf);
 SAFE_RELEASE(m_IndexBuf);

 if(m_ColorArray)delete[] m_ColorArray;
 if(m_TexUVArray)delete[] m_TexUVArray;
 if(m_VertexArray)delete[] m_VertexArray;
}

RC_2DPorigon::~RC_2DPorigon(){Release();}

bool RC_2DPorigon::DeviceLost_Release()
{
 Release();return 0;
}

bool RC_2DPorigon::DeviceLost_Restore(){return Build(m_Size,m_DrawRefreshOn,m_UseDepth,m_Texture);}

bool RC_2DPorigon::Set_Box(int x,int y,int w,int h,int bold)
{
	f_Point.x=x;
	f_Point.y=y;

	f_Size.x=bold;
	f_Size.y=h;
	Set();

	f_Point.x=x+bold;
	f_Size.x=w-bold;
	f_Size.y=bold;
	Set();

	f_Point.x=x+w-bold;
	f_Point.y=y+bold;
	f_Size.x=bold;
	f_Size.y=h-bold;
	Set();

	f_Point.x=x+bold;
	f_Point.y=y+h-bold;
	f_Size.x=w-bold*2;
	f_Size.y=bold;
	Set();

	f_Point.x=x;
	f_Point.y=y;

	f_Size.x=w;
	f_Size.y=h;

	return 0;
}

bool RC_2DPorigon::Build(DWORD size,bool refresh,bool depth,RC_Texture* texture)
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


void RC_2DPorigon::DrawStanby()
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

bool RC_2DPorigon::Set_Tri(D3DXVECTOR3 start,D3DXVECTOR2 size,bool leftway,D3DXVECTOR4 color)
{
  int useVertex=4;
  if((useVertex+m_VertexNum)/4 >= m_Size){return true;}
  LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();

	{
			D3DVIEWPORT9    vp;

			int WinW;
			int WinH;

			if(FAILED(dDevice->GetViewport(&vp))) {return 1;}

			WinW = vp.Width;
			WinH = vp.Height; 

		 float x,y,w,h;

		 x=start.x; 
		 y=start.y; 
		 w=size.x;
		 h=size.y;

		 m_VertexArray[m_VertexNum]=D3DXVECTOR3(x, y, 0);
		 m_VertexArray[m_VertexNum+1]= D3DXVECTOR3(x+w,y, 0);
		 m_VertexArray[m_VertexNum+2]=D3DXVECTOR3(x,y+h, 0);
		 m_VertexArray[m_VertexNum+3]=D3DXVECTOR3(x+w, y+h, 0);


		 if(leftway)
		 {
			 m_VertexArray[m_VertexNum]=D3DXVECTOR3(x+w/2, y, 0);
			 m_VertexArray[m_VertexNum+1]=D3DXVECTOR3(x+w/2,y, 0);		 
		 }
		 else
		 {
		 m_VertexArray[m_VertexNum+2]= D3DXVECTOR3(x+w/2,y+h, 0);
		 m_VertexArray[m_VertexNum+3]=D3DXVECTOR3(x+w/2, y+h, 0);		 
		 }
		 
			  for (int i=0 ; i < 4 ; i++ ) 
			  {
				 m_VertexArray[m_VertexNum+i].x =-1.0 +m_VertexArray[m_VertexNum+i].x/(WinW/2.);
				 m_VertexArray[m_VertexNum+i].y = 1.0 -m_VertexArray[m_VertexNum+i].y/(WinH/2.);
				 m_VertexArray[m_VertexNum+i].z =start.z;
			  }
		  
	
	}

	{///////////////////////////////////////////////////////////////////////////////////
	   m_ColorArray[m_VertexNum] =   color;
       m_ColorArray[m_VertexNum+1] = color;
       m_ColorArray[m_VertexNum+2] = color;
       m_ColorArray[m_VertexNum+3] = color;

	}

 m_VertexNum+=4;
 m_IndexNum+=6;

	return false;
}

bool RC_2DPorigon::Set_Arrow(D3DXVECTOR3 start,D3DXVECTOR3 end,float bold,float abold,float arrowSize,float space,D3DXVECTOR4 color)
{
return Set_Arrow(start,end,bold,abold,arrowSize,space,color,color);
}

bool RC_2DPorigon::Set_Arrow(D3DXVECTOR3 start,D3DXVECTOR3 end,float bold,float abold,float arrowSize,float space,D3DXVECTOR4 colorA,D3DXVECTOR4 colorB)
{
  int useVertex=8;
  if((useVertex+m_VertexNum)/4 >= m_Size){return 1;}
  LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();


  //頂点カラー
	for(int i=0;i<useVertex;i++)
	{
		m_ColorArray[m_VertexNum+i] =  colorA;
		m_VertexArray[m_VertexNum+i]=D3DXVECTOR3(0,0,0);
	}

	m_ColorArray[m_VertexNum+0] =  colorB;
	m_ColorArray[m_VertexNum+1] =  colorB;
	{
		 D3DVIEWPORT9    vp;

		 int WinW;
		 int WinH;

		 if(FAILED(dDevice->GetViewport(&vp))) {return 1;}

		 WinW = vp.Width;
		 WinH = vp.Height; 

		 float x,y,w,h;

		 D3DXVECTOR2 vector;
		 vector.x=end.x-start.x;
		 vector.y=end.y-start.y;
		 float len=D3DXVec2Length(&vector);
		 D3DXVec2Normalize(&vector,&vector);

		 float way=Sum_FlatAngle360(&vector);

	     D3DXVECTOR3 vec3;
		 vec3.x=vector.x;
		 vec3.y=vector.y;
		 vec3.z=0;

		 x=f_Point.x; 
		 y=f_Point.y; 
		 w=f_Size.x;
		 h=f_Size.y;

		 bold/=2;

		 D3DXVECTOR3 a,b;
		 a=D3DXVECTOR3(bold*sin(D3DXToRadian(way-90)), bold*cos(D3DXToRadian(way-90)), 0);
		 b=D3DXVECTOR3(bold*sin(D3DXToRadian(way+90)), bold*cos(D3DXToRadian(way+90)), 0);

		 D3DXVECTOR3 lineStart=start+vec3*space;
		 D3DXVECTOR3 arrowStart=start+vec3*Cut0f(len-arrowSize-space);

		 m_VertexArray[m_VertexNum]=lineStart+b;
		 m_VertexArray[m_VertexNum+1]= lineStart+a;
		 m_VertexArray[m_VertexNum+2]=arrowStart+b;
		 m_VertexArray[m_VertexNum+3]=arrowStart+a;

		 //矢印
		 a=D3DXVECTOR3(abold*sin(D3DXToRadian(way-90)), abold*cos(D3DXToRadian(way-90)), 0);
		 b=D3DXVECTOR3(abold*sin(D3DXToRadian(way+90)), abold*cos(D3DXToRadian(way+90)), 0);

		 D3DXVECTOR3 arrowEnd=start+vec3*Cut0f(len-space);

		 m_VertexArray[m_VertexNum+4]=arrowStart+b;
		 m_VertexArray[m_VertexNum+5]= arrowStart+a;
		 m_VertexArray[m_VertexNum+6]=arrowEnd;
		 m_VertexArray[m_VertexNum+7]=arrowEnd;


			  for (int i=0 ; i < useVertex ; i++ ) 
			  {
				 m_VertexArray[m_VertexNum+i].x =-1.0 +m_VertexArray[m_VertexNum+i].x/(WinW/2.);
				 m_VertexArray[m_VertexNum+i].y = 1.0 -m_VertexArray[m_VertexNum+i].y/(WinH/2.);
				 m_VertexArray[m_VertexNum+i].z =start.z;
			  }
		  
	
	}

	
	m_VertexNum+=useVertex;
	m_IndexNum+=6*2;
}


bool RC_2DPorigon::Set_Line(D3DXVECTOR3 start,D3DXVECTOR3 end,float bold,D3DXVECTOR4 color)
{
  int useVertex=4;
  if((useVertex+m_VertexNum)/4 >= m_Size){return 1;}
  LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();


  //頂点カラー
	for(int i=0;i<useVertex;i++)
	{
		m_ColorArray[m_VertexNum+i] =  color;
		m_VertexArray[m_VertexNum+i]=D3DXVECTOR3(0,0,0);
	}

	{
		 D3DVIEWPORT9    vp;

		 int WinW;
		 int WinH;

		 if(FAILED(dDevice->GetViewport(&vp))) {return 1;}

		 WinW = vp.Width;
		 WinH = vp.Height; 

		 float x,y,w,h;

		 D3DXVECTOR2 vector;
		 vector.x=end.x-start.x;
		 vector.y=end.y-start.y;
		 float len=D3DXVec2Length(&vector);
		 D3DXVec2Normalize(&vector,&vector);

		 float way=Sum_FlatAngle360(&vector);

	     D3DXVECTOR3 vec3;
		 vec3.x=vector.x;
		 vec3.y=vector.y;
		 vec3.z=0;

		 x=f_Point.x; 
		 y=f_Point.y; 
		 w=f_Size.x;
		 h=f_Size.y;

		 bold/=2;

		 D3DXVECTOR3 a,b;
		 a=D3DXVECTOR3(bold*sin(D3DXToRadian(way-90)), bold*cos(D3DXToRadian(way-90)), 0);
		 b=D3DXVECTOR3(bold*sin(D3DXToRadian(way+90)), bold*cos(D3DXToRadian(way+90)), 0);

		 D3DXVECTOR3 lineStart=start;//+vec3*space;
		 D3DXVECTOR3 arrowStart=end;//+vec3*Cut0f(len-arrowSize-space);

		 m_VertexArray[m_VertexNum]=lineStart+b;
		 m_VertexArray[m_VertexNum+1]= lineStart+a;
		 m_VertexArray[m_VertexNum+2]=arrowStart+b;
		 m_VertexArray[m_VertexNum+3]=arrowStart+a;


			  for (int i=0 ; i < useVertex ; i++ ) 
			  {
				 m_VertexArray[m_VertexNum+i].x =-1.0 +m_VertexArray[m_VertexNum+i].x/(WinW/2.);
				 m_VertexArray[m_VertexNum+i].y = 1.0 -m_VertexArray[m_VertexNum+i].y/(WinH/2.);
				 m_VertexArray[m_VertexNum+i].z =start.z;
			  }
		  
	
	}

	
	m_VertexNum+=useVertex;
	m_IndexNum+=6;
}


bool RC_2DPorigon::Set()
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

	if(m_Texture)
	{////////////////////////////////////////////////////////////////////////////////////

			D3DXVECTOR2 offset;

			offset.x = 0.5/m_Texture->SIZEW();
			offset.y = 0.5/m_Texture->SIZEH();

			if(f_Inverse ==0)
			 {
			   m_TexUVArray[m_VertexNum]= offset+D3DXVECTOR2( f_TexUV.x/m_Texture->SIZEW(),  f_TexUV.y/m_Texture->SIZEH());
			   m_TexUVArray[m_VertexNum+1] = offset+D3DXVECTOR2( (f_TexUV.x+f_TexUV.z)/m_Texture->SIZEW(),f_TexUV.y/m_Texture->SIZEH());
			   m_TexUVArray[m_VertexNum+2] = offset+D3DXVECTOR2( f_TexUV.x/m_Texture->SIZEW(), (f_TexUV.y+f_TexUV.w)/m_Texture->SIZEH());
			   m_TexUVArray[m_VertexNum+3] = offset+D3DXVECTOR2( (f_TexUV.x+f_TexUV.z)/m_Texture->SIZEW(), (f_TexUV.y+f_TexUV.w)/m_Texture->SIZEH());
			 }
			 else
			 {/*
			  m_TextureUVArray[m_VertexNum+1]= offset+D3DXVECTOR2( dataStr->m_TexX/m_Premise.m_Texture->Desc.Width,  dataStr->m_TexY/m_Premise.m_Texture->Desc.Height);
			  m_TextureUVArray[m_VertexNum+0] = offset+D3DXVECTOR2( (dataStr->m_TexX+dataStr->m_TexW)/m_Premise.m_Texture->Desc.Width,dataStr->m_TexY/m_Premise.m_Texture->Desc.Height);
			  m_TextureUVArray[m_VertexNum+3] = offset+D3DXVECTOR2( dataStr->m_TexX/m_Premise.m_Texture->Desc.Width, (dataStr->m_TexY+dataStr->m_TexH)/m_Premise.m_Texture->Desc.Height);
			  m_TextureUVArray[m_VertexNum+2] =offset+ D3DXVECTOR2( (dataStr->m_TexX+dataStr->m_TexW)/m_Premise.m_Texture->Desc.Width, (dataStr->m_TexY+dataStr->m_TexH)/m_Premise.m_Texture->Desc.Height);
 */
			 }	
	}


 m_VertexNum+=4;
 m_IndexNum+=6;

	return 0;
}


void RC_2DPorigon::Draw()
{
 if(m_VertexNum==0){return;}
 DrawStanby();
 if(m_BuildEnd==0){return;}
 LPDIRECT3DDEVICE9 dDevice=NHLib->GetDirectXDevice__();

 dDevice->SetVertexDeclaration( m_MyNest->m_Declare_Normal);

   dDevice->SetRenderState( D3DRS_CULLMODE , D3DCULL_NONE );

 m_MyNest->m_FX_Square2D.Pointer->SetTechnique(m_MyNest->m_FX_Square2D.hTech);
  m_MyNest->m_FX_Square2D.Pointer->Begin(0, 0);

 

  dDevice->SetIndices(m_IndexBuf);
  dDevice->SetStreamSource( 0, m_PositionBuf, 0, sizeof( D3DXVECTOR3 ) );
  dDevice->SetStreamSource( 1, m_ColorBuf, 0, sizeof( D3DXVECTOR4 ));
  dDevice->SetStreamSource( 2, m_TexUVBuf, 0, sizeof( D3DXVECTOR2 ) );

  int texPlus=0;
  if(m_Texture)
  {
   texPlus=2;
   m_MyNest->m_FX_Square2D.Pointer->SetTexture(m_MyNest->m_FX_Square2D.hTexture, (m_Texture->GetRes()));
  }
  	
  m_MyNest->m_FX_Square2D.Pointer->CommitChanges();


    if(m_UseDepth==0)
    {
     m_MyNest->m_FX_Square2D.Pointer->BeginPass(texPlus);
    }
    else
    {
     m_MyNest->m_FX_Square2D.Pointer->BeginPass(1+texPlus);
    }
	    
  dDevice->DrawIndexedPrimitive(D3DPT_TRIANGLELIST, 0, 0, m_VertexNum,0,m_IndexNum/3);

  m_MyNest->m_FX_Square2D.Pointer->EndPass();
  m_MyNest->m_FX_Square2D.Pointer->End();		


  if(m_DrawRefreshOn)
  {
   m_BuildEnd=0;
   m_VertexNum=0;
   m_IndexNum=0;
  }
}