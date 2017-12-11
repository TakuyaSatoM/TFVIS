#ifndef _RL_Reader_h_
#define _RL_Reader_h_
#include <windows.h>


 
class C_Reader
{
protected:

char m_FileName[256];
char StockText[256];//一時退避ボックス
char* Source;//ファイル内容
DWORD FileSeek;//読み込み地点
DWORD FileSize;//ファイルサイズ


bool Goto(char stop);//引数まで飛ぶ

bool SeekDel(char stop){return SeekDel(stop,true);}
bool SeekDel(char stop,bool space);
bool Seek();
virtual bool NoUseWord();

bool PickText();


bool Check(char* text);

int Read_Int(char* text);
int Read_Int(){return Read_Int(StockText);}
float Read_Float();

public:
C_Reader(char* filename);
virtual ~C_Reader();

char* GetSourceTop(){return Source;}

bool GetFIle(){return Source!=NULL;}


};


#endif