#include "R_Math.h"
#include <math.h>
#include "R_LibCore.h"


string CutExtension(string name)
{
 sprintf(NHLib->Text,"%s",name.c_str());
 int i=0;

 while(NHLib->Text[i] != 0 && NHLib->Text[i] != '.')
 {
  i++;
 } 
 NHLib->Text[i]=0;

 return NHLib->Text;
}


void FileNameAnalysis(char* text,char* fileWhere,char* fileName)
{
 int len=strlen(text);
 int line;
 char work[256];
  for(int i=0;;i++)
 {
  work[i]=text[i];
  
  if(work[i]==0)
  {break;}
 }


 for(line=len-2;line>0;line--)
 {
   if(work[line] == '\\')
   {
    line++;
  
    break;
   } 
 }

 sprintf_s(fileName,256,("%s"),&work[line]);


 if(line > 0)
 {
  work[line]=0;
  sprintf_s(fileWhere,256,"%s",work);
 }
 else
 {
     fileWhere[0]=0;
 }

 return;
}



string NumberPlusMinus(int a)
{
 //NHLib
	string r;
	sprintf_s(NHLib->Text, ("%d") ,a );

	if(a < 0)
	{
     r = NHLib->Text;

	 return r;
	}


	r = NHLib->Text;
	r = "+"+r;

	return r;
}