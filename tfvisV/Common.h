//
// Common.h
// 共通で使用するマクロや定数の定義
//

#ifndef _CommonA_h_
#define _CommonA_h_
#define _CRT_SECURE_NO_WARNINGS

#include <windows.h>
#include <stdio.h> //for sprintf
#include <assert.h>


#ifdef _DEBUG
#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#endif

#define CheckBox(txt){ MessageBoxA(NULL, txt, ("Error"), MB_OK | MB_ICONEXCLAMATION);}


class INT2
{
public:
 int x,y;

 INT2(){x=y=0;}
 INT2(int ix,int iy){x=ix;y=iy;}
  INT2(int a){x=a;y=a;}

  int SIZE(){return x*y;}


INT2 operator + (INT2 obj) {
	    
		return INT2(this->x+obj.x,this->y+obj.y);
	}

INT2 operator - (INT2 obj) {
	    
		return INT2(this->x-obj.x,this->y-obj.y);
	}
INT2 operator * (INT2 obj) {
	    
		return INT2(this->x*obj.x,this->y*obj.y);
	}
INT2 operator / (INT2 obj) {
	    
		return INT2(this->x/obj.x,this->y/obj.y);
	}

bool operator == (INT2 obj) {
	    
	if(this->x == obj.x && this->y == obj.y){return 1;}
		return 0;
	}

bool operator != (INT2 obj) {
	    
	if(this->x != obj.x || this->y != obj.y){return 1;}
		return 0;
	}


};


// メモリの解放
#define SAFE_DELETE(p)  { if(p) { delete (p);     (p)=NULL; } }

// 参照カウンタのデクリメント
#define SAFE_RELEASE(p) { if(p) { (p)->Release(); (p)=NULL; } }

// エラーの報告とアプリケーションの終了
#define ERROR_EXIT() { int line = __LINE__; const char *file = __FILE__;\
	char msg[_MAX_FNAME + _MAX_EXT + 256];\
	char drive[_MAX_DRIVE];\
	char dir[_MAX_DIR];\
	char fname[_MAX_FNAME];\
	char ext[_MAX_EXT];\
	_splitpath(file, drive, dir, fname, ext);\
	sprintf(msg, "何らかのエラーが発生したためアプリケーションを終了します\r\n"\
		"ファイル : %s%s\r\n"\
		"行番号 : %d", fname, ext, line);\
	MessageBoxA(NULL, msg, "Error", MB_OK | MB_ICONEXCLAMATION);\
	PostQuitMessage(1);\
}

#endif // _Common_h_