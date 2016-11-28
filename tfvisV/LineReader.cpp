#include "LineReader.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;
#include "Method.h"

#include "Exe.h"

/*-------------------------------------------------------------
アニメデータの読み込み
return アニメデータ
*///-----------------------------------------------------------
void LineReader::Read(Method* ctop)
{
 if(ctop == NULL){return;}

 m_Method=ctop;
 int count=0;
 int baseCount=0;
 int xoffset=0;
 int bold=20*2;
 char ref[1024];
 while(true)
 {
	if(SeekDel('#')){return;}
	C_Int types;
	TEXT::SplitInt(StockText,',',&types);

	if(SeekDel('#')){return;}
	string target=StockText;

	if(SeekDel('#')){return;}
	sprintf(ref,"%s",StockText);

	if(SeekDel('#',false)){return;}
	string text=StockText;
	

	C_Int* index=&types;
	bool top=true;
	while(index=index->next())
	{
		if(index->m_Var==ev::DATAREAD){continue;}

		C_Line* line;
		m_Method->m_Line.Add(line=new C_Line);

		line->m_Top=top;

		if(top==true){
			TEXT::SplitString(ref,',',&line->m_Ref);
		}

		top=false;
		line->m_EventType=index->m_Var;

		if(ev::isUpdate(line->m_EventType))
		{
			string head;
			string rest;
			if(TEXT::headCut(target.c_str(),',',head,rest))
			{
				line->m_Target=	head;
				if(head == "a")
				{
					int a=0;
				}
			}
			else{line->m_Target=target;}
			target=rest;		
		}

		line->m_Text=text;
		line->m_BaseID=baseCount;
		line->m_EventNum=types.NUM();

		line->m_ID=count++;
		 ctop->m_UseYArea=count*bold/2;

		 if( line->m_EventType==ev::METHOD_END || line->m_EventType==ev::LOOP_END){xoffset--;}
		line->m_Indent=xoffset;
		if( line->m_EventType==ev::METHOD_START || line->m_EventType==ev::LOOP_START){xoffset++;}
	}

	baseCount++;
 }



 return;
} 
