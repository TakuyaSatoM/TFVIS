#include "R_Window.h"
#include "NHLib.h"
#include "R_LibCore.h"


//-------------------------------------------------------------
// ���b�Z�[�W�����p�R�[���o�b�N�֐�
// ����
//		hWnd	: �E�B���h�E�n���h��
//		msg		: ���b�Z�[�W
//		wParam	: ���b�Z�[�W�̍ŏ��̃p�����[�^
//		lParam	: ���b�Z�[�W��2�Ԗڂ̃p�����[�^
// �߂�l 
//		���b�Z�[�W��������
//-------------------------------------------------------------
LRESULT CALLBACK RL_WindowProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	m_DoubleClick=0;

    switch (msg) {
	case WM_CLOSE:				// �E�C���h�E������ꂽ
		PostQuitMessage(0);		// �A�v���P�[�V�������I������
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
