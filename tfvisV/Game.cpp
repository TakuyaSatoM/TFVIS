#include "Game.h"
#include "S_Base.h"


C_GameNest* GameNest=NULL;

C_GameNest* G(){return GameNest;}



C_GameNest::C_GameNest(){


 m_SelectExe=NULL;
 m_DeviceLost=0;

  mRT_ExeFlow=NULL;

 m_FlashCounter=0;



}


void C_GameNest::FrameDealing(){

	m_FlashCounter += GetLoopTime()*3;
	if(m_FlashCounter >= 2.0){m_FlashCounter=0;}
	m_InsZ=0;
}

float C_GameNest::FlashDecimal(){return  0.5+1*(abs(1.- m_FlashCounter));}

bool C_GameNest::Release(){delete m_MainTask;return 0;}

void C_GameNest::doMainTask(){m_MainTask->_Task();}

C_GameNest::~C_GameNest(){Release();}



bool C_GameNest::Create()
{
	m_MainTask=new S_Base;


 //レンダリングテクスチャ・バッファ
    
   
  m_CommonFont= DWordCreateFont(35);
  m_CommonFont_S= DWordCreateFont(20);

   //実行フロー
 mTexXY_SelectList=INT2(0,0);
 mDrawArea_SelectList=C_Box(15,35,195,485);
 mSize_SelectList=INT2(1000,1000);

 mRT_SelectList=CreateNewRenderingTexture( mSize_SelectList.x*2, mSize_SelectList.y*2);
 mRT_SelectList->SetColor( &D3DXCOLOR(0.9,0.9,0.9,1) );

 mTexXY_ExeFlow=INT2(0,0);
 mDrawArea_ExeFlow=C_Box(mDrawArea_SelectList.x+mDrawArea_SelectList.w+SCREEN_SPACE,35,WINDOWSIZEW-(mDrawArea_SelectList.x+mDrawArea_SelectList.w+SCREEN_SPACE),485);
 mSize_ExeFlow=INT2(2000,2000);

 mRT_ExeFlow=CreateNewRenderingTexture( mSize_ExeFlow.x*2, mSize_ExeFlow.y*2);
 mRT_ExeFlow->SetColor( &D3DXCOLOR(0.9,0.9,0.9,1) );

 mRT_Spe=CreateNewRenderingTexture( mSize_ExeFlow.x*2, mSize_ExeFlow.y*2);
 mRT_Spe->SetColor( &D3DXCOLOR(0.9,0.9,0.9,0) );

 // 注釈エリア
 mTexXY_Output=INT2(0,0);
 mDrawArea_Output=C_Box(15,mDrawArea_SelectList.y+mDrawArea_SelectList.h+SCREEN_SPACE,WINDOWSIZEW-SCREEN_SPACE,200-SCREEN_SPACE);
 mSize_Output=INT2(2000,1000);

 mRT_Output=CreateNewRenderingTexture( mSize_Output.x*2, mSize_Output.y*2);
 mRT_Output->SetColor( &D3DXCOLOR(0.9,0.9,0.9,1) );



  db::load();
  draw::stanby();


  ////////////////////////
return 0;
}





void C_GameNest::SystemRecover(){}