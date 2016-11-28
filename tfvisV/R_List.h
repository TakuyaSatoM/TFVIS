
#ifndef _RList_h_
#define _RList_h_

#include <stdio.h>
#include <assert.h>
#pragma warning(disable:4291) 
 
class C_List;
class C_ListNest;

class C_List
{
 private:
 friend class C_ListNest;

 C_List *Next,*Back;
 
 C_List(){}


 bool Idle; 
 protected:
 C_ListNest    *Mother;
 

 virtual ~C_List();

 public:
 
 C_List(C_ListNest* mother);
 
 
 void Delete();
 bool IsActive(){return Idle;}
 void Stop(){Idle=1;}
 void Start(){Idle=0;}
 bool IsStop(){return Idle;}
 C_List* NEXT(){return Next;}
 C_List* BACK(){return Back;}

};



class C_ListNest
{
 private:
 friend C_List;

  C_List *ActiveList;

 
 protected:

 public:
 int NumUseList;
  C_ListNest();
  ~C_ListNest();
  void Clear();
  C_List *NextList(C_List *Object);
  C_List *BackList(C_List *Object);
};




#endif