#include "R_List.h"

///////////////////////////////////////////////////////////////////////////////////////////////////
//C_Task
///////////////////////////////////////////////////////////////////////////////////////////////////
 
// コンストラクタ
C_List::C_List(C_ListNest* mother)
:	Mother(mother)
{
	Next=mother->ActiveList;
	Back=mother->ActiveList->Back;

	Next->Back=this;
	Back->Next=this;

	Idle=0;


	
}



void C_List::Delete()
{

	
 C_List *index= this;
 
  

   index->Back->Next=index->Next;
   index->Next->Back=index->Back;
   
        

  
  delete this;
 
}
         
C_List::~C_List()
{
 
}
        

///////////////////////////////////////////////////////////////////////////////////////////////////
//C_TaskNest
///////////////////////////////////////////////////////////////////////////////////////////////////


C_ListNest::C_ListNest()
{

	// アクティブタスクリストの初期化
	ActiveList=new C_List();

	ActiveList->Back=ActiveList->Next=ActiveList;


	

}

C_ListNest::~C_ListNest()
{

 Clear();
 
  ActiveList->Delete();

}


C_List *C_ListNest::NextList(C_List *Object)
{
 C_List* Ms;

 if(Object == NULL)
 {
	Ms=ActiveList->Next;
	if(Ms == ActiveList)
	{return NULL;}
	 return ActiveList->Next;
 }
 
 
 
 Ms = Object->Next;
 
 if(Ms == ActiveList){return NULL;}

 return Ms;
}


C_List *C_ListNest::BackList(C_List *Object)
{
 C_List* Ms;

 if(Object == NULL)
 {
	Ms=ActiveList->Back;
	if(Ms == ActiveList)
	{return NULL;}
	 return ActiveList->Back;
 }
 
 
 
 Ms = Object->Back;
 
 if(Ms == ActiveList){return NULL;}

 return Ms;
}


void C_ListNest::Clear()
{
 C_List *index,*subindex;
 index = ActiveList->Next;
         
	 

 while(1)
 {
  if(index == ActiveList){break;}

        subindex = index->Next;

		index->Delete();
   
        index = subindex;
 }

}


