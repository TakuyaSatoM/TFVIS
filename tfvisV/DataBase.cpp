#include "Game.h"

namespace{
	C_Class n_Class;
	Exe n_Exe;
	MethodExe n_MethodExe;
}

namespace db{

	C_Class* getClass(){return &n_Class;}
	Exe* getExe(){return &n_Exe;}
	MethodExe* getMethodExe(){return &n_MethodExe;}

	//���\�b�h����(�Ǘ��ԍ��w��)
	Method* searchMethod(int globalId){

		C_Class* indexClass=getClass();
		while(indexClass=indexClass->next()){
			Method* indexMethod=&indexClass->m_Method;
			while(indexMethod=indexMethod->next()){
				if(indexMethod->m_MethodID == globalId){return indexMethod;}
			}
		}
		return NULL;
	}

	//�N���X����(�Ǘ��ԍ��w��)
	C_Class* searchClass(int globalId){

		C_Class* indexClass=getClass();
		while(indexClass=indexClass->next()){
			if(globalId == indexClass->m_ClassID){return indexClass;}
		}

		return NULL;
	}
	//�N���X����(���O�w��)
	C_Class* searchClass(string name){

		C_Class* indexClass=getClass();
		while(indexClass=indexClass->next()){
			if(name == indexClass->m_Name){return indexClass;}
		}

		return NULL;
	}

	//���\�b�h���s����(���\�b�h���A�Ăяo���ԍ��w��)
	MethodExe* searchMethodExe(Method* method,int exeNum){
	
		MethodExe* index=getMethodExe();
		while(index=index->next()){

			if(index->m_Method==method && index->m_MethodExeNum==exeNum){return index;}
		}
		return NULL;
	}
	//���\�b�h���s����(�Ǘ��ԍ��w��)
	MethodExe* searchMethodExe(int globalId){
	
		MethodExe* index=getMethodExe();
		while(index=index->next()){
			if(index->m_ID == globalId){return index;}
		}
		return NULL;
	}

	//���s����(�^�C�v�A���\�b�h���s�Ǘ��ԍ��w��)
	Exe* searchExe(int type,int id){

		Exe* index=getExe();
		while(index=index->CHECK()){
			if(index->m_EventType==type && index->m_MethodExeID==id){return index;}
		}
		return NULL;
	}

	//�s����(���\�b�h�w��A�s�ԍ��w��)
	C_Line* searchLine(Method* method,int id){
		C_Line* index=&method->m_Line;
		while(index=index->CHECK()){
			if(id == index->m_BaseID){return index;}
		}
		return NULL;
	}

}