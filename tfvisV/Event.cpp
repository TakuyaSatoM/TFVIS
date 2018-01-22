#include "Event.h"
#include "Exe.h"

namespace ev{

	bool isArrayUpdate(int id){
		if(GENERATE_INSTANCE<=id && id<= UPDATE_BOOLEANARRAY && id%2 !=0){
			return true;
		}
		return false;
	}

	bool isArrayUpdate(string eventName){
		if(getUpdateType(eventName) % 2 !=0){
			return true;
		}
		return false;
	}

	bool isInstanceUpdate(int id){
		if(id == UPDATE_INSTANCE || id == GENERATE_INSTANCE){
			return true;
		}
		return false;
	}

	bool isPrimitiveUpdate(string eventName){

		if(getUpdateType(eventName) % 2 ==0 && getUpdateType(eventName) != UPDATE_INSTANCE){
			return true;
		}
		return false;
	}

	bool isPrimitiveUpdate(int eventNum){

		if(eventNum % 2 ==0 && eventNum >= UPDATE_INT){
			return true;
		}
		return false;
	}

	bool isUpdate(int id){

		if(GENERATE_INSTANCE<=id && id<= UPDATE_BOOLEANARRAY){return true;}
		return false;
	}

	string getUpdateType(int id){
		if(id==UPDATE_INT){return "int";}
		if(id==UPDATE_INTARRAY){return "int[]";}
		if(id==UPDATE_DOUBLE){return "double";}
		if(id==UPDATE_DOUBLEARRAY){return "double[]";}
		if(id==UPDATE_STRING){return "String";}
		if(id==UPDATE_STRINGARRAY){return "String[]";}
		if(id==UPDATE_SHORT){return "short";}
		if(id==UPDATE_SHORTARRAY){return "short[]";}
		if(id==UPDATE_LONG){return "long";}
		if(id==UPDATE_LONGARRAY){return "long[]";}
		if(id == UPDATE_STRING){return "byte";}
		if(id == UPDATE_STRINGARRAY){return "byte[]";}
		if(id == UPDATE_CHAR){return "char";}
		if(id == UPDATE_CHARARRAY){return "char[]";}
		if(id == UPDATE_FLOAT){return "float";}
		if(id == UPDATE_FLOATARRAY){return "float[]";}
		if(id == UPDATE_BOOLEAN){return "boolean";}
		if(id == UPDATE_BOOLEANARRAY){return "boolean[]";}

		return "Object";
	}

	int getUpdateType(string type){
		if(type == "int"){return UPDATE_INT;}
		if(type == "int[]"){return UPDATE_INTARRAY;}
		if(type == "double"){return UPDATE_DOUBLE;}
		if(type == "double[]"){return UPDATE_DOUBLEARRAY;}
		if(type == "String"){return UPDATE_STRING;}
		if(type == "String[]"){return UPDATE_STRINGARRAY;}
		if(type == "short"){return UPDATE_SHORT;}
		if(type == "short[]"){return UPDATE_SHORTARRAY;}
		if(type == "long"){return UPDATE_LONG;}
		if(type == "long[]"){return UPDATE_LONGARRAY;}
		if(type == "byte"){return UPDATE_STRING;}
		if(type == "byte[]"){return UPDATE_STRINGARRAY;}
		if(type == "char"){return UPDATE_CHAR;}
		if(type == "char[]"){return UPDATE_CHARARRAY;}
		if(type == "float"){return UPDATE_FLOAT;}
		if(type == "float[]"){return UPDATE_FLOATARRAY;}
		if(type == "boolean"){return UPDATE_BOOLEAN;}
		if(type == "boolean[]"){return UPDATE_BOOLEANARRAY;}

		return UPDATE_INSTANCE;
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

void E_Update::SetPrimitives(char* stock, int type)
{
	int seek=0;
	char get[256];
	//name
	TEXT::Seek(stock,',',&seek,get);
	string name=get;
	//value
	TEXT::Seek(stock,',',&seek,get);
	string value=get;
	m_Updates.Add(new UpdateVars(name,instanceID,value, ev::getUpdateType(type)));
	while(true)
	{
		TEXT::Seek(stock,',',&seek,get);
		
		if(get[0]==0){break;}
		m_Infs.Add(new C_String(get, instanceID));

	}



}
void E_Update::SetPrimitivesArray(char* stock, int type)
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
		m_Updates.Add(new UpdateVars(tmp,instanceID,"", ev::getUpdateType(type)));
	}

	for(int i=0;i<num;i++)
	{
		TEXT::Seek(stock,',',&seek,get);
		string value=get;
	
		sprintf(tmp,"%s[%d]",baseName.c_str(),i);
		m_Updates.Add(new UpdateVars(tmp,instanceID,value,ev::getUpdateType(type)));

	}
	return;

}

vector<string> split(const string &s, char delim) {
    vector<string> elems;
    string item;
	for (int i=0; i<s.length(); i++){
        if (s[i] == delim) {
            if (!item.empty())
                elems.push_back(item);
            item.clear();
        }
        else {
            item += s[i];
        }
    }
    if (!item.empty())
        elems.push_back(item);
    return elems;
}


