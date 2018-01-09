#include "Event.h"
#include "Exe.h"

namespace ev{

	bool isArrayUpdate(int id){
		if(id == UPDATE_INTARRAY || id==UPDATE_DOUBLEARRAY || id==UPDATE_STRINGARRAY){
			return true;
		}
		return false;
	}

	bool isInstanceUpdate(int id){
		if(id == UPDATE_INSTANCE){
			return true;
		}
		return false;
	}

	bool isUpdate(int id){

		if(id==UPDATE_INT || id==UPDATE_INTARRAY ||
			id==UPDATE_DOUBLE || id==UPDATE_DOUBLEARRAY ||
			id==UPDATE_STRING || id==UPDATE_STRINGARRAY ||
			id==UPDATE_INSTANCE){return true;}
		return false;
	}

	string getUpdateType(int id){
		if(id==UPDATE_INT){return "int";}
		if(id==UPDATE_INTARRAY){return "int[]";}
		if(id==UPDATE_DOUBLE){return "double";}
		if(id==UPDATE_DOUBLEARRAY){return "double[]";}
		if(id==UPDATE_STRING){return "String";}
		if(id==UPDATE_STRINGARRAY){return "String[]";}

		return "Object";
	}

	float getEventWeight(int type){

		if(ev::isUpdate(type)){return 2*2;}

		switch(type){

			case ev::METHOD_START:
			case ev::METHOD_END:	
				{return 8*2; }
			case ev::DATAREAD:
			case ev::OTHER:
				{return 2*2;}
			case ev::LOOP_START:
			case ev::LOOP_NEXT:
			case ev::LOOP_END:
				{return 0;}
		}

		return 0;
	}

	string getEventViewText(int type){

		if(ev::isUpdate(type)){return "u";}

		switch(type)
		{
			case ev::CLASS_START:{return "Class";}
			case ev::METHOD_START:{return "Method";}
			case ev::LOOP_START:{return "Loop";}
			case ev::METHOD_CALL:{return "Call";}
			case ev::METHOD_END:{return "Back";}
			case ev::ROUTE:{return "Con";}
			case ev::RETURN:{return "Back";}
			case ev::CATCH:{return "Catch";}
		}
		return "";
	}

	bool checkEventEqual(int tA,int tB){

		if(tA==tB){return true;}

		if(tA==ev::RETURN && tB==ev::METHOD_END){return true;}
		if(tB==ev::RETURN && tA==ev::METHOD_END){return true;}

		return false;
	}
}



void DTAItem::release()
{
	if(m_Child){delete m_Child;}
	if(m_Sibling){delete m_Sibling;}

	m_Child=NULL;
	m_Sibling=NULL;
	m_Ad=NULL;
}

int E_Update::existRelation(E_Update* base,E_Update* inf)
{
	C_String* sin=&base->m_Infs;

	if(sin->NUM()==0){return -1;}
	int count=0;
	
	while(sin=sin->CHECK())
	{
		UpdateVars* uin=&inf->m_Updates;
		count=0;
		while(uin=uin->next())
		{
			if(uin->m_Target == sin->m_Text)
			{
				return count;
			}
			count++;
		}
	}
	return -1;
}

void E_Update::SetInt(char* stock)
{
	int seek=0;
	char get[256];
	//name
	TEXT::Seek(stock,',',&seek,get);
	string name=get;
	//value
	TEXT::Seek(stock,',',&seek,get);
	int value=atoi(get);
	m_Updates.Add(new UV_Int(name,instanceID,value));
	while(true)
	{
		TEXT::Seek(stock,',',&seek,get);
		
		if(get[0]==0){break;}
		m_Infs.Add(new C_String(get, instanceID));

	}



}
void E_Update::SetIntArray(char* stock)
{
	int seek=0;
	char get[256];
	char tmp[256];

	//name
	TEXT::Seek(stock,',',&seek,get);
	string baseName=get;

	//size
	TEXT::Seek(stock,',',&seek,get);
	int num=atoi(get);

	// 空配列の場合
	if(num == 0){
		sprintf(tmp,"%s[%d]",baseName.c_str(),num);
		m_Updates.Add(new UV_String(tmp,instanceID,""));
	}

	for(int i=0;i<num;i++)
	{
		TEXT::Seek(stock,',',&seek,get);
		int value=atoi(get);
	
		sprintf(tmp,"%s[%d]",baseName.c_str(),i);
		m_Updates.Add(new UV_Int(tmp,instanceID,value));

	}
	return;

}

