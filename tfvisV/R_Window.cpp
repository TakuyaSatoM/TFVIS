#include "R_Window.h"
#include "ORStr_Window.h"

RC_WindowNest::RC_WindowNest()
{
 m_FullScreen=0;
 m_WindowSizeW=640;
 m_WindowSizeH=480;
 m_WindowSleepTime=30;
 m_HandleWindow=NULL;
// m_Msg=NULL;
 m_HandleInstance=NULL;
 m_WindowName[0]=NULL;
 m_WindowActive=0;


} 

LRESULT CALLBACK RL_WindowProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

bool RC_WindowNest::Create(RS_WindowSetData* windowSetData)
{
	m_FullScreen=windowSetData->m_IsFullScreen;
	m_WindowSizeW=windowSetData->m_WindowSizeW;
	m_WindowSizeH=windowSetData->m_WindowSizeH;
	m_HandleInstance=windowSetData->m_hInstance;

	for(int ct_w=0;ct_w<256;ct_w++)
	{
		m_WindowName[ct_w]=windowSetData->m_WindowName[ct_w];
	
		if(windowSetData->m_WindowName[ct_w] == 0)
		{break;}
	}


	// ウィンドウクラスの初期化
	WNDCLASSEX	wcex = {
		sizeof(WNDCLASSEX),				// この構造体のサイズ
		CS_DBLCLKS,							// ウインドウのスタイル(default)
		RL_WindowProc,						// メッセージ処理関数の登録
		0,								// 通常は使わないので常に0
		0,								// 通常は使わないので常に0
		m_HandleInstance,						// インスタンスへのハンドル
		LoadIcon(m_HandleInstance , TEXT("KITTY")),							// アイコン（なし）
		LoadCursor(NULL, IDC_ARROW),	// カーソルの形
		NULL, NULL,						// 背景なし、メニューなし
		m_WindowName,						// クラス名の指定
		NULL							// 小アイコン（なし）
	};

	// ウィンドウクラスの登録
	if(RegisterClassEx(&wcex) == 0){
		return 1;	// 登録失敗
	}


	
	
	// ウィンドウの作成
	if(m_FullScreen) { // フルスクリーン
		int sw;
		int sh;
		// 画面全体の幅と高さを取得
		sw = GetSystemMetrics(SM_CXSCREEN);
		sh = GetSystemMetrics(SM_CYSCREEN);

		m_HandleWindow= CreateWindow( 
					m_WindowName, 			// 登録されているクラス名
					m_WindowName, 			// ウインドウ名
					WS_POPUP,			// ウインドウスタイル（ポップアップウインドウを作成）
					CW_USEDEFAULT, 					// ウインドウの横方向の位置
					CW_USEDEFAULT, 					// ウインドウの縦方向の位置
					m_WindowSizeW, 		// ウインドウの幅
					m_WindowSizeH,		// ウインドウの高さ
					NULL,				// 親ウインドウのハンドル（省略）
					NULL,				// メニューや子ウインドウのハンドル
					m_HandleInstance, 			// アプリケーションインスタンスのハンドル
					NULL				// ウインドウの作成データ
				);
	}
	else {
		m_HandleWindow = CreateWindow(m_WindowName, 
							m_WindowName, 
							WS_SYSMENU |WS_CAPTION  | WS_VISIBLE | WS_MINIMIZEBOX ,
							CW_USEDEFAULT, CW_USEDEFAULT, 
							CW_USEDEFAULT, CW_USEDEFAULT,
							NULL, NULL, m_HandleInstance, NULL);
		

		// ウィンドウサイズを再設定する
		RECT rect;
		int ww, wh;
		int cw, ch;

		// ウインドウ全体の横幅の幅を計算
		GetWindowRect(m_HandleWindow, &rect);		// ウインドウ全体のサイズ取得
		ww = rect.right - rect.left;	// ウインドウ全体の幅の横幅を計算
		wh = rect.bottom - rect.top;	// ウインドウ全体の幅の縦幅を計算
		
		// クライアント領域の外の幅を計算
		GetClientRect(m_HandleWindow , &rect);		// クライアント部分のサイズの取得
		cw = rect.right - rect.left;	// クライアント領域外の横幅を計算
		ch = rect.bottom - rect.top;	// クライアント領域外の縦幅を計算

		ww = ww - cw;					// クライアント領域以外に必要な幅
		wh = wh - ch;					// クライアント領域以外に必要な高さ

		// ウィンドウサイズの再計算
		ww = m_WindowSizeW + ww;			// 必要なウインドウの幅
		wh = m_WindowSizeH + wh;		// 必要なウインドウの高さ

		// ウインドウサイズの再設定
		SetWindowPos(m_HandleWindow , HWND_TOP, 0, 0, ww, wh, 0);

	}

	
	

	// ウィンドウの表示
    ShowWindow(m_HandleWindow , SW_SHOW);

	// WM_PAINTが呼ばれないようにする
	ValidateRect(m_HandleWindow , 0);



return 0;
}


bool RC_WindowNest::MessageCheck__()
{


if(PeekMessage(&m_Msg, NULL, 0, 0, PM_REMOVE))
{
	if(m_Msg.message == WM_QUIT) {	// PostQuitMessage()が呼ばれた
	return 1;	//ループの終了
	}
	else {
				// メッセージの翻訳とディスパッチ
			TranslateMessage(&m_Msg);
			DispatchMessage(&m_Msg);
			}
        }

return 0;
}