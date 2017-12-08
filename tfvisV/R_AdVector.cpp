#include "R_Strings.h"


///////////////////////////////////////////////////////////////////////////////////////
void C_AdVector::add(void* str)
{
 new  C_AdVectorChild(this,str);
}
 
void*  C_AdVector::Get(int x)
{
 int count=0;
 C_List* index=NULL;

 while(index=this->NextList(index))
 {
	 if(x==count){return ((C_AdVectorChild*)index)->m_Ad;}
	 count++;
 }
 return NULL;
}

int C_AdVector::Cap()
{
 int count=0;
 C_List* index=NULL;

 while(index=this->NextList(index))
 {count++;}
 return count;
}

int C_AdVector::Split(string origin,char del)
{
	int x=0;
	string tmp="";
	for(int i=0;i<origin.size();i++)
	{
		if(origin[i]==del)
		{
			x++;
			add((void*)atoi(tmp.c_str()));
		 tmp="";
		 continue;
		}

		tmp+=origin[i];
	}

	if(tmp.size() > 0){ add((void*)atoi(tmp.c_str()));x++;}
	return x;
}

int  C_AdVector::Contain(void* number)
{
 int out=-1;
 C_List* index=NULL;
 C_AdVectorChild* child;
 int count=0;
 while(index=this->NextList(index))
 {
	 child = (C_AdVectorChild*)index;

	 if(child->m_Ad == number){return count;}

	 count++;
 }
 return out; 


}