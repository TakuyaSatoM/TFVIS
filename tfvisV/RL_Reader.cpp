#include "RL_Reader.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

//�R���X�g���N�^-������
C_Reader::C_Reader(char* filename)
{
 Source=0;
 FileSeek=0;
 FileSize=0;
 
 for(int i=0;;i++)
 {
  m_FileName[i]=filename[i];
  
  if(m_FileName[i]==0)
  {break;}
 }




 ifstream ifs;
 ifs.open(filename);
   if( !ifs.is_open() )
   {
      //ERROR File�ǂݍ��ݎ��s
      Source=NULL;
      return;
   }
   //�\�[�X�ǂݍ���
   
   while(!ifs.eof()){ ifs.ignore(); FileSize++;} // �T�C�Y�擾
   ifs.clear(); ifs.seekg(0, ios_base::beg); // �t�@�C���|�C���^�������ʒu��
   Source = new char[FileSize];
   ZeroMemory( Source, FileSize );
   ifs.read( Source, FileSize-1 );

 return;
}

C_Reader::~C_Reader()
{
 if(Source)
 {delete[] Source;}
 return;
}



//FileSeek��"stop"�̎��܂ňړ������܂�
bool C_Reader::Goto(char stop)
{
for(FileSeek;;FileSeek++)
 {
  if(Source[FileSeek] == stop)
  {  FileSeek++;
   break;
  }
  if(Source[FileSeek] == 0)
  {return 1;}
 }

 return 0;
}



bool C_Reader::Check(char* text)
{
 int s = strlen(text);

 if(strncmp(StockText,text,s)==0)
 {return 1;}

 return 0;
}

bool C_Reader::NoUseWord()
{
 if(Source[FileSeek] == 10 || Source[FileSeek] == 32)
 {
  return 1;
 }

return 0;
}

bool C_Reader::Seek()
{
 bool getf=0;
 int  count=0;
 while(1)
 {
  if(Source[FileSeek] == 0)
  {
   if(getf == 1)
   {
    StockText[count]=0;
    return 0;
   }
   return 1;
  }
  if(NoUseWord())
  {
   
  
   if(getf==1)
   {
    StockText[count]=0;
    return 0;
   }
   FileSeek++;
   continue;
  }
 
  getf=1;
  StockText[count]=Source[FileSeek];
  FileSeek++;
  count++;
 }


 return 0;
}




bool C_Reader::SeekDel(char stop,bool space)
{
 int  memoSeek=0;
 
 
 for(FileSeek;;FileSeek++)
 {
  if(Source[FileSeek]==0){return 1;}//EEROR
  if(Source[FileSeek]==10){continue;}//���s
  if(Source[FileSeek]==32 && space){continue;}//�X�y�[�X
 
  if(Source[FileSeek] == stop)
  {
   FileSeek++;
   StockText[memoSeek]=0;
   return 0;
  }
  
  StockText[memoSeek]=Source[FileSeek];
  
  memoSeek++;
 }
 

}


int C_Reader::Read_Int(char* text)
{
 return atoi(text);
}



float C_Reader::Read_Float()
{
 return atof(StockText);
}