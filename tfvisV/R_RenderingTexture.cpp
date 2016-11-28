#include "R_RenderingTexture.h"
#include "NHLib.h"
#include "RL_Texture.h"
#include "R_LibCore.h"
#include "R_Screen.h"

RC_RenderingTexture::RC_RenderingTexture(C_ListNest* list)
:RC_ResourceList(list)
{	
 m_Surface= NULL;	
 m_ZBuffer = NULL;	
 Clear();
  
 m_TextureTask =  CreateNewTexture(NULL);
 m_Color = D3DXCOLOR(0.2f, 0.2f, 0.4f, 1.0f);

 m_DeviceLost=0;
}

RC_RenderingTexture::~RC_RenderingTexture()
{
 Release();
 m_TextureTask->Delete();
}



void RC_RenderingTexture::Clear()
{
Release();

m_Surface= NULL;	
m_ZBuffer = NULL;	



}

void RC_RenderingTexture::Release()
{
    SAFE_RELEASE(m_Surface);
	
	SAFE_RELEASE(m_ZBuffer);
}

bool RC_RenderingTexture::Build(DWORD sizeW,DWORD sizeH)
{
	m_Data.SetSize(sizeW,sizeH);

 if(GetDirectXDevice() == NULL)
 {
  _ASSERT_EXPR( 0, TEXT("RC_RenderingTexture::pD3DDeviceが不正です") );
 }

 LPDIRECT3DTEXTURE9	hResource;

 if( FAILED(GetDirectXDevice()->CreateTexture(
						sizeW,sizeH,				// テクスチャのサイズ 
						1,						// テクスチャレベルの数（本著では常に１に設定）
         				D3DUSAGE_RENDERTARGET,	// レンダリングターゲットとして利用することを明示
						D3DFMT_A8R8G8B8,		// テクスチャフォーマット（RGBAの～モードを設定）
         				D3DPOOL_DEFAULT,		// テクスチャメモリの確保方法（D3DPOOL_DEFAULTが無難）
         				&hResource,			// テクスチャの格納先を示すポインタのアドレス
						NULL))) {				// 常にNULLを指定
		_ASSERT_EXPR( 0, TEXT("RenderingTexture_Class::テクスチャ生成失敗") );
							return 1;
	}


	// テクスチャからレンダリングターゲットにするサーフェースの取得
	if( FAILED(hResource->GetSurfaceLevel(0, &m_Surface))){
		CheckBox(("RenderingTexture_Class::サーフェース生成失敗"));
		return E_FAIL;
	}

	// テクスチャへのレンダリングに使う深度バッファーの作成
	if (FAILED(GetDirectXDevice()->CreateDepthStencilSurface(
						sizeW,sizeH,				// 深度バッファのサイズ
						D3DFMT_D24X8,			// 深度バッファのフォーマット（24ビット）
						D3DMULTISAMPLE_NONE,	// 高度な補間方法の設定（本著では常にD3DMULTISAMPLE_NONE）
						0,						// 画像の品質レベルの設定（本著では常に0）
						TRUE,					// 深度バッファの制御方法の指定（常にTRUEにしておく）
						&m_ZBuffer,				// 深度バッファの格納先を示すポインタのアドレス
						NULL))){				// 常にNULLを指定
        CheckBox(("RenderingTexture_Class::深度バッファー生成失敗"));
        return E_FAIL;
	}



	m_TextureTask->Create(hResource);

	


return 0;
}

void RC_RenderingTexture::BufferClear()
{
 BufferClear(D3DCLEAR_TARGET |D3DCLEAR_ZBUFFER);
}

void RC_RenderingTexture::BufferClear(DWORD flag)
{
// レンダリングターゲットを初期化
	if(FAILED(GetDirectXDevice()->Clear(0,NULL, 
								flag, 
								 m_Color, // 背景を白に設定
								1.0f, 0))) {
									CheckBox(("RenderingTexture_Class::レンダイリングCLass 初期化失敗"));
		return;
	}

}

void RC_RenderingTexture::Set(bool clear)
{
	NHLib->GetScreenNest()->m_NowBuffer=this;
 // テクスチャをレンダリングターゲットに設定
	GetDirectXDevice()->SetRenderTarget(0, m_Surface);

	// テクスチャ用の深度バッファを設定
	GetDirectXDevice()->SetDepthStencilSurface(m_ZBuffer);

	if(clear)BufferClear();

	// ビューポートをテクスチャ画像のサイズに設定
}

bool RC_RenderingTexture::DeviceLost_Release()
{
 SAFE_RELEASE(m_TextureTask->hResource);
 SAFE_RELEASE(m_Surface);
 SAFE_RELEASE(m_ZBuffer);
 m_DeviceLost=1;
 return 0;
}
bool RC_RenderingTexture::DeviceLost_Restore()
{
 m_DeviceLost=0;


 if(Build(D()->SIZEW(),D()->SIZEH()))
 {return 1;}

 BufferClear();
 return 0;
}
