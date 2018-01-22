
#ifndef _RSet_h_
#define _RSet_h_

#include "Common.h"
#include <string>
using namespace std;

class  C_Set
{
 private:
 C_Set *f_Top;
 C_Set *f_Next,*f_Back;
 C_Set *f_Branch;
 

 public:
 
 C_Set();
 virtual ~C_Set(); 
 
 void Delete();
 void Out();

 bool END(){return f_Top==this;}

 C_Set* BACK(){return f_Back;}
 C_Set* NEXT(){return f_Next;}
 C_Set* BRANCH(){return f_Branch;}

 virtual C_Set* CHECK()
 {
	 if(f_Top==f_Next){return NULL;}
	 return f_Next;
 }
 virtual C_Set* CHECK_BACK()
 {
	 if(f_Top==f_Back){return NULL;}
	 return f_Back;
 }
 virtual C_Set* CHECK_BRANCH()
 {
	 if(f_Top==f_Back){return nullptr;}
	 return f_Branch;
 }
 
 int  NUM();
 virtual void Add(C_Set* in);
 virtual void AddBranch(C_Set* branch);
 virtual void AddNotOverwriteTop(C_Set* branch);

 void Reset();
};

class C_Int:public C_Set
{
public:

  C_Int()
  {
	m_Var=0;
  }
  C_Int(int i)
  {
	m_Var=i;
  }
  C_Int* next(){return (C_Int*)C_Set::CHECK();}
  C_Int* back(){return (C_Int*)C_Set::CHECK_BACK();}

  int m_Var;

};


class C_String:public C_Set
{
public:

  C_String(){}
  C_String(string text){
	m_Text=text;
	instanceID=0;
  }
  C_String(string text, int id)
  {
	m_Text=text;
	instanceID=id;
  }
  C_String* CHECK(){return (C_String*)C_Set::CHECK();}

  string m_Text;
  int instanceID;

};


#endif