void E_Update::SetDouble(char* stock)
{
	int seek=0;
	char get[256];
	//name
	TEXT::Seek(stock,',',&seek,get);
	string name=get;
	//value
	TEXT::Seek(stock,',',&seek,get);
	double value=atof(get);
	m_Updates.Add(new UV_Double(name,instanceID, value));

	while(true)
	{
		TEXT::Seek(stock,',',&seek,get);		
		if(get[0]==0){break;}
		m_Infs.Add(new C_String(get,instanceID));
	}
}

void E_Update::SetDoubleArray(char* stock)
{
	int seek=0;
	char get[256];
	char tmp[256];

	//name
	TEXT::Seek(stock,',',&seek,get);
	string baseName=get;

	//size
	TEXT::Seek(stock,',',&seek,get);
	int num=atoi(get);

	// 空配列の場合
	if(num == 0){
		sprintf(tmp,"%s[%d]",baseName.c_str(),num);
		m_Updates.Add(new UV_String(tmp,instanceID,""));
	}

	for(int i=0;i<num;i++)
	{
		TEXT::Seek(stock,',',&seek,get);
		int value=atof(get);
	
		sprintf(tmp,"%s[%d]",baseName.c_str(),i);
		m_Updates.Add(new UV_Double(tmp,instanceID,value));

	}
	return;

}

void E_Update::SetString(char* stock)
{
	int seek=0;
	char get[256];
	//name
	TEXT::Seek(stock,',',&seek,get);
	string name=get;
	//value
	TEXT::Seek(stock,',',&seek,get);
	m_Updates.Add(new UV_String(name,instanceID,get));

	while(true)
	{
		TEXT::Seek(stock,',',&seek,get);		
		if(get[0]==0){break;}
		m_Infs.Add(new C_String(get, instanceID));
	}
}

void E_Update::SetStringArray(char* stock)
{
	int seek=0;
	char get[256];
	char tmp[256];

	//name
	TEXT::Seek(stock,',',&seek,get);
	string baseName=get;

	//size
	TEXT::Seek(stock,',',&seek,get);
	int num=atoi(get);

	// 空配列の場合
	if(num == 0){
		sprintf(tmp,"%s[%d]",baseName.c_str(),num);
		m_Updates.Add(new UV_String(tmp,instanceID,""));
	}

	for(int i=0;i<num;i++)
	{
		TEXT::Seek(stock,',',&seek,get);
	
		sprintf(tmp,"%s[%d]",baseName.c_str(),i);
		m_Updates.Add(new UV_String(tmp,instanceID,get));

	}
	return;

}

void E_Update::SetInstance(char* stock)
{
	int seek=0;
	char get[256];
	char tmp[256];
	
	//変数名
	TEXT::Seek(stock,',',&seek,get);
	string name = get;

	//インスタンスの型
	TEXT::Seek(stock,',',&seek,get);
	string type = get;

	//生成するインスタンスのid
	TEXT::Seek(stock,',',&seek,get);
	int targetInstanceID = atoi(get);

	//size
	TEXT::Seek(stock,',',&seek,get);
	int fieldNum=atoi(get)-1;

	
	string* fields = new string[fieldNum];

	// コンパイル時に自動追加されるTP_CLASSIDを除く
	for(int i=0;i<fieldNum;i++)
	{
		TEXT::Seek(stock,',',&seek,get);
		fields[i] =get;
	}

	m_Updates.Add(new UV_Instance(name,instanceID,type,fields,fieldNum,targetInstanceID));

	return;

}

void E_Update::setInputState(char* Input){
	if(strcmp(Input, "true") == 0){
		standard_Input = true;
	}else{
		standard_Input = false;
	}
}

bool E_Update::getInputState(){
	return standard_Input;
}

E_LoopStart::E_LoopStart(Exe* start)
:Event()
{
	m_Start=start;
	m_Count=0;
}

E_LoopNext::E_LoopNext(Exe* start)
:Event()
{
	m_Start=start;
	m_Turn=0;
}

E_LoopEnd::E_LoopEnd(Exe* start)
:Event()
{
	m_Start=start;
}

E_Con::E_Con()
:Event()
{
	m_True=false;
}

E_LifeLimit::E_LifeLimit(string target)
:Event()
{
	m_Target=target;
}

E_Try::E_Try(Exe* Try)
:Event()
{
	m_Try=Try;
}

E_Catch::E_Catch(Exe* Try)
:Event()
{
	m_Try=Try;
}

E_TryEnd::E_TryEnd(Exe* Try)
:Event()
{
	m_Try=Try;
}

E_Switch::E_Switch(Exe* Case)
:Event()
{
	m_Case=Case;
}

E_Case::E_Case(Exe* Switch)
:Event()
{
	m_Switch=Switch;
}
