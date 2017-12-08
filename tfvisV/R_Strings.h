#ifndef _R_Strings_h_
#define _R_Strings_h_
#include "Common.h"
#include <string>
#include "R_List.h"
using namespace std;

string GetResourcePointString(string FileName);
string GetResourceNameString(string FileName);



 
class C_StringVectorChild:public C_List
{
public:
  C_StringVectorChild(C_ListNest* list,string str):C_List(list){m_Text=str;}
  string m_Text;
};
class C_StringVector:public C_ListNest
{
 public:
	 C_StringVector(){}
 
	 void add(string str);
	 string Get(int x);
	 void Delete(int x);
	 int Cap();

	 int Split(string origin,char del);

	 int Contain(string name);
};

class C_AdVectorChild:public C_List
{
public:
  C_AdVectorChild(C_ListNest* list,void* str):C_List(list){m_Ad=str;}
  void* m_Ad;
};
class C_AdVector:public C_ListNest
{
 public:
	 C_AdVector(){}
 
	 void add(void* str);
	 void* Get(int x);
	 int Cap();

	 int Split(string origin,char del);

	 int Contain(void* number);
};

#endif