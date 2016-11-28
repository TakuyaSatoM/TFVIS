//
// Window.cpp
// �A�v���P�[�V����(�E�B���h�E)�̎���
//
// �R�}���h���C���p�����[�^��/f������ƃt���X�N���[���\��
//

#include <windows.h>

#include "Game.h"
#include "R_LibCore.h"

extern C_GameNest* GameNest;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{

	//���������[�N���o
	_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);

	//CRT���������[�N�ӏ��ӏ�
	//_CrtSetBreakAlloc(2249);

	//���C���E�B���h�E�ݒ�
	RS_WindowSetData windowSetData;
	windowSetData.m_hInstance= hInstance;
	windowSetData.m_IsFullScreen=false;
	windowSetData.m_WindowSizeW=WINDOWSIZEW;
	windowSetData.m_WindowSizeH=WINDOWSIZEH;
	windowSetData.m_WindowName = TEXT("TFVIS");

	_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
   //_CrtSetBreakAlloc(434);

	//DirectX����
	if(CreateNHLibrary(&windowSetData)){
	    CheckBox("NHLib�������s");
		 return 1;
	}

	//�t���[�����[�g��l�ݒ�
	SetFrameRate(DEFAULT_FPS);	

	
	//���C������
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

	//��n��
	delete GameNest;
	return EndNHLibrary();
}
