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


	// �E�B���h�E�N���X�̏�����
	WNDCLASSEX	wcex = {
		sizeof(WNDCLASSEX),				// ���̍\���̂̃T�C�Y
		CS_DBLCLKS,							// �E�C���h�E�̃X�^�C��(default)
		RL_WindowProc,						// ���b�Z�[�W�����֐��̓o�^
		0,								// �ʏ�͎g��Ȃ��̂ŏ��0
		0,								// �ʏ�͎g��Ȃ��̂ŏ��0
		m_HandleInstance,						// �C���X�^���X�ւ̃n���h��
		LoadIcon(m_HandleInstance , TEXT("KITTY")),							// �A�C�R���i�Ȃ��j
		LoadCursor(NULL, IDC_ARROW),	// �J�[�\���̌`
		NULL, NULL,						// �w�i�Ȃ��A���j���[�Ȃ�
		m_WindowName,						// �N���X���̎w��
		NULL							// ���A�C�R���i�Ȃ��j
	};

	// �E�B���h�E�N���X�̓o�^
	if(RegisterClassEx(&wcex) == 0){
		return 1;	// �o�^���s
	}


	
	
	// �E�B���h�E�̍쐬
	if(m_FullScreen) { // �t���X�N���[��
		int sw;
		int sh;
		// ��ʑS�̂̕��ƍ������擾
		sw = GetSystemMetrics(SM_CXSCREEN);
		sh = GetSystemMetrics(SM_CYSCREEN);

		m_HandleWindow= CreateWindow( 
					m_WindowName, 			// �o�^����Ă���N���X��
					m_WindowName, 			// �E�C���h�E��
					WS_POPUP,			// �E�C���h�E�X�^�C���i�|�b�v�A�b�v�E�C���h�E���쐬�j
					CW_USEDEFAULT, 					// �E�C���h�E�̉������̈ʒu
					CW_USEDEFAULT, 					// �E�C���h�E�̏c�����̈ʒu
					m_WindowSizeW, 		// �E�C���h�E�̕�
					m_WindowSizeH,		// �E�C���h�E�̍���
					NULL,				// �e�E�C���h�E�̃n���h���i�ȗ��j
					NULL,				// ���j���[��q�E�C���h�E�̃n���h��
					m_HandleInstance, 			// �A�v���P�[�V�����C���X�^���X�̃n���h��
					NULL				// �E�C���h�E�̍쐬�f�[�^
				);
	}
	else {
		m_HandleWindow = CreateWindow(m_WindowName, 
							m_WindowName, 
							WS_SYSMENU |WS_CAPTION  | WS_VISIBLE | WS_MINIMIZEBOX ,
							CW_USEDEFAULT, CW_USEDEFAULT, 
							CW_USEDEFAULT, CW_USEDEFAULT,
							NULL, NULL, m_HandleInstance, NULL);
		

		// �E�B���h�E�T�C�Y���Đݒ肷��
		RECT rect;
		int ww, wh;
		int cw, ch;

		// �E�C���h�E�S�̂̉����̕����v�Z
		GetWindowRect(m_HandleWindow, &rect);		// �E�C���h�E�S�̂̃T�C�Y�擾
		ww = rect.right - rect.left;	// �E�C���h�E�S�̂̕��̉������v�Z
		wh = rect.bottom - rect.top;	// �E�C���h�E�S�̂̕��̏c�����v�Z
		
		// �N���C�A���g�̈�̊O�̕����v�Z
		GetClientRect(m_HandleWindow , &rect);		// �N���C�A���g�����̃T�C�Y�̎擾
		cw = rect.right - rect.left;	// �N���C�A���g�̈�O�̉������v�Z
		ch = rect.bottom - rect.top;	// �N���C�A���g�̈�O�̏c�����v�Z

		ww = ww - cw;					// �N���C�A���g�̈�ȊO�ɕK�v�ȕ�
		wh = wh - ch;					// �N���C�A���g�̈�ȊO�ɕK�v�ȍ���

		// �E�B���h�E�T�C�Y�̍Čv�Z
		ww = m_WindowSizeW + ww;			// �K�v�ȃE�C���h�E�̕�
		wh = m_WindowSizeH + wh;		// �K�v�ȃE�C���h�E�̍���

		// �E�C���h�E�T�C�Y�̍Đݒ�
		SetWindowPos(m_HandleWindow , HWND_TOP, 0, 0, ww, wh, 0);

	}

	
	

	// �E�B���h�E�̕\��
    ShowWindow(m_HandleWindow , SW_SHOW);

	// WM_PAINT���Ă΂�Ȃ��悤�ɂ���
	ValidateRect(m_HandleWindow , 0);



return 0;
}


bool RC_WindowNest::MessageCheck__()
{


if(PeekMessage(&m_Msg, NULL, 0, 0, PM_REMOVE))
{
	if(m_Msg.message == WM_QUIT) {	// PostQuitMessage()���Ă΂ꂽ
	return 1;	//���[�v�̏I��
	}
	else {
				// ���b�Z�[�W�̖|��ƃf�B�X�p�b�`
			TranslateMessage(&m_Msg);
			DispatchMessage(&m_Msg);
			}
        }

return 0;
}