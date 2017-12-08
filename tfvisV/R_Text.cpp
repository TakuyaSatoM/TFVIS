#include "R_Math.h"
#include "R_Set.h"

namespace TEXT
{
	bool headCut(string ori,char delim,string& head,string& rest)
	{
		char text[256];
		sprintf(text,"%s",ori.c_str());


		for(int i=0;;i++)
		{
			if(text[i]==0){break;}

			if(text[i]==delim)
	 		{

				text[i]=0;
				head=text;

				rest=&text[i+1];
				return true;
			}
		}	

		rest=text;
		return false;
	}

	void Seek(char* text,char delim,int* seek,char* get)
	{
		int tmpCount=0;
		int i;
		for(i=*seek;;i++)
		{
			if(text[i]==delim)
			{
				get[tmpCount]=0;
				i++;
				break;
			}

			get[tmpCount++]=text[i];
			if(text[i]==0){break;}
		}	

		*seek=i;

	}


	void SplitInt(char* text,char delim,C_Int* result)
	{
		char tmp[256];
		int tmpCount=0;

		for(int i=0;;i++)
		{
			if(text[i]==0){break;}

			if(text[i]==delim)
			{
				tmp[tmpCount]=0;
				tmpCount=0;
				result->Add(new C_Int(atoi(tmp)));

				continue;
			}

			tmp[tmpCount++]=text[i];
		}
	}

	void SplitString(char* text,char delim,C_String* result)
	{
		char tmp[256];
		int tmpCount=0;

		for(int i=0;;i++)
		{
			if(text[i]==0){break;}

			if(text[i]==delim)
			{
				tmp[tmpCount]=0;
				tmpCount=0;
				result->Add(new C_String(tmp));

				continue;
			}

			tmp[tmpCount++]=text[i];
		}
	}
}