#include "R_Strings.h"


string GetResourcePointString(string FileName)
{
 int start=0;
	for(start=FileName.size()-1;start>=0;start--)
	{
	 if(FileName[start] == '\\')
	 {
	   start++;
	  break;
	 }
	}
	
	return FileName.substr(0,start);



}

string GetResourceNameString(string FileName)
{
 int start=0;
	for(start=FileName.size()-1;start>=0;start--)
	{
	 if(FileName[start] == '\\')
	 {
	  start++;
	  break;
	 }
	}
	
	return FileName.substr(start-1,FileName.size()-start);



}

