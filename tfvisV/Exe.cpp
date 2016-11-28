#include "Exe.h"



int Exe::GetMethodExeID(int methodID,int number){

	Exe* indexExe=this;
	
	while(indexExe=indexExe->CHECK()){

		if(indexExe->m_MethodID != methodID){continue;}

		if(indexExe->m_EventType != ev::METHOD_START){continue;}

		if(number==0){return indexExe->m_MethodExeID;}
		number--;
	}
	return -1;
}