#include "R_Window.h"
#include "NHLib.h"
#include "R_LibCore.h"


//-------------------------------------------------------------
// メッセージ処理用コールバック関数
// 引数
//		hWnd	: ウィンドウハンドル
//		msg		: メッセージ
//		wParam	: メッセージの最初のパラメータ
//		lParam	: メッセージの2番目のパラメータ
// 戻り値 
//		メッセージ処理結果
//-------------------------------------------------------------
LRESULT CALLBACK RL_WindowProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	m_DoubleClick=0;

    switch (msg) {
	case WM_CLOSE:				// ウインドウが閉じられた
		PostQuitMessage(0);		// アプリケーションを終了する
		break;
		case WM_LBUTTONDBLCLK:

			m_DoubleClick=1;
			break;
	case WM_SETFOCUS:
		
		ShiftWindowActive();
	


		break;
	case WM_KILLFOCUS:
	
		ShiftWindowPassive();
		break;

	default:
		return DefWindowProc(hWnd, msg, wParam, lParam);
    }
    return 0;
}