Exe* getLastupdateEvent(string name, int targetInstanceID,Exe* exe){

	Exe* indexExe = exe;
	E_Update* updateEvent;

	// 各フィールドの型、更新値を取得
	while(indexExe = indexExe->back()){
		// イベントが変数更新の時
		if(ev::isUpdate(indexExe->m_EventType)){
			updateEvent = (E_Update*)indexExe->m_Event;
			// フィールド名とインスタンスIDが一致する変数更新の取得
			vector<string> m_TargetString = split(updateEvent->m_Updates.next()->m_Target, '[');
			if(m_TargetString[0] == name && indexExe -> m_InstanceID == targetInstanceID){
				break;
			}
		}
	}

	return indexExe;
}

void E_Update::recursiveMakeFieldEvent(vector<string> fieldName, int targetInstanceID, int fieldNum, Exe* exe){
	Exe* branch = exe;

	for(int i=0;i<fieldNum;i++){	
		Exe* targetUpdateExe = getLastupdateEvent(fieldName[i], targetInstanceID, exe);
		if(targetUpdateExe == nullptr){break;}

		Exe* updateFieldExe;
		if(i==0){
			branch->AddBranch(updateFieldExe = new Exe);
		}else{
			branch->AddNotOverwriteTop(updateFieldExe = new Exe);
		}


		updateFieldExe->m_EventType = targetUpdateExe->m_EventType;
		updateFieldExe->m_InstanceID = ((E_Update*)targetUpdateExe->m_Event)->instanceID;
		updateFieldExe->m_Number = exe->m_Number;
		updateFieldExe->m_MethodID = exe->m_MethodID;
		updateFieldExe->m_MethodExeID = exe->m_MethodExeID;
		updateFieldExe->m_LineID = exe->m_LineID;


	updateFieldExe->m_Event=new E_Update(updateFieldExe->m_InstanceID);
		
	if(ev::isPrimitiveUpdate(updateFieldExe->m_EventType)){
		// プリミティブ型更新イベントの追加
		((E_Update*)updateFieldExe->m_Event)->m_Updates.Add(new UpdateVars(fieldName[i],targetInstanceID,((E_Update*)targetUpdateExe->m_Event)->m_Updates.next()->m_Value, ev::getUpdateType(updateFieldExe->m_EventType)));
		if(((E_Update*)targetUpdateExe->m_Event)->m_Updates.next()->m_Value != ""){
			// 参照変数の追加
			 ((E_Update*)updateFieldExe->m_Event)->m_Infs.Add(new C_String(fieldName[i], targetInstanceID));
		 }
	}else if(ev::isArrayUpdate(updateFieldExe->m_EventType)){
		// 配列更新イベントの追加
		E_Update* fieldArrayUpdate = ((E_Update*)targetUpdateExe->m_Event);
		UpdateVars* index=&((E_Update*)targetUpdateExe->m_Event)->m_Updates;
		while(index = index->next()){
			((E_Update*)updateFieldExe->m_Event)->m_Updates.Add(new UpdateVars(index->m_Target, targetInstanceID, index->m_Value,ev::getUpdateType(updateFieldExe->m_EventType)));
		}
	 }else if(updateFieldExe->m_EventType == ev::UPDATE_INSTANCE){

		 if(((UV_Instance*)updateFieldExe->m_Event)->fieldNum <= 0){
			 continue;
		 }

		// インスタンス更新イベントの追加
		 UV_Instance* instanceUpdate = (UV_Instance*)((E_Update*)targetUpdateExe->m_Event)->m_Updates.next();
		 UV_Instance* instance= new UV_Instance(instanceUpdate->m_Target,instanceUpdate->instanceID,"",instanceUpdate->m_Type,instanceUpdate->fieldNum, instanceUpdate->m_TargetInstanceID);
		((E_Update*)updateFieldExe->m_Event)->m_Updates.Add(instance);

		int fieldNum = instanceUpdate->fieldNum; 

		Exe* index;
		if(index = targetUpdateExe->CHECK_BRANCH()){
			continue;	
		}

		vector<string> recursiveInstanceFieldName;
		recursiveInstanceFieldName.resize(instance->fieldNum);

		// インスタンス下インスタンスのフィールド名取得

		for(int j=0; j<instance->fieldNum;j++){
			E_Update* indexUpdate = (E_Update*)index->m_Event;
			UpdateVars* update = indexUpdate->m_Updates.next();
			recursiveInstanceFieldName[j] = update->m_Target;
			index = index->CHECK();
		}
		
		// 再帰的なイベント作成
		((E_Update*)updateFieldExe->m_Event)->recursiveMakeFieldEvent(recursiveInstanceFieldName,instance->instanceID,instance->fieldNum,updateFieldExe);
	 }
	((E_Update*)updateFieldExe->m_Event)->standard_Input = false;
	branch = updateFieldExe;
	}
	return;
}

void E_Update::SetInstance(char* stock,Exe* exe)
{
	int seek=0;
	char get[256];
	char tmp[256];
	string fieldName[64];
	
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
	// コンパイル時に自動生成するTP_CLASSIDを除く
	int fieldNum=atoi(get)-1;

	if(fieldNum > 0){
	UV_Instance* instance= new UV_Instance(name,instanceID,"",type,fieldNum,targetInstanceID);
	m_Updates.Add(instance);
	vector<string> fileName;
	fileName.resize(fieldNum);

	for(int i=0; i<fieldNum;i++){
		TEXT::Seek(stock,',',&seek,get);
		fileName[i] = get;	
	}

	recursiveMakeFieldEvent(fileName,targetInstanceID,fieldNum, exe);
	
	}else{
	UV_Instance* instance= new UV_Instance(name,instanceID,"nullptr",type,fieldNum,targetInstanceID);
	m_Updates.Add(instance);

	}
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
