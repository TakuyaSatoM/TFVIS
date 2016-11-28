#include "R_Renderer.h"
#include "NHLib.h"
#include "R_LibCore.h"

RC_RendererNest::RC_RendererNest()
 : m_pD3D(0), m_pD3DDevice(0)
{
} 

RC_RendererNest::~RC_RendererNest()
{
	Release();
}


bool RC_RendererNest::Init()
{

    D3DDISPLAYMODE d3ddm;
    
	// Direct3D9オブジェクトの作成
	if((m_pD3D = ::Direct3DCreate9(D3D_SDK_VERSION)) == 0){
        return 1;	// 取得失敗
    }
	
	// 現在のディスプレイモードを取得
    if(FAILED(m_pD3D->GetAdapterDisplayMode(D3DADAPTER_DEFAULT, &d3ddm))) {
		return 1;
	}

	// デバイスのプレゼンテーションパラメータを初期化
	ZeroMemory(&m_D3DPP, sizeof(D3DPRESENT_PARAMETERS));
	m_D3DPP.BackBufferCount			= 1;
	m_D3DPP.PresentationInterval=D3DPRESENT_INTERVAL_DEFAULT;

	if(IsFullScreen()) { // フルスクリーンの場合
		m_D3DPP.Windowed				= FALSE;			// フルスクリーン表示の指定
		m_D3DPP.BackBufferWidth			= GetWindowSizeW();		// フルスクリーン時の横幅
		m_D3DPP.BackBufferHeight		= GetWindowSizeH();		// フルスクリーン時の縦幅
	}
	else {
		m_D3DPP.Windowed				= TRUE;				// ウインドウ内表示の指定
	}
	m_D3DPP.BackBufferFormat		= d3ddm.Format;			// カラーモードの指定
	m_D3DPP.SwapEffect				= D3DSWAPEFFECT_DISCARD; 
	m_D3DPP.EnableAutoDepthStencil	= TRUE;
	m_D3DPP.AutoDepthStencilFormat	= D3DFMT_D24S8;
	
	// ディスプレイアダプタを表すためのデバイスを作成
	// 描画と頂点処理をハードウェアで行なう
	if(FAILED(m_pD3D->CreateDevice(D3DADAPTER_DEFAULT, 
									D3DDEVTYPE_HAL, 
									GetWindowHandle(), 
									D3DCREATE_HARDWARE_VERTEXPROCESSING, 
									&m_D3DPP, &m_pD3DDevice))) {
		// 上記の設定が失敗したら
		// 描画をハードウェアで行い、頂点処理はCPUで行なう
		if(FAILED(m_pD3D->CreateDevice(D3DADAPTER_DEFAULT, 
										D3DDEVTYPE_HAL, 
										GetWindowHandle(), 
										D3DCREATE_SOFTWARE_VERTEXPROCESSING, 
										&m_D3DPP, &m_pD3DDevice))) {
			// 上記の設定が失敗したら
			// 描画と頂点処理をCPUで行なう
			if(FAILED(m_pD3D->CreateDevice(D3DADAPTER_DEFAULT, 
											D3DDEVTYPE_REF, GetWindowHandle(), 
											D3DCREATE_SOFTWARE_VERTEXPROCESSING, 
											&m_D3DPP, &m_pD3DDevice))) {
				// 初期化失敗
				return 1;
			}
		}
	}


	return 0;
}

bool RC_RendererNest::SceneStart()
{
 if(IsDeviceLost())
 {
	 if (m_pD3DDevice->TestCooperativeLevel() != D3DERR_DEVICENOTRESET ){return 1;}


	 if(m_pD3DDevice->Reset(&m_D3DPP) != S_OK)
	 {
	  CheckBox("デバイス復帰失敗");
	  PostQuitMessage(0);
	 }
	 if(NHLib->DeviceLost_Restore())
	 {
	  CheckBox("再構築失敗");
	  PostQuitMessage(0);
	 }

	 ClearDeviceLost();

 }



 if(FAILED(m_pD3DDevice->Clear(0,NULL, 				// 初期化する領域は全面
					D3DCLEAR_TARGET |					// バックバッファを指定
					D3DCLEAR_ZBUFFER, 					// 深度バッファ（Zバッファ）を指定
					D3DXCOLOR(200./255,200./255,200./255, 1.0f), 	// 初期化する色
					1.0f, 								// 初期化する深度バッファ（Zバッファ）の値
					0))) {								// 初期化するステンシルバッファの値
		return 1;
	}

 GetDirectXDevice()->BeginScene();


 return 0;
}

bool RC_RendererNest::SceneEnd()
{
 if(IsDeviceLost())
 {return 0;}

	// 描画終了宣言
	m_pD3DDevice->EndScene();
	

	// 描画結果の転送
	if(FAILED(m_pD3DDevice->Present( 0, 0, 0, 0 ))) {
		// デバイス消失から復帰
		CallDeviceLost();

		NHLib->DeviceLost_Release();
		//m_pD3DDevice->Reset(&m_D3DPP);
	}


	return 0;
}



void RC_RendererNest::Release()
{
	// デバイスオブジェクトの解放
	SAFE_RELEASE(m_pD3DDevice);

	// DirectXGraphicsの解放
	SAFE_RELEASE(m_pD3D);
}