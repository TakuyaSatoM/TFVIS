//
// Window.cpp
// アプリケーション(ウィンドウ)の実装
//
// コマンドラインパラメータに/fをつけるとフルスクリーン表示
//

#include <windows.h>

#include "Game.h"
#include "R_LibCore.h"

extern C_GameNest* GameNest;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{

	//メモリリーク検出
	_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);

	//CRTメモリリーク箇所箇所
	//_CrtSetBreakAlloc(2249);

	//メインウィンドウ設定
	RS_WindowSetData windowSetData;
	windowSetData.m_hInstance= hInstance;
	windowSetData.m_IsFullScreen=false;
	windowSetData.m_WindowSizeW=WINDOWSIZEW;
	windowSetData.m_WindowSizeH=WINDOWSIZEH;
	windowSetData.m_WindowName = TEXT("TFVIS");

	_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
   //_CrtSetBreakAlloc(434);

	//DirectX準備
	if(CreateNHLibrary(&windowSetData)){
	    CheckBox("NHLib生成失敗");
		 return 1;
	}

	//フレームレート基準値設定
	SetFrameRate(DEFAULT_FPS);	

	
	//メイン処理
	while(!MessageCheck()){

		if(GameNest){
			if(IsDeviceLost()){G()->m_DeviceLost=1;}
			else{
				if(G()->m_DeviceLost == 1){
					G()->m_DeviceLost=0;
					 G()->SystemRecover();
				}
			}
		}
     
		if(DXSceneStart()){Sleep(100); continue;}

		if(GameNest==NULL){
			GameNest = new C_GameNest;
			G()->Create();
		}

		G()->doMainTask();
	

		DXSceneEnd();
	}

	//後始末
	delete GameNest;
	return EndNHLibrary();
}
