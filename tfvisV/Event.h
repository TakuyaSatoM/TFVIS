#ifndef _Event_h_
#define _Event_h_
#include "GameDefine.h"



namespace ev{

	static const int NONE=0;
	static const int CLASS_START=0;
	static const int CLASS_END=1;
	static const int METHOD_START=100;
	static const int METHOD_END=101;
	static const int RETURN=150;
	static const int SWITCH=160;
	static const int CASE=162;
	static const int ROUTE=170;
	static const int UPDATE_INT=200;
	static const int UPDATE_INTARRAY=201;
	static const int UPDATE_DOUBLE=210;
	static const int UPDATE_DOUBLEARRAY=211;
	static const int UPDATE_STRING=220;
	static const int UPDATE_STRINGARRAY=221;
	static const int METHOD_CALL=250;
	static const int UPDATE_INSTANCE=260;
	static const int LOOP_START=300;
	static const int LOOP_NEXT=301;
	static const int LOOP_END=302;
	static const int TRY=380;
	static const int CATCH=390;
	static const int TRY_END=352;
	static const int DATAREAD=500;
	static const int LIFELIMIT=680;
	static const int MEMO=980;
	static const int OTHER=999;

   bool checkEventEqual(int tA,int tB);
   string getEventViewText(int type);
   float getEventWeight(int type);

   bool isUpdate(int id);
   bool isArrayUpdate(int id);
    bool isArrayUpdate(string eventName);
   bool isInstanceUpdate(int id);
   bool isPrimitiveUpdate(string eventName);
   string getUpdateType(int id);

}




#endif 