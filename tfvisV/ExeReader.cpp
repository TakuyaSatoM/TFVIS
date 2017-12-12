#include "ExeReader.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;
#include "Exe.h"
#include "Method.h"

/*-------------------------------------------------------------
アニメデータの読み込み
return アニメデータ
*///-----------------------------------------------------------
void ExeReader::read(Exe* top)
{
 if(top == NULL){return;}

 MethodExe::ms_ExistNum=0;

 int md=100000;
 int count=0;
  char ref[1024];
  boolean isLoop=false;

 while(true)
 {
	 if(SeekDel('#')){return;}
	 
	 Exe* index;
	 top->Add(index=new Exe);
	 
	 //種別
	 index->m_EventType=Read_Int();

	 index->m_Number=count++;

	 //インスタンスID
	 if(SeekDel('#')){return;}
	 index->m_InstanceID=Read_Int();

	 //メソッドID
	 if(SeekDel('#')){return;}
	 index->m_MethodID=Read_Int();

	 //exeMethod
	 if(SeekDel('#')){return;}
	 index->m_MethodExeID=Read_Int();
	 MethodExe::ms_ExistNum=max(MethodExe::ms_ExistNum,index->m_MethodExeID+1);

	 //Line
	 if(SeekDel('#')){return;}
	 index->m_LineID=Read_Int();
	
	 //update
	 if(ev::isUpdate(index->m_EventType) )
	 {

		 if(SeekDel('#')){return;}

		 if(index->m_EventType==ev::UPDATE_INT){
			 index->m_Event=new E_Update;
			 ((E_Update*)index->m_Event)->SetInt(StockText);
			 if(SeekDel('#')){return;}
			 ((E_Update*)index->m_Event)->setInputState(StockText);
		 }else if(index->m_EventType==ev::UPDATE_INTARRAY){
			 index->m_Event=new E_Update;
			 ((E_Update*)index->m_Event)->SetIntArray(StockText);
			 if(SeekDel('#')){return;}
			 ((E_Update*)index->m_Event)->setInputState(StockText);
		 }
		 else if(index->m_EventType==ev::UPDATE_DOUBLE){
			 index->m_Event=new E_Update;
			 ((E_Update*)index->m_Event)->SetDouble(StockText);
			 if(SeekDel('#')){return;}
			 ((E_Update*)index->m_Event)->setInputState(StockText);
		 }else if(index->m_EventType==ev::UPDATE_DOUBLEARRAY){
			 index->m_Event=new E_Update;
			 ((E_Update*)index->m_Event)->SetDoubleArray(StockText);
			 if(SeekDel('#')){return;}
			 ((E_Update*)index->m_Event)->setInputState(StockText);
		 }
		 else if(index->m_EventType==ev::UPDATE_STRING){
			 index->m_Event=new E_Update;
			 ((E_Update*)index->m_Event)->SetString(StockText);
			 if(SeekDel('#')){return;}
			 ((E_Update*)index->m_Event)->setInputState(StockText);
		 }else if(index->m_EventType==ev::UPDATE_STRINGARRAY){
			 index->m_Event=new E_Update;
			 ((E_Update*)index->m_Event)->SetStringArray(StockText);
			 if(SeekDel('#')){return;}
			 ((E_Update*)index->m_Event)->setInputState(StockText);
		 }
	 }
	 if(index->m_EventType==ev::LIFELIMIT){
		 if(SeekDel('#')){return;}
		 index->m_Event=new E_LifeLimit(StockText);
	 }
	 if(index->m_EventType==ev::ROUTE){
		 index->m_Event=new E_Con();
	 }
	 if(index->m_EventType==ev::LOOP_NEXT && isLoop){
		 if(SeekDel('#')){return;}
		 index->m_Event=new E_LoopNext(NULL);
	 }
	 if(index->m_EventType==ev::LOOP_END && isLoop){
		 index->m_Event=new E_LoopEnd(NULL);
	 }
	 if(index->m_EventType==ev::LOOP_START){
		 index->m_Event=new E_LoopStart(NULL);
		 isLoop=true;
	 }	  
	 if(index->m_EventType==ev::TRY){
		 index->m_Event=new E_Try(NULL);
	 }
	 if(index->m_EventType==ev::TRY_END){
		 index->m_Event=new E_TryEnd(NULL);
	 }
	 if(index->m_EventType==ev::CATCH){
		 index->m_Event=new E_Catch(NULL);
	 }
	 if(index->m_EventType==ev::SWITCH){
		 index->m_Event=new E_Switch(NULL);
	 }
	 if(index->m_EventType==ev::CASE){
		 index->m_Event=new E_Switch(NULL);
	 }
	 if(index->m_EventType==ev::DATAREAD){
		 if(SeekDel('#')){return;}
		 //StockText
		 TEXT::SplitString(StockText,',',&index->m_Ref);
	 }
 }


 return;
} 
