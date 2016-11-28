#include "R_Strings.h"


///////////////////////////////////////////////////////////////////////////////////////
void C_StringVector::add(string str)
{
 new  C_StringVectorChild(this,str);
}
 
string C_StringVector::Get(int x)
{
 int count=0;
 C_List* index=NULL;

 while(index=this->NextList(index))
 {
	 if(x==count){return ((C_StringVectorChild*)index)->m_Text;}
	 count++;
 }
 return "";
}

void C_StringVector::Delete(int x)
{
 int count=0;
 C_List* index=NULL;

 while(index=this->NextList(index))
 {
	 if(x==count){index->Delete();break;}
	 count++;
 }
 return;
}

int C_StringVector::Cap()
{
 int count=0;
 C_List* index=NULL;

 while(index=this->NextList(index))
 {count++;}
 return count;
}

int C_StringVector::Split(string origin,char del)
{
	int x=0;
	string tmp="";
	for(int i=0;i<origin.size();i++)
	{
		if(origin[i]==del)
		{
			x++;
		 add(tmp);
		 tmp="";
		 continue;
		}

		tmp+=origin[i];
	}

	if(tmp.size() > 0){ add(tmp);x++;}
	return x;
}

int  C_StringVector::Contain(string name)
{
 int out=-1;
 C_List* index=NULL;
 C_StringVectorChild* child;
 int count=0;
 while(index=this->NextList(index))
 {
	 child = (C_StringVectorChild*)index;

	 if(child->m_Text == name){return count;}

	 count++;
 }
 return out; 


}